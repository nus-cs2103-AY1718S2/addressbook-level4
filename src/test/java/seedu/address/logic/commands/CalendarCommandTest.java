package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.SwitchTabRequestEvent;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author trafalgarandre
public class CalendarCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();


    @Test
    public void execute() {
        CalendarCommand calendarCommand = new CalendarCommand();

        // write success Message
        CommandResult commandResult = calendarCommand.execute();
        assertEquals(String.format(CalendarCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);

        // change to right tab
        SwitchTabRequestEvent lastEvent = (SwitchTabRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(CalendarCommand.TAB_ID, lastEvent.tabId);
    }
}
