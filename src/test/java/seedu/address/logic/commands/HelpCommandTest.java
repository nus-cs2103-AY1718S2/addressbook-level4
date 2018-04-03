package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        CommandResult result = new HelpCommand().execute();
        assertEquals(SHOWING_HELP_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowHelpRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    //@@author Kyomian
//    @Test
//    public void execute_helpForHelp_success() {
//        HelpCommand command = new HelpCommand("help");
//        assertCommandSuccess(command, HelpCommand.MESSAGE_USAGE);
//    }
//
//    @Test
//    public void execute_helpForMan_success() {
//        HelpCommand command = new HelpCommand("man");
//        assertCommandSuccess(command, HelpCommand.MESSAGE_USAGE);
//    }
}
