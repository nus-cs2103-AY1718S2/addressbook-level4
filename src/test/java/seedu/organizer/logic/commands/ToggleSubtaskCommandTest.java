package seedu.organizer.logic.commands;

//@@author agus
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * ToggleSubtaskCommand.
 */
public class ToggleSubtaskCommandTest {
    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        } catch (UserPasswordWrongException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask, INDEX_FIRST_TASK);
        Subtask toggledSubtask = toggledTask.getSubtasks().get(INDEX_FIRST_TASK.getZeroBased());

        ToggleSubtaskCommand toggleSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);

        String expectedMessage = String.format(ToggleSubtaskCommand.MESSAGE_EDIT_SUBTASK_SUCCESS, toggledSubtask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.updateTask(originalTask, toggledTask);

        assertCommandSuccess(toggleSubtaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_secondunfilteredList_success() throws Exception {
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask, INDEX_SECOND_TASK);
        Subtask toggledSubtask = toggledTask.getSubtasks().get(INDEX_SECOND_TASK.getZeroBased());

        ToggleSubtaskCommand toggleSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);

        String expectedMessage = String.format(ToggleSubtaskCommand.MESSAGE_EDIT_SUBTASK_SUCCESS, toggledSubtask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.updateTask(originalTask, toggledTask);

        assertCommandSuccess(toggleSubtaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ToggleSubtaskCommand toggleSubtaskCommand = prepareCommand(outOfBoundsIndex, INDEX_FIRST_TASK);

        assertCommandFailure(toggleSubtaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSubtaskIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ToggleSubtaskCommand toggleSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, outOfBoundsIndex);

        assertCommandFailure(toggleSubtaskCommand, model, Messages.MESSAGE_INVALID_SUBTASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSubtaskAndTaskIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ToggleSubtaskCommand toggleSubtaskCommand = prepareCommand(outOfBoundsIndex, outOfBoundsIndex);

        assertCommandFailure(toggleSubtaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask, INDEX_FIRST_TASK);
        ToggleSubtaskCommand toggleSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);

        // edit -> first task edited
        toggleSubtaskCommand.execute();
        undoRedoStack.push(toggleSubtaskCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.updateTask(originalTask, toggledTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equalityTest() {
        ToggleSubtaskCommand testSubject1 = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);
        ToggleSubtaskCommand testSubject2 = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);
        ToggleSubtaskCommand testSubject3 = prepareCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        ToggleSubtaskCommand testSubject4 = prepareCommand(INDEX_SECOND_TASK, INDEX_SECOND_TASK);

        assertEquals(testSubject1, testSubject1);
        assertEquals(testSubject1, testSubject2);
        assertNotEquals(testSubject1, testSubject3);
        assertNotEquals(testSubject1, testSubject4);
        assertNotEquals(testSubject4, testSubject3);
    }

    /**
     * Retrun an (@code Task) with status inversed
     */
    private Task toggleTask(Task task, Index index) {
        List<Subtask> subtasks = new ArrayList<>(task.getSubtasks());
        Subtask oldSubtask = subtasks.get(index.getZeroBased());
        Subtask newSubtask = new Subtask(oldSubtask.getName(), oldSubtask.getStatus().getInverse());
        subtasks.set(index.getZeroBased(), newSubtask);
        return new Task(
                task.getName(),
                task.getUpdatedPriority(),
                task.getBasePriority(),
                task.getDeadline(),
                task.getDateAdded(),
                task.getDateCompleted(),
                task.getDescription(),
                task.getStatus(),
                task.getTags(),
                subtasks,
                task.getUser(),
                task.getRecurrence());
    }

    /**
     * Returns an {@code ToggleSubtaskCommand} with parameters {@code index} and {@code descriptor}
     */
    private ToggleSubtaskCommand prepareCommand(Index taskIndex, Index subtaskIndex) {
        ToggleSubtaskCommand toggleSubtaskCommand = new ToggleSubtaskCommand(taskIndex, subtaskIndex);
        toggleSubtaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return toggleSubtaskCommand;
    }
}
