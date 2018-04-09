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
 * {@code CheckToDoCommand}.
 */
public class CheckToDoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ToDo toDoToCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        ToDo checkedToDo = new ToDo(toDoToCheck.getContent(), new Status("done"));
        CheckToDoCommand checkToDoCommand = prepareCommand(INDEX_FIRST_TODO);

        String expectedMessage = String.format(CheckToDoCommand.MESSAGE_CHECK_TODO_SUCCESS, toDoToCheck);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateToDo(toDoToCheck, checkedToDo);

        assertCommandSuccess(checkToDoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        CheckToDoCommand checkToDoCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(checkToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        ToDo toDoToCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        ToDo checkedToDo = new ToDo(toDoToCheck.getContent(), new Status("done"));
        CheckToDoCommand checkToDoCommand = prepareCommand(INDEX_FIRST_TODO);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // check -> first to-do checked
        checkToDoCommand.execute();
        undoRedoStack.push(checkToDoCommand);

        // undo -> reverts addressbook back to previous state and filtered to-do list to show all to-dos
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first to-do checked again
        expectedModel.updateToDo(toDoToCheck, checkedToDo);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredToDoList().size() + 1);
        CheckToDoCommand checkToDoCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> checkToDoCommand not pushed into undoRedoStack
        assertCommandFailure(checkToDoCommand, model, Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        CheckToDoCommand checkToDoFirstCommand = prepareCommand(INDEX_FIRST_TODO);
        CheckToDoCommand checkToDoSecondCommand = prepareCommand(INDEX_SECOND_TODO);

        // same object -> returns true
        assertTrue(checkToDoFirstCommand.equals(checkToDoFirstCommand));

        // same values -> returns true
        CheckToDoCommand checkToDoFirstCommandCopy = prepareCommand(INDEX_FIRST_TODO);
        assertTrue(checkToDoFirstCommand.equals(checkToDoFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        checkToDoFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(checkToDoFirstCommand.equals(checkToDoFirstCommandCopy));

        // different types -> returns false
        assertFalse(checkToDoFirstCommand.equals(1));

        // null -> returns false
        assertFalse(checkToDoFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(checkToDoFirstCommand.equals(checkToDoSecondCommand));
    }

    /**
     * Returns a {@code CheckToDoCommand} with the parameter {@code index}.
     */
    private CheckToDoCommand prepareCommand(Index index) {
        CheckToDoCommand checkToDoCommand = new CheckToDoCommand(index);
        checkToDoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return checkToDoCommand;
    }
}
