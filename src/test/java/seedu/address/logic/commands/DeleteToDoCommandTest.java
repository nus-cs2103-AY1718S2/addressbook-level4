package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TODO;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.todo.ToDo;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteToDoCommand}.
 */
public class DeleteToDoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ToDo toDoToDelete = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        DeleteToDoCommand deleteToDoCommand = prepareCommand(INDEX_FIRST_TODO);

        String expectedMessage = String.format(DeleteToDoCommand.MESSAGE_DELETE_TODO_SUCCESS, toDoToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteToDo(toDoToDelete);

        assertCommandSuccess(deleteToDoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        DeleteToDoCommand deleteToDoCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ToDo toDoToDelete = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        DeleteToDoCommand deleteToDoCommand = prepareCommand(INDEX_FIRST_TODO);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first to-do deleted
        deleteToDoCommand.execute();
        undoRedoStack.push(deleteToDoCommand);

        // undo -> reverts addressbook back to previous state and filtered to-do list to show all to-dos
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first to-do deleted again
        expectedModel.deleteToDo(toDoToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        DeleteToDoCommand deleteToDoCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteToDoCommand not pushed into undoRedoStack
        assertCommandFailure(deleteToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteToDoCommand deleteToDoFirstCommand = prepareCommand(INDEX_FIRST_TODO);
        DeleteToDoCommand deleteToDoSecondCommand = prepareCommand(INDEX_SECOND_TODO);

        // same object -> returns true
        assertTrue(deleteToDoFirstCommand.equals(deleteToDoFirstCommand));

        // same values -> returns true
        DeleteToDoCommand deleteToDoFirstCommandCopy = prepareCommand(INDEX_FIRST_TODO);
        assertTrue(deleteToDoFirstCommand.equals(deleteToDoFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteToDoFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteToDoFirstCommand.equals(deleteToDoFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteToDoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteToDoFirstCommand.equals(null));

        // different to-do -> returns false
        assertFalse(deleteToDoFirstCommand.equals(deleteToDoSecondCommand));
    }

    /**
     * Returns a {@code DeleteToDoCommand} with the parameter {@code index}.
     */
    private DeleteToDoCommand prepareCommand(Index index) {
        DeleteToDoCommand deleteToDoCommand = new DeleteToDoCommand(index);
        deleteToDoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteToDoCommand;
    }
}
