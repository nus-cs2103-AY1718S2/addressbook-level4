package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ACTIVITY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;

//@@author Kyomian
/**
 * Contains unit tests for {@code RemoveCommand} and integration tests (interactions with Model, UndoCommand
 * and RedoCommand)
 */
public class RemoveCommandTest {

    private Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());

    @Test
    public void execute_validTaskOptionValidIndex_success() throws Exception {
        Activity activityToDelete = model.getFilteredTaskList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        RemoveCommand removeCommand = prepareCommand("task", INDEX_FIRST_ACTIVITY);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_TASK_SUCCESS, activityToDelete);

        ModelManager expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
        expectedModel.deleteActivity(activityToDelete);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEventOptionValidIndex_success() throws Exception {
        Activity activityToDelete = model.getFilteredEventList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        RemoveCommand removeCommand = prepareCommand("event", INDEX_FIRST_ACTIVITY);

        String expectedMessage = String.format(RemoveCommand.MESSAGE_REMOVE_EVENT_SUCCESS, activityToDelete);

        ModelManager expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
        expectedModel.deleteActivity(activityToDelete);

        assertCommandSuccess(removeCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validOptionInvalidIndex_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        RemoveCommand removeCommand = prepareCommand("task", outOfBoundIndex);

        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validOptionValidIndex_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Activity activityToDelete = model.getFilteredTaskList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        RemoveCommand removeCommand = prepareCommand("task", INDEX_FIRST_ACTIVITY);
        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());

        // delete -> first activity deleted
        removeCommand.execute();
        undoRedoStack.push(removeCommand);

        // undo -> reverts desk board back to previous state
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first activity deleted again
        expectedModel.deleteActivity(activityToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_validOptionInvalidIndex_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        RemoveCommand removeCommand = prepareCommand("task", outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(removeCommand, model, Messages.MESSAGE_INVALID_ACTIVITY_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        RemoveCommand removeFirstCommand = prepareCommand("task", INDEX_FIRST_ACTIVITY);
        RemoveCommand removeSecondCommand = prepareCommand("task", INDEX_SECOND_ACTIVITY);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        RemoveCommand removeFirstCommandCopy = prepareCommand("task", INDEX_FIRST_ACTIVITY);
        assertTrue(removeFirstCommand.equals(removeFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        removeFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(removeFirstCommand.equals(removeFirstCommandCopy));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));

        // different activity -> returns false
        assertFalse(removeFirstCommand.equals(removeSecondCommand));
    }

    private RemoveCommand prepareCommand(String activityOption, Index index) {
        RemoveCommand removeCommand = new RemoveCommand(activityOption, index);
        removeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removeCommand;
    }
}
