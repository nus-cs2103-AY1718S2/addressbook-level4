package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Task;
import seedu.address.testutil.TaskBuilder;

//@@author Kyomian
/**
 * Contains integration tests (interaction with the Model) for {@code TaskCommand}.
 */
public class TaskCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
    }

    @Test
    public void execute_newTask_success() throws Exception {
        Task validTask = new TaskBuilder().build();

        Model expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());
        expectedModel.addActivity(validTask);

        assertCommandSuccess(prepareCommand(validTask, model), model,
                String.format(TaskCommand.MESSAGE_SUCCESS, validTask), expectedModel);
    }

    @Test
    // Questionable - does the app check for duplicate task?
    public void execute_duplicateTask_throwsCommandException() {
        Activity activityInList = model.getDeskBoard().getActivityList().get(1);
        assertCommandFailure(prepareCommand((Task) activityInList, model), model, TaskCommand.MESSAGE_DUPLICATE_TASK);
    }

    /**
     * Generates a new {@code TaskCommand} which upon execution, adds {@code task} into the {@code model}.
     */
    private TaskCommand prepareCommand(Task task, Model model) {
        TaskCommand command = new TaskCommand(task);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
