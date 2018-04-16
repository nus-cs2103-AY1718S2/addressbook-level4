package seedu.organizer.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.organizer.logic.commands.CommandTestUtil.showRecurredTasksAtIndex;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.recurrence.exceptions.TaskAlreadyRecurredException;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

//@@author natania-d-reused
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteRecurredTasksCommand}.
 */
public class DeleteRecurredTasksCommandTest {

    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
            model.recurWeeklyTask(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()), 1);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        } catch (DuplicateTaskException e) {
            e.printStackTrace();
        } catch (TaskAlreadyRecurredException e) {
            e.printStackTrace();
        } catch (TaskNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(INDEX_FIRST_TASK);


        String expectedMessage = String.format(DeleteRecurredTasksCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);


        ModelManager expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.deleteRecurredTasks(taskToDelete);

        assertCommandSuccess(deleteRecurredTasksCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecurredTasksCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showRecurredTasksAtIndex(model, INDEX_FIRST_TASK);

        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteRecurredTasksCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.deleteRecurredTasks(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteRecurredTasksCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecurredTasksAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_THIRD_TASK;
        // ensures that outOfBoundIndex is still in bounds of organizer list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrganizer().getTaskList().size());

        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteRecurredTasksCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_taskNotRecurring_throwsCommandException() {
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(INDEX_THIRD_TASK);

        assertCommandFailure(deleteRecurredTasksCommand, model, DeleteRecurredTasksCommand.MESSAGE_NOT_RECURRED_TASK);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());

        // delete -> first task and its iterations deleted
        deleteRecurredTasksCommand.execute();
        undoRedoStack.push(deleteRecurredTasksCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task deleted again
        expectedModel.deleteRecurredTasks(taskToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteRecurredTasksCommand not pushed into undoRedoStack
        assertCommandFailure(deleteRecurredTasksCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteRecurredTasksCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_TASK);
        DeleteRecurredTasksCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteRecurredTasksCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteRecurredTasksCommand} with the parameter {@code index}.
     */
    private DeleteRecurredTasksCommand prepareCommand(Index index) {
        DeleteRecurredTasksCommand deleteRecurredTasksCommand = new DeleteRecurredTasksCommand(index);
        deleteRecurredTasksCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteRecurredTasksCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
