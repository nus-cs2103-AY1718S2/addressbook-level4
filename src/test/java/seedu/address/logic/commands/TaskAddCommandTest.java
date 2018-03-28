package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.Task;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code TaskAddCommand}.
 */
public class TaskAddCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() throws Exception {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), getTypicalEventBook(), new UserPrefs());
        expectedModel.addTask(validTask);

        assertCommandSuccess(prepareCommand(validTask, model), model,
                String.format(TaskAddCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    public void execute_duplicateTask_throwsCommandException() {
        Task taskInList = model.getAddressBook().getTaskList().get(0);
        assertCommandFailure(prepareCommand(taskInList, model), model, TaskAddCommand.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void equals() {
        Task taskone = new TaskBuilder().withTaskName("Taskone").build();
        Task tasktwo = new TaskBuilder().withTaskName("Tasktwo").build();
        TaskAddCommand addTaskOneCommand = new TaskAddCommand(taskone);
        TaskAddCommand addTaskTwoCommand = new TaskAddCommand(tasktwo);

        // same object -> returns true
        assertTrue(addTaskOneCommand.equals(addTaskOneCommand));

        // same values -> returns true
        TaskAddCommand addTaskOneCommandCopy = new TaskAddCommand(taskone);
        assertTrue(addTaskOneCommand.equals(addTaskOneCommandCopy));

        // different types -> returns false
        assertFalse(addTaskOneCommand.equals(5));

        // null -> returns false
        assertFalse(addTaskOneCommand.equals(null));

        // different task -> returns false
        assertFalse(addTaskOneCommand.equals(addTaskTwoCommand));
    }

    /**
     * Generates a new {@code TaskAddCommand} which upon execution, adds {@code task} into the {@code model}.
     */
    private TaskAddCommand prepareCommand(Task task, Model model) {
        TaskAddCommand command = new TaskAddCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
