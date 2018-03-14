package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.ActiveListType;
import seedu.address.model.BookShelf;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code AddCommand}.
 */
public class AddCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(new BookShelf(), new UserPrefs());
        prepareSearchResultListInModel(model);
    }

    @Test
    public void constructor_nullBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_invalidActiveListType_failure() {
        model.setActiveListType(ActiveListType.BOOK_SHELF);
        AddCommand addCommand = prepareCommand(INDEX_FIRST_BOOK);

        assertCommandFailure(addCommand, model, AddCommand.MESSAGE_WRONG_ACTIVE_LIST);
    }

    @Test
    public void execute_validIndexSearchResultsList_success() throws Exception {
        Book bookToAdd = model.getSearchResultsList().get(INDEX_FIRST_BOOK.getZeroBased());

        String expectedMessage = String.format(AddCommand.MESSAGE_SUCCESS, bookToAdd);

        AddCommand addCommand = prepareCommand(INDEX_FIRST_BOOK);
        ModelManager expectedModel = new ModelManager();
        prepareSearchResultListInModel(expectedModel);
        expectedModel.addBook(bookToAdd);

        assertCommandSuccess(addCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexSearchResultsList_failure() {
        AddCommand addCommand = prepareCommand(Index.fromOneBased(model.getSearchResultsList().size() + 1));

        assertCommandFailure(addCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateBook_throwsCommandException() throws Exception {
        Book bookInList = model.getSearchResultsList().get(INDEX_FIRST_BOOK.getZeroBased());
        model.addBook(bookInList);
        assertCommandFailure(prepareCommand(INDEX_FIRST_BOOK), model, AddCommand.MESSAGE_DUPLICATE_BOOK);
    }

    @Test
    public void executeUndoRedo_validIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Book bookToAdd = model.getSearchResultsList().get(INDEX_FIRST_BOOK.getZeroBased());
        AddCommand addCommand = prepareCommand(INDEX_FIRST_BOOK);
        ModelManager expectedModel = new ModelManager(model.getBookShelf(), new UserPrefs());
        prepareSearchResultListInModel(expectedModel);

        // add -> first book added
        addCommand.execute();
        undoRedoStack.push(addCommand);

        // undo -> reverts bookshelf back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book added again
        expectedModel.addBook(bookToAdd);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndex_failure() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getSearchResultsList().size() + 1);
        AddCommand addCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> addCommand not pushed into undoRedoStack
        assertCommandFailure(addCommand, model, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        AddCommand addFirstCommand = prepareCommand(INDEX_FIRST_BOOK);
        AddCommand addSecondCommand = prepareCommand(INDEX_SECOND_BOOK);

        // same object -> returns true
        assertTrue(addFirstCommand.equals(addFirstCommand));

        // same values -> returns true
        AddCommand addFirstCommandCopy = prepareCommand(INDEX_FIRST_BOOK);
        assertTrue(addFirstCommand.equals(addFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        addFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(addFirstCommand.equals(addFirstCommandCopy));

        // different types -> returns false
        assertFalse(addFirstCommand.equals(1));

        // null -> returns false
        assertFalse(addFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(addFirstCommand.equals(addSecondCommand));
    }

    /**
     * Set up {@code model} with a non-empty search result list and
     * switch active list to search results list.
     */
    private void prepareSearchResultListInModel(Model model) {
        model.setActiveListType(ActiveListType.SEARCH_RESULTS);
        BookShelf bookShelf = getTypicalBookShelf();
        model.updateSearchResults(bookShelf);
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private AddCommand prepareCommand(Index index) {
        AddCommand addCommand = new AddCommand(index);
        addCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addCommand;
    }

}
