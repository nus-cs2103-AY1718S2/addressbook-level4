//@@author chantiongley
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.model.book.Avail.RESERVED;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_BOOK;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Author;
import seedu.address.model.book.Avail;
import seedu.address.model.book.Book;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Title;
import seedu.address.model.tag.Tag;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code reserveCommand}.
 */
public class ReserveCommandTest {

    private Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FOURTH_BOOK.getZeroBased());
        Book reservedBook = createReservedBook(bookToReserve);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FOURTH_BOOK);

        String expectedMessage = String.format(reserveCommand.MESSAGE_RESERVE_BOOK_SUCCESS, bookToReserve);

        ModelManager expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());
        expectedModel.reserveBook(bookToReserve, reservedBook);

        assertCommandSuccess(reserveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of catalogue list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCatalogue().getBookList().size());

        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Book bookToReserve = model.getFilteredBookList().get(INDEX_FIFTH_BOOK.getZeroBased());
        Book reservedBook = createReservedBook(bookToReserve);
        ReserveCommand reserveCommand = prepareCommand(INDEX_FIFTH_BOOK);
        Model expectedModel = new ModelManager(model.getCatalogue(), new UserPrefs());

        // reserve -> first book reserve
        reserveCommand.execute();
        undoRedoStack.push(reserveCommand);

        // undo -> reverts catalogue back to previous state and filtered book list to show all books
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book reserved again
        expectedModel.returnBook(bookToReserve, reservedBook);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        ReserveCommand reserveCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> reserveCommand not pushed into undoRedoStack
        assertCommandFailure(reserveCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        ReserveCommand reserveFirstCommand = prepareCommand(INDEX_THIRD_BOOK);
        ReserveCommand reserveSecondCommand = prepareCommand(INDEX_FOURTH_BOOK);

        // same object -> returns true
        assertTrue(reserveFirstCommand.equals(reserveFirstCommand));

        // different types -> returns false
        assertFalse(reserveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(reserveFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(reserveFirstCommand.equals(reserveSecondCommand));
    }

    private ReserveCommand prepareCommand(Index index) {
        ReserveCommand reserveCommand = new ReserveCommand(index);
        reserveCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return reserveCommand;
    }

    /**
     * @param bookToReserve
     * @return duplicated book with Reserved Availability
     */
    public Book createReservedBook(Book bookToReserve) {
        assert bookToReserve != null;

        Title updatedTitle = bookToReserve.getTitle();
        Isbn updatedIsbn = bookToReserve.getIsbn();
        Avail updatedAvail = new Avail(RESERVED);
        Author updatedAuthor = bookToReserve.getAuthor();
        Set<Tag> updatedTags = bookToReserve.getTags();

        return new Book(updatedTitle, updatedAuthor, updatedIsbn, updatedAvail, updatedTags);
    }

}
