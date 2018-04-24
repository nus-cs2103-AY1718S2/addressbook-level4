package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showActivityAtIndex;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowEventOnlyRequestEvent;
import seedu.address.commons.events.ui.ShowTaskOnlyRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
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

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalDeskBoard(), new UserPrefs());
        expectedModel = new ModelManager(model.getDeskBoard(), new UserPrefs());

        listCommand = new ListCommand("");
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * not sure if needed
     */
    public void execute_listIsFiltered_showsEverything() {
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    //@@author jasmoon
    @Test
    public void execute_listForTask_success() {
        ListCommand command = new ListCommand("task");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(command, ListCommand.MESSAGE_SUCCESS_TASK);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowTaskOnlyRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_listForEvent_success() {
        ListCommand command = new ListCommand("event");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandSuccess(command, ListCommand.MESSAGE_SUCCESS_EVENT);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowEventOnlyRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    /**
     * Note: Setup is mainView, calling ListCommand("") will not trigger a ShowActivityOnlyRequest Event.
     */
    @Test
    public void execute_listForActivity_success()    {
        ListCommand command = new ListCommand("");
        assertCommandSuccess(command, ListCommand.MESSAGE_SUCCESS);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 0);
    }

    @Test
    public void execute_invalidArgs_throwsCommandException() throws Exception   {
        ListCommand command = new ListCommand("hello");
        assertCommandFailure(command, String.format(ListCommand.MESSAGE_INVALID_LIST_REQUEST, "hello"));
    }
}
