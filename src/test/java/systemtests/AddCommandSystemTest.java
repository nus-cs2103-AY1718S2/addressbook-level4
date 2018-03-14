package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;

import org.junit.Test;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;

public class AddCommandSystemTest extends BibliotekSystemTest {

    @Test
    public void add() throws Exception {
        executeCommand("clear");

        executeCommand(SearchCommand.COMMAND_WORD + " hello");
        new GuiRobot().waitForEvent(() -> !getResultDisplay().getText().equals(SearchCommand.MESSAGE_SEARCHING));

        Model model = getModel();
        ObservableList<Book> searchResultsList = model.getSearchResultsList();

        /* ------------------------ Perform add operations on the search results list ----------------------------- */

        /* Case: add a book to a non-empty book shelf, command with leading spaces and trailing spaces -> added
         */
        Book firstBook = searchResultsList.get(0);
        String command = "   " + AddCommand.COMMAND_WORD + "  1";
        assertCommandSuccess(command, firstBook);

        /* Case: undo adding firstBook to the list -> firstBook deleted */
        Model modelAfterAdding = getModel();
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding firstBook to the list -> firstBook added again */
        /*
        command = RedoCommand.COMMAND_WORD;
        model.addBook(firstBook);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelAfterAdding, expectedResultMessage);
        */

        /* Case: add to empty book shelf -> added */
        deleteAllBooks();

        executeCommand(SearchCommand.COMMAND_WORD + " hello");
        new GuiRobot().waitForEvent(() -> !getResultDisplay().getText().equals(SearchCommand.MESSAGE_SEARCHING));

        model = getModel();
        searchResultsList = model.getSearchResultsList();

        command = AddCommand.COMMAND_WORD + " 1";
        firstBook = searchResultsList.get(0);

        assertCommandSuccess(command, firstBook);

        /* ------------------------ Perform add operation while a book card is selected --------------------------- */

        /* Case: selects first card in the book list, add a book -> added, card selection remains unchanged */
        selectSearchResult(Index.fromOneBased(1));
        command = AddCommand.COMMAND_WORD + " 2";
        assertCommandSuccess(command, searchResultsList.get(1));

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate book -> rejected */
        model = getModel();
        executeCommand(command);
        new GuiRobot().waitForEvent(() -> !getResultDisplay().getText().equals(AddCommand.MESSAGE_ADDING));
        assertApplicationDisplaysExpected("", AddCommand.MESSAGE_DUPLICATE_BOOK, model);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(AddCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(AddCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = searchResultsList.size() + 1;
        assertCommandFailure(AddCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(AddCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(AddCommand.COMMAND_WORD + " 1 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        assertCommandFailure("adds 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("Add 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid active list type */
        executeCommand("list");
        assertCommandFailure(AddCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased(),
                AddCommand.MESSAGE_WRONG_ACTIVE_LIST);

        /* Case: add from empty search result list -> rejected */
        executeCommand(SearchCommand.COMMAND_WORD + " !@#$%^&*()(*%$#@!#$%^&&*");
        new GuiRobot().waitForEvent(() -> !getResultDisplay().getText().equals(SearchCommand.MESSAGE_SEARCHING));
        model.updateSearchResults(new BookShelf());
        assertCommandFailure(AddCommand.COMMAND_WORD + " " + INDEX_FIRST_BOOK.getOneBased(),
                MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

    }

    /**
     * Executes {@code command} and verifies that, after the web API has returned a result,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the search successful message.<br>
     * 4. {@code Model}, {@code Storage} and {@code BookListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see BibliotekSystemTest#assertSelectedBookListCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Book toAdd) throws Exception {
        Model expectedModel = getModel();

        executeCommand(command);
        assertCommandBoxShowsDefaultStyle();

        new GuiRobot().waitForEvent(() -> !getResultDisplay().getText().equals(AddCommand.MESSAGE_ADDING));

        try {
            expectedModel.addBook(toAdd);
        } catch (DuplicateBookException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);
        assertBookInBookShelf(toAdd);
        expectedModel.resetData(getModel().getBookShelf());

        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedBookListCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Book)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code BookListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddCommandSystemTest#assertCommandSuccess(String, Book)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedBookListCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code BookListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedBookListCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Checks that {@code book} is contained in the latest book shelf.
     */
    private void assertBookInBookShelf(Book book) {
        assertTrue(getModel().getBookShelf().getBookList().contains(book));
    }
}
