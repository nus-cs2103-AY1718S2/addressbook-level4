package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.CalendarGoBackwardEvent;

//@@author jlks96
public class GoBackwardCommandTest {
    private boolean calendarGoBackwardEventRaised;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @Before
    public void setUp() {
        calendarGoBackwardEventRaised = false;
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute_canGoBackward_success() {
        GoBackwardCommand command = new GoBackwardCommand();
        String expectedMessage = GoBackwardCommand.MESSAGE_SUCCESS;
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(true, calendarGoBackwardEventRaised);
    }

    /**
     * Handles the event where the user is trying to make the calendar view go backward in time from the currently
     * displaying date
     */
    @Subscribe
    private void handleCalendarGoBackwardEvent(CalendarGoBackwardEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        calendarGoBackwardEventRaised = true;
    }
}
