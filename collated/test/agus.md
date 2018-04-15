# agus
###### /java/seedu/organizer/logic/commands/AddSubtaskCommandTest.java
``` java
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
```
###### /java/seedu/organizer/logic/commands/DeleteSubtaskCommandTest.java
``` java
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
 * DeleteSubtaskCommand.
 */
public class DeleteSubtaskCommandTest {
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
        Subtask toggledSubtask = originalTask.getSubtasks().get(INDEX_FIRST_TASK.getZeroBased());

        DeleteSubtaskCommand deleteSubtaskCommand = prepareCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteSubtaskCommand.MESSAGE_EDIT_SUBTASK_SUCCESS, toggledSubtask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
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
        expectedModel.loginUser(ADMIN_USER);

        // edit -> first task edited
        deleteSubtaskCommand.execute();
        undoRedoStack.push(deleteSubtaskCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all tasks
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
     * Returns an {@code DeleteSubtaskCommand} with parameters {@code index} and {@code descriptor}
     */
    private DeleteSubtaskCommand prepareCommand(Index taskIndex, Index subtaskIndex) {
        DeleteSubtaskCommand deleteSubtaskCommand = new DeleteSubtaskCommand(taskIndex, subtaskIndex);
        deleteSubtaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteSubtaskCommand;
    }
}
```
###### /java/seedu/organizer/logic/commands/EditSubtaskCommandTest.java
``` java
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
```
###### /java/seedu/organizer/logic/commands/ToggleCommandTest.java
``` java
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;
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
import seedu.organizer.model.Organizer;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * ToggleCommand.
 */
public class ToggleCommandTest {
    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    @Before
    public void setUp() throws UserNotFoundException, CurrentlyLoggedInException, UserPasswordWrongException {
        model.loginUser(ADMIN_USER);
    }

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask);

        ToggleCommand toggleCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(ToggleCommand.MESSAGE_EDIT_TASK_SUCCESS, toggledTask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.updateTask(originalTask, toggledTask);

        assertCommandSuccess(toggleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_secondunfilteredList_success() throws Exception {
        Task originalTask = model.getFilteredTaskList().get(1);
        Task toggledTask = toggleTask(originalTask);

        ToggleCommand toggleCommand = prepareCommand(INDEX_SECOND_TASK);

        String expectedMessage = String.format(ToggleCommand.MESSAGE_EDIT_TASK_SUCCESS, toggledTask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.updateTask(originalTask, toggledTask);

        assertCommandSuccess(toggleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ToggleCommand toggleCommand = prepareCommand(outOfBoundsIndex);

        assertCommandFailure(toggleCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask);
        ToggleCommand toggleCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);

        // edit -> first task edited
        toggleCommand.execute();
        undoRedoStack.push(toggleCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all tasks
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.updateTask(originalTask, toggledTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Return an (@code Task) with status inversed
     */
    private Task toggleTask(Task task) {
        return new Task(
                task.getName(),
                task.getUpdatedPriority(),
                task.getBasePriority(),
                task.getDeadline(),
                task.getDateAdded(),
                task.getDateCompleted(), task.getDescription(),
                task.getStatus().getInverse(),
                task.getTags(),
                task.getSubtasks(),
                task.getUser(),
                task.getRecurrence());
    }

    /**
     * Returns an {@code ToggleCommand} with parameters {@code index} and {@code descriptor}
     */
    private ToggleCommand prepareCommand(Index index) {
        ToggleCommand toggleCommand = new ToggleCommand(index);
        toggleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return toggleCommand;
    }
}
```
###### /java/seedu/organizer/logic/commands/ToggleSubtaskCommandTest.java
``` java
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
```
###### /java/seedu/organizer/logic/parser/AddSubtaskCommandParserTest.java
``` java
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_STUDY;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.AddSubtaskCommand;
import seedu.organizer.logic.commands.util.EditTaskDescriptor;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Name;
import seedu.organizer.testutil.EditTaskDescriptorBuilder;

public class AddSubtaskCommandParserTest {

    private static final String VALID_NAME = " " + PREFIX_NAME + VALID_NAME_EXAM;
    private static final String INVALID_NAME = " " + INVALID_NAME_DESC;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubtaskCommand.MESSAGE_USAGE);

    private AddSubtaskCommandParser parser = new AddSubtaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_EXAM, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_NAME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_NAME, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + VALID_NAME;

        AddSubtaskCommand expectedCommand = new AddSubtaskCommand(targetIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + VALID_NAME + VALID_NAME;

        AddSubtaskCommand expectedCommand = new AddSubtaskCommand(targetIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + INVALID_NAME + VALID_NAME;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withPriority(VALID_PRIORITY_STUDY).build();
        AddSubtaskCommand expectedCommand = new AddSubtaskCommand(targetIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/organizer/logic/parser/DeleteSubtaskCommandParserTest.java
``` java
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.organizer.logic.commands.DeleteSubtaskCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 *
 * @see DeleteCommandParserTest
 */
