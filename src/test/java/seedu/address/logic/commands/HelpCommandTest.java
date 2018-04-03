package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class HelpCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        HelpCommand command = new HelpCommand();
        assertCommandSuccess(command, SHOWING_HELP_MESSAGE);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_helpForTask_success() {
        HelpCommand command = new HelpCommand("task");
        assertCommandSuccess(command, TaskCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForEvent_success() {
        HelpCommand command = new HelpCommand("event");
        assertCommandSuccess(command, EventCommand.MESSAGE_USAGE);
    }

    @Test
    public void execute_helpForRemove_success()    {
        HelpCommand command = new HelpCommand("rm");
        assertCommandSuccess(command, RemoveCommand.MESSAGE_USAGE);;
    }

    @Test
    public void execute_helpForComplete_success()    {
        HelpCommand command = new HelpCommand("complete");
        assertCommandSuccess(command, CompleteCommand.MESSAGE_USAGE);;
    }

    @Test
    public void execute_invalidArgs_throwsCommandException()   {
        HelpCommand command = new HelpCommand("hello");
        assertCommandFailure(command, HelpCommand.MESSAGE_USAGE);
    }

}
