package seedu.address.logic.commands;
//@@author SuxianAlicia
import static seedu.address.ui.util.CalendarFxUtil.REQUEST_TODAY;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches currently displayed date in Calendar to today's date.
 * This command will display the calendar if it is not displayed when command is executed.
 */
public class ViewTodayCommand extends Command {

    public static final String COMMAND_WORD = "calendartoday";
    public static final String COMMAND_ALIAS = "caltoday";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays today's date in calendar.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_VIEW_CALENDAR_TODAY_SUCCESS = "Displayed Today in Calendar.";

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeCalendarPageRequestEvent(REQUEST_TODAY));
        return new CommandResult(MESSAGE_VIEW_CALENDAR_TODAY_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewTodayCommand); // instanceof handles nulls
    }
}