public class DeleteSubtaskCommandParserTest {

    private DeleteSubtaskCommandParser parser = new DeleteSubtaskCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1 2", new DeleteSubtaskCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSubtaskCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSubtaskCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 2 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteSubtaskCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/organizer/logic/parser/EditSubtaskCommandParserTest.java
``` java
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.EditSubtaskCommand;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.Name;

public class EditSubtaskCommandParserTest {

    private static final String VALID_NAME = " " + PREFIX_NAME + VALID_NAME_EXAM;
    private static final String INVALID_NAME = " " + INVALID_NAME_DESC;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditSubtaskCommand.MESSAGE_USAGE);

    private EditSubtaskCommandParser parser = new EditSubtaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_EXAM, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1" + VALID_NAME_EXAM, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 1" + VALID_NAME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 -2" + VALID_NAME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 1" + VALID_NAME, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 0" + VALID_NAME, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "hue 1", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1 1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index taskIndex = INDEX_SECOND_TASK;
        Index subtaskIndex = INDEX_FIRST_TASK;
        String userInput = taskIndex.getOneBased() + " " + subtaskIndex.getOneBased() + VALID_NAME;

        EditSubtaskCommand expectedCommand = new EditSubtaskCommand(taskIndex, subtaskIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index taskIndex = INDEX_SECOND_TASK;
        Index subtaskIndex = INDEX_FIRST_TASK;
        String userInput = taskIndex.getOneBased() + " " + subtaskIndex.getOneBased() + VALID_NAME + VALID_NAME;

        EditSubtaskCommand expectedCommand = new EditSubtaskCommand(taskIndex, subtaskIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index taskIndex = INDEX_FIRST_TASK;
        Index subtaskIndex = INDEX_FIRST_TASK;
        String userInput = taskIndex.getOneBased() + " " + subtaskIndex.getOneBased() + INVALID_NAME + VALID_NAME;
        EditSubtaskCommand expectedCommand = new EditSubtaskCommand(taskIndex, subtaskIndex,
                new Subtask(new Name(VALID_NAME_EXAM)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### /java/seedu/organizer/logic/parser/OrganizerParserLoggedInTest.java
``` java
    @Test
    public void parseCommand_editSubtask() throws Exception {
        Task task = new TaskBuilder().build();
        Subtask subtask = new Subtask(task.getName());
        EditSubtaskCommand command = (EditSubtaskCommand) parser.parseCommand(EditSubtaskCommand.COMMAND_WORD
                + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + INDEX_FIRST_TASK.getOneBased() + " "
                + TaskUtil.getSubtaskDetails(task));
        EditSubtaskCommand commandAlias = (EditSubtaskCommand) parser.parseCommand(
                EditSubtaskCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_TASK.getOneBased() + " " + INDEX_FIRST_TASK.getOneBased() + " "
                + TaskUtil.getSubtaskDetails(task));
        assertEquals(new EditSubtaskCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, subtask), command);
        assertEquals(new EditSubtaskCommand(INDEX_FIRST_TASK, INDEX_FIRST_TASK, subtask), commandAlias);
    }
```
###### /java/seedu/organizer/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseMultipleIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseIndexAsArray("10 a");
    }

    @Test
    public void parseMultipleIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndexAsArray(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseMultipleIndex_outOfRangeInputWithSomeValidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndexAsArray(Long.toString(Integer.MAX_VALUE + 1) + " 1 2");
    }

    @Test
    public void parseMultipleIndex_validInput_success() throws Exception {
        Index[] testCase = {INDEX_FIRST_TASK, INDEX_SECOND_TASK};

        // No whitespaces
        assertArrayEquals(testCase, ParserUtil.parseIndexAsArray("1 2"));

        // Leading and trailing whitespaces
        assertArrayEquals(testCase, ParserUtil.parseIndexAsArray("  1   2  "));
    }

    @Test
    public void parseSubtaskIndex_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseSubtaskIndex("10 a");
    }

    @Test
    public void parseSubtaskIndex_outOfRangeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseSubtaskIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseSubtaskIndex_outOfRangeInputWithSomeValidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseSubtaskIndex(Long.toString(Integer.MAX_VALUE + 1) + " 2");
    }

    @Test
    public void parseSubtaskIndex_oneIndex_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_WRONG_PART_COUNT);
        ParserUtil.parseSubtaskIndex("2");
    }

    @Test
    public void parseSubtaskIndex_threeIndex_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_WRONG_PART_COUNT);
        ParserUtil.parseSubtaskIndex("2 3 4");
    }

    @Test
    public void parseSubtaskIndex_validInput_success() throws Exception {
        Index[] testCase = {INDEX_FIRST_TASK, INDEX_SECOND_TASK};

        // No whitespaces
        assertArrayEquals(testCase, ParserUtil.parseSubtaskIndex("1 2"));

        // Leading and trailing whitespaces
        assertArrayEquals(testCase, ParserUtil.parseSubtaskIndex("  1   2  "));
    }
```
###### /java/seedu/organizer/logic/parser/ToggleCommandParserTest.java
``` java
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import org.junit.Test;

import seedu.organizer.logic.commands.ToggleCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 *
 * @see DeleteCommandParserTest
 */
public class ToggleCommandParserTest {

    private ToggleCommandParser parser = new ToggleCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new ToggleCommand(INDEX_FIRST_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ToggleCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/organizer/logic/parser/ToggleSubtaskCommandParserTest.java
``` java
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;

