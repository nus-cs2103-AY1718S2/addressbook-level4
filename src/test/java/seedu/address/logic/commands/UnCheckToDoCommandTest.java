//@@author nhatquang3112
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
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code UnCheckToDoCommand}.
 */
public class UnCheckToDoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ToDo toDoToUnCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        ToDo unCheckedToDo = new ToDo(toDoToUnCheck.getContent(), new Status("undone"));
        UnCheckToDoCommand unCheckToDoCommand = prepareCommand(INDEX_FIRST_TODO);

        String expectedMessage = String.format(UnCheckToDoCommand.MESSAGE_UNCHECK_TODO_SUCCESS, toDoToUnCheck);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateToDo(toDoToUnCheck, unCheckedToDo);

        assertCommandSuccess(unCheckToDoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        UnCheckToDoCommand unCheckToDoCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unCheckToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ToDo toDoToUnCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        ToDo unCheckedToDo = new ToDo(toDoToUnCheck.getContent(), new Status("undone"));
        UnCheckToDoCommand unCheckToDoCommand = prepareCommand(INDEX_FIRST_TODO);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // uncheck -> first to-do unchecked
        unCheckToDoCommand.execute();
        undoRedoStack.push(unCheckToDoCommand);

        // undo -> reverts addressbook back to previous state and filtered to-do list to show all to-dos
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first to-do unChecked again
        expectedModel.updateToDo(toDoToUnCheck, unCheckedToDo);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        UnCheckToDoCommand unCheckToDoCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> unCheckToDoCommand not pushed into undoRedoStack
        assertCommandFailure(unCheckToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        UnCheckToDoCommand unCheckToDoFirstCommand = prepareCommand(INDEX_FIRST_TODO);
        UnCheckToDoCommand unCheckToDoSecondCommand = prepareCommand(INDEX_SECOND_TODO);

        // same object -> returns true
        assertTrue(unCheckToDoFirstCommand.equals(unCheckToDoFirstCommand));

        // same values -> returns true
        UnCheckToDoCommand unCheckToDoFirstCommandCopy = prepareCommand(INDEX_FIRST_TODO);
        assertTrue(unCheckToDoFirstCommand.equals(unCheckToDoFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        unCheckToDoFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(unCheckToDoFirstCommand.equals(unCheckToDoFirstCommandCopy));

        // different types -> returns false
        assertFalse(unCheckToDoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unCheckToDoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unCheckToDoFirstCommand.equals(unCheckToDoSecondCommand));
    }

    /**
     * Returns a {@code UnCheckToDoCommand} with the parameter {@code index}.
     */
    private UnCheckToDoCommand prepareCommand(Index index) {
        UnCheckToDoCommand unCheckToDoCommand = new UnCheckToDoCommand(index);
        unCheckToDoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unCheckToDoCommand;
    }
}
