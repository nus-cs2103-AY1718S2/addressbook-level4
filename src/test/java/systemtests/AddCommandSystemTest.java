package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.AUTHOR_DESC_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.CATEGORY_DESC_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_BABYLON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_ARTEMIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ARTEMIS;
import static seedu.address.testutil.TypicalBooks.ARTEMIS;
import static seedu.address.testutil.TypicalBooks.BABYLON_ASHES;
import static seedu.address.testutil.TypicalBooks.COLLAPSING_EMPIRE;
import static seedu.address.testutil.TypicalBooks.CONSIDER_PHLEBAS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.exceptions.DuplicateBookException;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookUtil;

public class AddCommandSystemTest extends BibliotekSystemTest {

    @Test
    public void add() throws Exception {
        executeCommand("clear");
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a book to a non-empty book shelf, command with leading spaces and trailing spaces -> added
         */
        Book toAdd = ARTEMIS;
        String command = "   " + AddCommand.COMMAND_WORD + "  " + TITLE_DESC_ARTEMIS + "  " + AUTHOR_DESC_ARTEMIS + " "
                + DESCRIPTION_DESC_ARTEMIS + "   " + CATEGORY_DESC_ARTEMIS + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Artemis to the list -> Artemis deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Artemis to the list -> Artemis added again */
        command = RedoCommand.COMMAND_WORD;
        model.addBook(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a book with all fields same as another book in the book shelf except title -> added */
        toAdd = new BookBuilder().withAuthors(VALID_AUTHOR_ARTEMIS).withCategories(VALID_CATEGORY_ARTEMIS)
                .withDescription(VALID_DESCRIPTION_ARTEMIS).withTitle("Title 1").build();
        command = AddCommand.COMMAND_WORD + " t/Title 1" + AUTHOR_DESC_ARTEMIS
                + DESCRIPTION_DESC_ARTEMIS + CATEGORY_DESC_ARTEMIS;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty book shelf -> added */
        deleteAllBooks();
        assertCommandSuccess(COLLAPSING_EMPIRE);

        /* Case: add a book, command with parameters in random order -> added */
        toAdd = BABYLON_ASHES;
        command = AddCommand.COMMAND_WORD + CATEGORY_DESC_BABYLON + AUTHOR_DESC_BABYLON
                + DESCRIPTION_DESC_BABYLON + TITLE_DESC_BABYLON;
        assertCommandSuccess(command, toAdd);

        /* ------------------------ Perform add operation while a book card is selected --------------------------- */

        /* Case: selects first card in the book list, add a book -> added, card selection remains unchanged */
        selectBook(Index.fromOneBased(1));
        assertCommandSuccess(CONSIDER_PHLEBAS);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate book -> rejected */
        command = BookUtil.getAddCommand(CONSIDER_PHLEBAS);
        assertCommandFailure(command, AddCommand.MESSAGE_DUPLICATE_BOOK);

        /* Case: missing title -> rejected */
        command = AddCommand.COMMAND_WORD + CATEGORY_DESC_BABYLON + AUTHOR_DESC_BABYLON
                + DESCRIPTION_DESC_BABYLON;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + BookUtil.getBookDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

    }

    /**
     * Executes the {@code AddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code BookListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see BibliotekSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Book toAdd) {
        assertCommandSuccess(BookUtil.getAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Book)}. Executes {@code command}
     * instead.
     * @see AddCommandSystemTest#assertCommandSuccess(Book)
     */
    private void assertCommandSuccess(String command, Book toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addBook(toAdd);
        } catch (DuplicateBookException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
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
        assertSelectedCardUnchanged();
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
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
