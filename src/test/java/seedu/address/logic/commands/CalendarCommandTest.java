//@@author ifalluphill-reused
//{Based on HelpCommandTest}
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CalendarCommand.MESSAGE_SHOWING_CALENDAR;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowCalendarRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

public class CalendarCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new CalendarCommand().execute();
        assertEquals(MESSAGE_SHOWING_CALENDAR, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowCalendarRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}

//@@author
