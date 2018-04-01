package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
    //@@author jasmoon
    @Test
    public void execute_helpForTask_success() {
        CommandResult result = new ListCommand("task").execute();
        assertEquals(ListCommand.MESSAGE_SUCCESS_TASK, result.feedbackToUser);
    }

    @Test
    public void execute_helpForEvent_success()    {
        CommandResult result = new ListCommand("event").execute();
        assertEquals(ListCommand.MESSAGE_SUCCESS_EVENT, result.feedbackToUser);
    }

    @Test
    public void execute_helpForTaskAndEvent_success()    {
        CommandResult result = new ListCommand().execute();
        assertEquals(ListCommand.MESSAGE_SUCCESS, result.feedbackToUser);
    }

}
