package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ZoomOutEvent;

//@@author jlks96
public class ZoomOutCommandTest {
    private boolean eventRaised;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @Before
    public void setUp() {
        eventRaised = false;
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute() {
        ZoomOutCommand command = new ZoomOutCommand();
        String expectedMessage = ZoomOutCommand.MESSAGE_SUCCESS;
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(true, eventRaised);
    }

    /**
     * Handles the event where the user is trying to zoom out on the calendar
     */
    @Subscribe
    private void handleZoomOutEvent(ZoomOutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventRaised = true;
    }
}
