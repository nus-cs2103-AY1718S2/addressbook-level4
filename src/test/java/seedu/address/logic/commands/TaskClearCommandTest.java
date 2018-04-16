package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class TaskClearCommandTest {

    @Test
    public void execute_emptyTaskBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, TaskClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyTaskBook_success() {
        Model model =
                new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), getTypicalTaskBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, TaskClearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code TaskClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private TaskClearCommand prepareCommand(Model model) {
        TaskClearCommand command = new TaskClearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
