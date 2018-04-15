//@@author ifalluphill-reused
//{Based on HelpCommandTest}
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ErrorLogCommand.MESSAGE_SHOWING_ERRORLOG;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowErrorsRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

// Tests that the errorlog command opens the webview successfully.
public class ErrorLogCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new ErrorLogCommand().execute();
        assertEquals(MESSAGE_SHOWING_ERRORLOG, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowErrorsRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}

//@@author
