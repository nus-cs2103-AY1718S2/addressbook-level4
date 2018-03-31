package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ToggleNotificationCenterEvent;

/**
 * Toggles the notification center.
 */
public class NotiCommand extends Command {

    public static final String COMMAND_WORD = "noti";

    public static final String MESSAGE_SUCCESS = "";


    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ToggleNotificationCenterEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
