package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author Kyomian
public class ClearCommandTest {

    @Test
    public void clearAll_emptyDeskBoard_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareClearAllCommand(model), model, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void clearAllTasks_emptyDeskBoard_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareClearTasksCommand(model), model, ClearCommand.MESSAGE_CLEAR_TASK_SUCCESS, model);
    }

    @Test
    public void clearAll_nonEmptyDeskBoard_success() {
        Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        assertCommandSuccess(prepareClearAllCommand(model), model, ClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void clearAllTasks_nonEmptyDeskBoard_success() {
        Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        assertCommandSuccess(prepareClearTasksCommand(model), model, ClearCommand.MESSAGE_CLEAR_TASK_SUCCESS, model);
    }

    @Test
    public void clearAllEvents_nonEmptyDeskBoard_success() {
        Model model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        assertCommandSuccess(prepareClearEventsCommand(model), model, ClearCommand.MESSAGE_CLEAR_EVENT_SUCCESS, model);
    }

    /**
     * Generates a new {@code ClearCommand} which upon execution,
     * clears all the content in {@code model}.
     */
    private ClearCommand prepareClearAllCommand(Model model) {
        ClearCommand command = new ClearCommand("");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code ClearCommand} to clear all tasks in {@code model}.
     */
    private ClearCommand prepareClearTasksCommand(Model model) {
        ClearCommand command = new ClearCommand("task");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code ClearCommand} to clear all events in {@code model}.
     */
    private ClearCommand prepareClearEventsCommand(Model model) {
        ClearCommand command = new ClearCommand("event");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
