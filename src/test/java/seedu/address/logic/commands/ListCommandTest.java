package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ShowActivityRequestEvent;
import seedu.address.commons.events.ui.ShowTaskOnlyRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {
//may need revising, is the interaction with the model needed?
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    public ExpectedException thrown = ExpectedException.none();

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
        ListCommand command = new ListCommand("task");
        assertCommandSuccess(command, ListCommand.MESSAGE_SUCCESS_TASK);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowTaskOnlyRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_helpForEvent_success()    {
        ListCommand command = new ListCommand();
        assertCommandSuccess(command, ListCommand.MESSAGE_SUCCESS);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowActivityRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_invalidArgs_throwsCommandException() throws Exception   {
        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_LIST_REQUEST, "3"));
        ListCommand command = new ListCommand("3");
    }
}
