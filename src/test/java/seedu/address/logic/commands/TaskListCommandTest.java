package seedu.address.logic.commands;

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

/**
 * Contains integration tests (interaction with the Model) and unit tests for TaskListCommand.
 */
public class TaskListCommandTest {

    private Model model;
    private Model expectedModel;
    private TaskListCommand taskListCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventBook(), new UserPrefs());

        taskListCommand = new TaskListCommand();
        taskListCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(taskListCommand, model, TaskListCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
