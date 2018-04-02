package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ZoomInEvent;

//@@author jlks96
public class ZoomInCommandTest {
    private boolean eventRaised;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @Before
    public void setUp() {
        eventRaised = false;
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute() {
        ZoomInCommand command = new ZoomInCommand();
        String expectedMessage = ZoomInCommand.MESSAGE_SUCCESS;
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(true, eventRaised);
    }

    /**
     * Handles the event where the user is trying to zoom in on the calendar
     */
    @Subscribe
    private void handleZoomInEvent(ZoomInEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        eventRaised = true;
    }
}
