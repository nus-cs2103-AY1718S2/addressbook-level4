package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SwitchTabRequestEvent;

//@@author trafalgarandre
/**
 * Switch tab to Calendar
 */
public class CalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_SUCCESS = "Opened your calendar";

    public static final int TAB_ID = 2;

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new SwitchTabRequestEvent(TAB_ID));
        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }

}
