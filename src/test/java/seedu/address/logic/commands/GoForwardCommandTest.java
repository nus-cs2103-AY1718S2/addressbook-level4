package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.CalendarGoForwardEvent;

//@@author jlks96
public class GoForwardCommandTest {
    private boolean calendarGoForwardEventRaised;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @Before
    public void setUp() {
        calendarGoForwardEventRaised = false;
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute_canGoForward_success() {
        GoForwardCommand command = new GoForwardCommand();
        String expectedMessage = GoForwardCommand.MESSAGE_SUCCESS;
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(true, calendarGoForwardEventRaised);
    }

    /**
     * Handles the event where the user is trying to make the calendar view go forward in time from the currently
     * displaying date
     */
    @Subscribe
    private void handleCalendarGoForwardEvent(CalendarGoForwardEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        calendarGoForwardEventRaised = true;
    }
}
