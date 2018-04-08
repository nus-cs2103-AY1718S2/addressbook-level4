package seedu.address.logic.commands;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.logic.ZoomInEvent;
import seedu.address.commons.events.ui.MaxZoomInEvent;
import seedu.address.commons.events.ui.ZoomSuccessEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author jlks96
/**
 * Zooms in the calendar view to show a more detailed view
 */
public class ZoomInCommand extends Command {
    public static final String COMMAND_WORD = "zoomin";
    public static final String COMMAND_ALIAS = "zi";

    public static final String MESSAGE_SUCCESS = "Calendar zoomed in";
    public static final String MESSAGE_MAX_ZOOM_IN = "The calendar is already zoomed in to the maximum level";

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private boolean receivedFeedback = false;
    private boolean isSuccessful = false;

    @Override
    public CommandResult execute() throws CommandException {
        registerAsAnEventHandler(this);
        raise(new ZoomInEvent());
        while (!receivedFeedback);

        if (isSuccessful) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(MESSAGE_MAX_ZOOM_IN);
        }
    }

    /**
     * Handles the event where the calendar is already zoomed in to the maximum level
     */
    @Subscribe
    private void handleMaxZoomInEvent(MaxZoomInEvent event) {
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
