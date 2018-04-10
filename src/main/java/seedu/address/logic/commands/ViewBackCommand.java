package seedu.address.logic.commands;
//@@author SuxianAlicia
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarPageRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches current page of Calendar to previous page.
 * Depending on the current viewing format of Calendar, the previous page can be the previous day, previous week,
 * or previous month of the current displayed date.
 * This command will display the calendar if it is not displayed when command is executed.
 */
public class ViewBackCommand extends Command {
    public static final String COMMAND_WORD = "calendarback";
    public static final String COMMAND_ALIAS = "calback";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays previous page of current displayed date in calendar.\n"
            + "Depending on the current viewing format of Calendar, the previous page can be the previous day,"
            + " previous week, or previous month of the current displayed date.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_VIEW_CALENDAR_BACK_SUCCESS = "Displayed previous page in Calendar.";
    public static final String REQUEST_BACK = "Back";

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new ChangeCalendarPageRequestEvent(REQUEST_BACK));
        return new CommandResult(MESSAGE_VIEW_CALENDAR_BACK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewBackCommand); // instanceof handles nulls
    }
}
