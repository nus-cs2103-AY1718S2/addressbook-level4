package seedu.organizer.logic.commands;

//@@author aguss787
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
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

public class AddSubtaskCommandTest {

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
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        Task originalTask = model.getFilteredTaskList().get(0);
        Task editedTask = addSubtask(originalTask, subtask);

        AddSubtaskCommand addSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, subtask);

        String expectedMessage = String.format(AddSubtaskCommand.MESSAGE_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.updateTask(originalTask, editedTask);

        assertCommandSuccess(addSubtaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_secondunfilteredList_success() throws Exception {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        Task originalTask = model.getFilteredTaskList().get(1);
        Task editedTask = addSubtask(originalTask, subtask);

        AddSubtaskCommand addSubtaskCommand = prepareCommand(INDEX_SECOND_TASK, subtask);

        String expectedMessage = String.format(AddSubtaskCommand.MESSAGE_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.updateTask(originalTask, editedTask);

        assertCommandSuccess(addSubtaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        AddSubtaskCommand addSubtaskCommand = prepareCommand(outOfBoundsIndex, subtask);

        assertCommandFailure(addSubtaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicateSubtask_failure() {
        Task originalTask = model.getFilteredTaskList().get(0);
        Subtask subtask = originalTask.getSubtasks().get(0);

        AddSubtaskCommand addSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, subtask);

        assertCommandFailure(addSubtaskCommand, model, AddSubtaskCommand.MESSAGE_DUPLICATED);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        Task originalTask = model.getFilteredTaskList().get(0);
        Task editedTask = addSubtask(originalTask, subtask);

        AddSubtaskCommand addSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, subtask);
        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);

        // edit -> first task edited
        addSubtaskCommand.execute();
        undoRedoStack.push(addSubtaskCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.updateTask(originalTask, editedTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equal_unequalObject_false() {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));
        Subtask otherSubtask = model.getFilteredTaskList().get(0).getSubtasks().get(0);

        AddSubtaskCommand firstIndexSubtask = prepareCommand(INDEX_FIRST_TASK, subtask);
        AddSubtaskCommand firstIndexOtherSubtask = prepareCommand(INDEX_FIRST_TASK, otherSubtask);
        AddSubtaskCommand secondIndexSubtask = prepareCommand(INDEX_SECOND_TASK, subtask);
        AddSubtaskCommand secondIndexOtherSubtask = prepareCommand(INDEX_SECOND_TASK, otherSubtask);

        assertNotEquals(firstIndexSubtask, firstIndexOtherSubtask);
        assertNotEquals(firstIndexSubtask, secondIndexOtherSubtask);
        assertNotEquals(firstIndexSubtask, secondIndexSubtask);
    }

    @Test
    public void equal_equalObject_true() {
        Subtask subtask = new Subtask(new Name(VALID_NAME_EXAM));

        AddSubtaskCommand firstIndexSubtask = prepareCommand(INDEX_FIRST_TASK, subtask);
        AddSubtaskCommand firstIndexOtherSubtask = prepareCommand(INDEX_FIRST_TASK, subtask);

        assertEquals(firstIndexSubtask, firstIndexOtherSubtask);
        assertEquals(firstIndexSubtask, firstIndexSubtask);
    }

    /**
     * Retrun an (@code Task) with added subtask
     */
    private Task addSubtask(Task task, Subtask subtask) {
        List<Subtask> subtasks = new ArrayList<Subtask>(task.getSubtasks());
        subtasks.add(subtask);
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
     * Returns an {@code AddSubtaskCommand} with parameters {@code index} and {@code descriptor}
     */
    private AddSubtaskCommand prepareCommand(Index index, Subtask subtask) {
        AddSubtaskCommand addSubtaskCommand = new AddSubtaskCommand(index, subtask);
        addSubtaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addSubtaskCommand;
    }
}
