package seedu.organizer.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import java.util.ArrayList;
import java.util.List;

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

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * DeleteSubtaskCommand.
 */
public class DeleteSubtaskCommandTest {
    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask, INDEX_FIRST_TASK);
        Subtask toggledSubtask = toggledTask.getSubtasks().get(INDEX_FIRST_TASK.getZeroBased());

        DeleteSubtaskCommand deleteSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteSubtaskCommand.MESSAGE_EDIT_SUBTASK_SUCCESS, toggledSubtask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.updateTask(originalTask, toggledTask);

        assertCommandSuccess(deleteSubtaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteSubtaskCommand deleteSubtaskCommand = prepareCommand(outOfBoundsIndex, INDEX_FIRST_TASK);

        assertCommandFailure(deleteSubtaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSubtaskIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteSubtaskCommand deleteSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, outOfBoundsIndex);

        assertCommandFailure(deleteSubtaskCommand, model, Messages.MESSAGE_INVALID_SUBTASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidSubtaskAndTaskIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteSubtaskCommand deleteSubtaskCommand = prepareCommand(outOfBoundsIndex, outOfBoundsIndex);

        assertCommandFailure(deleteSubtaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask, INDEX_FIRST_TASK);
        DeleteSubtaskCommand deleteSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());

        // edit -> first task edited
        deleteSubtaskCommand.execute();
        undoRedoStack.push(deleteSubtaskCommand);

        // undo -> reverts addressbook back to previous state and filtered task list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.updateTask(originalTask, toggledTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equalityTest() {
        DeleteSubtaskCommand testSubject1 = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);
        DeleteSubtaskCommand testSubject2 = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);
        DeleteSubtaskCommand testSubject3 = prepareCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK);
        DeleteSubtaskCommand testSubject4 = prepareCommand(INDEX_SECOND_TASK, INDEX_SECOND_TASK);

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
        subtasks.remove(index.getZeroBased());
        return new Task(
                task.getName(),
                task.getPriority(),
                task.getDeadline(),
                task.getDateAdded(),
                task.getDescription(),
                task.getStatus(),
                task.getTags(),
                subtasks
        );
    }

    /**
     * Returns an {@code DeleteSubtaskCommand} with parameters {@code index} and {@code descriptor}
     */
    private DeleteSubtaskCommand prepareCommand(Index taskIndex, Index subtaskIndex) {
        DeleteSubtaskCommand deleteSubtaskCommand = new DeleteSubtaskCommand(taskIndex, subtaskIndex);
        deleteSubtaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteSubtaskCommand;
    }
}
