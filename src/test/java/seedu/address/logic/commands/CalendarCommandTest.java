package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CalendarCommand.SHOWING_CALENDAR_MESSAGE;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ShowCalendarEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author wayneong95

public class CalendarCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_calendar_success() {
        CommandResult result = new CalendarCommand().execute();
        assertEquals(SHOWING_CALENDAR_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowCalendarEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
