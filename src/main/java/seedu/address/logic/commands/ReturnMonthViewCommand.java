package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.CalendarUnfocusEvent;
//@@author yuxiangSg
/**
 * return to month view for the calendar GUI.
 */
public class ReturnMonthViewCommand extends Command {
    public static final String COMMAND_WORD = "back";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unfocus the Calendar to Month view.\n"
            + "Example: " + COMMAND_WORD;

    public static final String RETURN_MONTH_VIEW_MESSAGE = "Calendar back to Month view";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new CalendarUnfocusEvent());
        return new CommandResult(RETURN_MONTH_VIEW_MESSAGE);
    }
}
