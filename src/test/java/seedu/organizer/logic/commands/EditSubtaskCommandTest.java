package seedu.organizer.logic.commands;

//@@author agus
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
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
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

public class EditSubtaskCommandTest {

    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    @Before
    public void setUp() throws Exception {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        Task originalTask = model.getFilteredTaskList().get(0);
        Task editedTask = editSubtask(originalTask, INDEX_FIRST_TASK, subtask);

        EditSubtaskCommand editSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, subtask);

        String expectedMessage = String.format(editSubtaskCommand.MESSAGE_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.updateTask(originalTask, editedTask);

        assertCommandSuccess(editSubtaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditSubtaskCommand editSubtaskCommand = prepareCommand(outOfBoundsIndex, INDEX_FIRST_TASK, subtask);

        assertCommandFailure(editSubtaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidsubaskIndexUnfilteredList_failure() {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().get(0).getSubtasks().size() + 1);
        EditSubtaskCommand editSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, outOfBoundsIndex, subtask);

        assertCommandFailure(editSubtaskCommand, model, Messages.MESSAGE_INVALID_SUBTASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateSubtask_failure() {
        Task originalTask = model.getFilteredTaskList().get(0);
        Subtask subtask = originalTask.getSubtasks().get(1);

        EditSubtaskCommand editSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, subtask);

        assertCommandFailure(editSubtaskCommand , model, EditSubtaskCommand.MESSAGE_DUPLICATED);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        Task originalTask = model.getFilteredTaskList().get(0);
        Task editedTask = editSubtask(originalTask, INDEX_FIRST_TASK, subtask);

        EditSubtaskCommand editSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, subtask);
        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);

        // edit -> first task edited
        editSubtaskCommand.execute();
        undoRedoStack.push(editSubtaskCommand);

        // undo -> reverts addressbook back to previous state and filtered task list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.updateTask(originalTask, editedTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equal_unequalObject_false() {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));
        Subtask otherSubtask = model.getFilteredTaskList().get(0).getSubtasks().get(0);

        EditSubtaskCommand firstIndexSubtask = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, subtask);
        EditSubtaskCommand firstIndexOtherSubtask = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, otherSubtask);
        EditSubtaskCommand secondIndexSubtask = prepareCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK, subtask);
        EditSubtaskCommand secondIndexOtherSubtask = prepareCommand(INDEX_SECOND_TASK, INDEX_FIRST_TASK, subtask);

        assertNotEquals(firstIndexSubtask, firstIndexOtherSubtask);
        assertNotEquals(firstIndexSubtask, secondIndexOtherSubtask);
        assertNotEquals(firstIndexSubtask, secondIndexSubtask);
    }

    @Test
    public void equal_equalObject_true() {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        EditSubtaskCommand firstIndexSubtask = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, subtask);
        EditSubtaskCommand firstIndexOtherSubtask = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, subtask);

        assertEquals(firstIndexSubtask, firstIndexOtherSubtask);
        assertEquals(firstIndexSubtask, firstIndexSubtask);
    }

    /**
     * Retrun an (@code Task) with added subtask
     */
    private Task editSubtask(Task task, Index index, Subtask subtask) {
        List<Subtask> subtasks = new ArrayList<Subtask>(task.getSubtasks());
        subtasks.set(index.getZeroBased(), subtask);
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
                task.getRecurrence()
        );
    }

    /**
     * Returns an {@code editSubtaskCommand} with parameters {@code taskIndex}, {@code subtaskIndex}
     * and {@code subtask}
     */
    private EditSubtaskCommand prepareCommand(Index taskIndex, Index subtaskIndex, Subtask subtask) {
        EditSubtaskCommand editSubtaskCommand = new EditSubtaskCommand (taskIndex, subtaskIndex, subtask);
        editSubtaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editSubtaskCommand;
    }
}