import org.junit.Test;

import seedu.organizer.logic.commands.ToggleSubtaskCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 *
 * @see DeleteCommandParserTest
 */
public class ToggleSubtaskCommandParserTest {

    private ToggleSubtaskCommandParser parser = new ToggleSubtaskCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1 2", new ToggleSubtaskCommand(INDEX_FIRST_TASK, INDEX_SECOND_TASK));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ToggleSubtaskCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ToggleSubtaskCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 2 3", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ToggleSubtaskCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/organizer/model/subtask/SubtaskTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Status;
import seedu.organizer.testutil.Assert;

public class SubtaskTest {
    public static final Name VALID_NAME = new Name("hue");
    public static final Status VALID_STATUS = new Status(true);

    @Test
    public void constructorwithouttask_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subtask(null, null));
        Assert.assertThrows(NullPointerException.class, () -> new Subtask(null, VALID_STATUS));
        Assert.assertThrows(NullPointerException.class, () -> new Subtask(null));
    }

    @Test
    public void constructor_nullstatus_statusfalse() {
        Subtask subtask = new Subtask(VALID_NAME, null);
        assertEquals(subtask.getStatus().value, false);
    }

    @Test
    public void equal_allcombination() {
        Subtask subtask1 = new Subtask(VALID_NAME);
        Subtask subtask2 = new Subtask(VALID_NAME, new Status(true));
        Subtask subtask3 = new Subtask(VALID_NAME, new Status(false));
        Subtask subtask4 = new Subtask(new Name("ganteng"), new Status(false));

        assertEquals(subtask1, subtask2);
        assertEquals(subtask1, subtask3);
        assertEquals(subtask2, subtask3);
        assertNotEquals(subtask4, subtask3);
    }
}
```
###### /java/seedu/organizer/model/subtask/UniqueSubtaskListTest.java
``` java
import static java.util.Collections.reverse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.organizer.model.util.SampleDataUtil;
import seedu.organizer.testutil.Assert;

public class UniqueSubtaskListTest {
    @Test
    public void constructor_null_nullpointerexception() {
        Assert.assertThrows(NullPointerException.class, () -> new UniqueSubtaskList(null));
    }

