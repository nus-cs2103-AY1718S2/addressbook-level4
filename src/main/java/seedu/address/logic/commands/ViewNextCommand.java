package seedu.address.logic.commands;
//@@author SuxianAlicia
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_NEXT;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches current page of Calendar to next page.
 * Depending on the current viewing format of Calendar, the next page can be the next day, next week
 * or next month of the current displayed date.
 * This command will display the calendar if it is not displayed when command is executed.
 */
public class ViewNextCommand extends Command {
    public static final String COMMAND_WORD = "calendarnext";
    public static final String COMMAND_ALIAS = "calnext";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays next page of current displayed date in calendar.\n"
            + "Depending on the current viewing format of Calendar, the next page can be the next day,"
            + " next week or next month of the current displayed date.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_VIEW_CALENDAR_NEXT_SUCCESS = "Displayed next page in Calendar.";

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeCalendarPageRequestEvent(REQUEST_NEXT));
        return new CommandResult(MESSAGE_VIEW_CALENDAR_NEXT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewNextCommand); // instanceof handles nulls
    }
}
