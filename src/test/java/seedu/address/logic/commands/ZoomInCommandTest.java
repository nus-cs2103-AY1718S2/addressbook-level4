package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ZoomInCommand.MESSAGE_MAX_ZOOM_IN;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ZoomInEvent;
import seedu.address.commons.events.ui.MaxZoomInEvent;
import seedu.address.commons.events.ui.ZoomSuccessEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author jlks96
public class ZoomInCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private boolean zoomInEventRaised;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @Before
    public void setUp() {
        zoomInEventRaised = false;
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute_canZoomIn_success() throws CommandException {
        ZoomSuccessEventRaiser eventRaiser = new ZoomSuccessEventRaiser();
        EventsCenter.getInstance().registerHandler(eventRaiser);
        ZoomInCommand command = new ZoomInCommand();
        String expectedMessage = ZoomInCommand.MESSAGE_SUCCESS;
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(true, zoomInEventRaised);
    }

    @Test
    public void execute_maxZoomIn_throwsCommandException() throws CommandException {
        MaxZoomInEventRaiser eventRaiser = new MaxZoomInEventRaiser();
        EventsCenter.getInstance().registerHandler(eventRaiser);
        ZoomInCommand command = new ZoomInCommand();
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_MAX_ZOOM_IN);
        command.execute();
    }

    /**
     * Handles the event where the user is trying to zoom in on the calendar
     */
    @Subscribe
    private void handleZoomInEvent(ZoomInEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        zoomInEventRaised = true;
    }

    /**
     * An event raiser that raises a {@code MaxZoomInEvent} when handling a {@code ZoomInEvent}.
     */
    private class MaxZoomInEventRaiser {
        /**
         * Raises a {@code MaxZoomInEvent}
         */
        @Subscribe
        private void handleZoomInEvent(ZoomInEvent event) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            EventsCenter.getInstance().post(new MaxZoomInEvent());
        }
    }

    /**
     * An event raiser that raises a {@code ZoomSuccessEvent} when handling a {@code ZoomInEvent}.
     */
    private class ZoomSuccessEventRaiser {
        /**
         * Raises a {@code ZoomSuccessEvent}
         */
        @Subscribe
        private void handleZoomInEvent(ZoomInEvent event) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            EventsCenter.getInstance().post(new ZoomSuccessEvent());
        }
    }
}