    @Test
    public void constructor_validInput_preserveOrder() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        assertEquals(subtaskList.toList(), subtasks);
        reverse(subtasks);
        assertNotEquals(subtaskList.toList(), subtasks);
    }

    @Test
    public void setSubtasks_validInput_canchange() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("c", "d");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        subtaskList.setSubtasks(otherSubtasks);
        assertNotEquals(subtaskList.toList(), subtasks);
        assertEquals(subtaskList.toList(), otherSubtasks);
    }

    @Test
    public void contains_validInput_chackforcontains() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> cloned = new ArrayList<>(subtasks);
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("c", "d");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);

        for (Subtask subtask: cloned) {
            assertTrue(subtaskList.contains(subtask));
        }

        for (Subtask subtask: otherSubtasks) {
            assertFalse(subtaskList.contains(subtask));
        }
    }

    @Test
    public void add_allInput() throws Exception {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("c", "d");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);

        Assert.assertThrows(NullPointerException.class, () -> subtaskList.add(null));
        Assert.assertThrows(UniqueSubtaskList.DuplicateSubtaskException.class, () -> subtaskList.add(subtasks.get(0)));
        subtaskList.add(otherSubtasks.get(0));
        subtasks.add(otherSubtasks.get(0));
        assertEquals(subtaskList.toList(), subtasks);
    }

    @Test
    public void iterator() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList();
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);

        for (Subtask subtask: subtaskList) {
            otherSubtasks.add(subtask);
        }

        assertEquals(subtasks, otherSubtasks);
    }

    @Test
    public void asObservableList() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList();
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);

        for (Subtask subtask: subtaskList.asObservableList()) {
            otherSubtasks.add(subtask);
        }

        assertEquals(subtasks, otherSubtasks);
    }

    @Test
    public void equals() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("b", "a");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList otherSubtaskList = new UniqueSubtaskList(otherSubtasks);

        assertNotEquals(subtaskList, otherSubtaskList);
        assertEquals(subtaskList, subtaskList);
    }

    @Test
    public void equalsOrderInsensitive_true() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("b", "a");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList otherSubtaskList = new UniqueSubtaskList(otherSubtasks);

        assertTrue(subtaskList.equalsOrderInsensitive(otherSubtaskList));
    }

    @Test
    public void equalsOrderInsensitive_false() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("c", "a");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList otherSubtaskList = new UniqueSubtaskList(otherSubtasks);

        assertFalse(subtaskList.equalsOrderInsensitive(otherSubtaskList));
    }

    @Test
    public void hash_same() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");
        List<Subtask> otherSubtasks = SampleDataUtil.getSubtaskList("a", "c");
        UniqueSubtaskList subtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList otherSubtaskList = new UniqueSubtaskList(subtasks);
        UniqueSubtaskList diffSubtaskList = new UniqueSubtaskList(otherSubtasks);

        assertEquals(subtaskList.hashCode(), otherSubtaskList.hashCode());
        assertEquals(subtaskList.hashCode(), subtaskList.hashCode());
        assertNotEquals(subtaskList.hashCode(), diffSubtaskList.hashCode());
    }
}
```
###### /java/seedu/organizer/model/task/StatusTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StatusTest {

    @Test
    public void comparator_allCombination() {
        Status sTrue1 = new Status(true);
        Status sTrue2 = new Status(true);
        Status sFalse2 = new Status(false);
        Status sFalse1 = new Status(false);

        assertTrue(sTrue1.equals(sTrue1));
        assertTrue(sTrue1.equals(sTrue2));
        assertFalse(sTrue1.equals(sFalse2));

        assertTrue(sFalse2.equals(sFalse2));
        assertTrue(sFalse2.equals(sFalse1));
        assertFalse(sFalse2.equals(sTrue1));
    }

    public void getInverse_allCombination() {
        Status sTrue = new Status(true);
        Status sFalse = new Status(false);

        assertEquals(sTrue.getInverse(), sFalse);
        assertEquals(sFalse.getInverse(), sTrue);
    }
}
```
###### /java/seedu/organizer/storage/XmlAdaptedSubtaskTest.java
``` java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.util.SampleDataUtil;
import seedu.organizer.testutil.Assert;

public class XmlAdaptedSubtaskTest {

    public static final String NAME = "Jennifer";
    public static final boolean STATUS = false;

    public static final String OTHER_NAME = "Jessica";

    @Test
    public void equal_defaultconstructor() {
        XmlAdaptedSubtask subtask = new XmlAdaptedSubtask(NAME, STATUS);
        XmlAdaptedSubtask otherSubtask = new XmlAdaptedSubtask(NAME, STATUS);
        assertEquals(subtask, otherSubtask);
        assertEquals(subtask, subtask);

        XmlAdaptedSubtask diffSubtask = new XmlAdaptedSubtask(OTHER_NAME, STATUS);
        assertNotEquals(subtask, diffSubtask);
    }

    @Test
    public void equal_subtaskconstructor() {
        List<Subtask> subtasks = SampleDataUtil.getSubtaskList("a", "b");

        XmlAdaptedSubtask subtask = new XmlAdaptedSubtask(subtasks.get(0));
        XmlAdaptedSubtask otherSubtask = new XmlAdaptedSubtask(subtasks.get(0));
        assertEquals(subtask, otherSubtask);
        assertEquals(subtask, subtask);

        XmlAdaptedSubtask diffSubtask = new XmlAdaptedSubtask(subtasks.get(1));
        assertNotEquals(subtask, diffSubtask);
    }

    @Test
    public void toModel_invalidName() {
        Assert.assertThrows(
            IllegalValueException.class, () -> new XmlAdaptedSubtask("", false).toModelType()
        );
    }

}
```
###### /java/seedu/organizer/testutil/TaskBuilder.java
``` java
    /**
     * Parses the {@code subtask} into a {@code List<Subtask>} and set it to the {@code Task} that we are building.
     */
    public TaskBuilder withSubtask(String... subtask) {
        this.subtasks = SampleDataUtil.getSubtaskList(subtask);
        return this;
    }
```
