package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.ZoomOutCommand.MESSAGE_MAX_ZOOM_OUT;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ZoomOutEvent;
import seedu.address.commons.events.ui.MaxZoomOutEvent;
import seedu.address.commons.events.ui.ZoomSuccessEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author jlks96
public class ZoomOutCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private boolean zoomOutEventRaised;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @Before
    public void setUp() {
        zoomOutEventRaised = false;
        EventsCenter.getInstance().registerHandler(this);
    }

    @Test
    public void execute_canZoomOut_success() throws CommandException {
        ZoomSuccessEventRaiser eventRaiser = new ZoomSuccessEventRaiser();
        EventsCenter.getInstance().registerHandler(eventRaiser);
        ZoomOutCommand command = new ZoomOutCommand();
        String expectedMessage = ZoomOutCommand.MESSAGE_SUCCESS;
        CommandResult result = command.execute();
        assertEquals(expectedMessage, result.feedbackToUser);
        assertEquals(true, zoomOutEventRaised);
    }

    @Test
    public void execute_maxZoomOut_throwsCommandException() throws CommandException {
        MaxZoomOutEventRaiser eventRaiser = new MaxZoomOutEventRaiser();
        EventsCenter.getInstance().registerHandler(eventRaiser);
        ZoomOutCommand command = new ZoomOutCommand();
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_MAX_ZOOM_OUT);
        command.execute();
    }

    /**
     * Handles the event where the user is trying to zoom out on the calendar
     */
    @Subscribe
    private void handleZoomOutEvent(ZoomOutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        zoomOutEventRaised = true;
    }

    /**
     * An event raiser that raises a {@code MaxZoomOutEvent} when handling a {@code ZoomOutEvent}.
     */
    private class MaxZoomOutEventRaiser {
        /**
         * Raises a {@code MaxZoomOutEvent}
         */
        @Subscribe
        private void handleZoomOutEvent(ZoomOutEvent event) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            EventsCenter.getInstance().post(new MaxZoomOutEvent());
        }
    }

    /**
     * An event raiser that raises a {@code ZoomSuccessEvent} when handling a {@code ZoomOutEvent}.
     */
    private class ZoomSuccessEventRaiser {
        /**
         * Raises a {@code ZoomSuccessEvent}
         */
        @Subscribe
        private void handleZoomOutEvent(ZoomOutEvent event) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            EventsCenter.getInstance().post(new ZoomSuccessEvent());
        }
    }
}
