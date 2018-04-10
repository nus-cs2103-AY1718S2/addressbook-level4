package seedu.address.logic.commands;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ZoomOutEvent;
import seedu.address.commons.events.ui.MaxZoomOutEvent;
import seedu.address.commons.events.ui.ZoomSuccessEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author jlks96
/**
 * Zooms out the calendar view to show a more general view
 */
public class ZoomOutCommand extends Command {
    public static final String COMMAND_WORD = "zoomout";
    public static final String COMMAND_ALIAS = "zo";

    public static final String MESSAGE_SUCCESS = "Calendar zoomed out";
    public static final String MESSAGE_MAX_ZOOM_OUT = "The calendar is already zoomed out to the maximum level";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private boolean receivedFeedback = false;
    private boolean isSuccessful = false;

    @Override
    public CommandResult execute() throws CommandException {
        registerAsAnEventHandler(this);
        raise(new ZoomOutEvent());
        while (!receivedFeedback);

        if (isSuccessful) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_MAX_ZOOM_OUT);
        }
    }

    /**
     * Handles the event where the calendar is already zoomed out to the maximum level
     */
    @Subscribe
    private void handleMaxZoomOutEvent(MaxZoomOutEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        receivedFeedback = true;
        isSuccessful = false;
    }

    /**
     * Handles the event where the calendar is successfully zoomed in
     */
    @Subscribe
    private void handleZoomSuccessEvent(ZoomSuccessEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        receivedFeedback = true;
        isSuccessful = true;
    }
}
