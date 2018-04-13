package seedu.address.logic.commands;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.ui.util.CalendarFxUtil.DAY_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.MONTH_VIEW;
import static seedu.address.ui.util.CalendarFxUtil.WEEK_VIEW;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarViewRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays Calendar in ContactSails in 3 possible viewing formats, Day, Week or Month.
 */
public class ViewCalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "[VIEW_FORMAT]";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays Calendar in a specified viewing format.\n"
            + "Parameters: [VIEW_FORMAT] (must be either \"day\", \"week\" or \"month\" without captions)\n"
            + "If no parameters are given or given parameter does not follow the accepted keywords,"
            + " calendar will display in Day-View.\n"
            + "Example: " + COMMAND_WORD + " day";

    public static final String MESSAGE_SHOW_CALENDAR_SUCCESS = "Display Calendar in %1$s-View.";


    private final String view;

    public ViewCalendarCommand(String view) {
        requireNonNull(view);
        String trimmedView = view.trim();

        if (trimmedView.equalsIgnoreCase(MONTH_VIEW)) {
            this.view = MONTH_VIEW;
        } else if (trimmedView.equalsIgnoreCase(WEEK_VIEW)) {
            this.view = WEEK_VIEW;
        } else { //If view is equal to DAY_VIEW, is empty or does not match any of the accepted keywords
            this.view = DAY_VIEW;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(view);
        EventsCenter.getInstance().post(new ChangeCalendarViewRequestEvent(view));
        return new CommandResult(String.format(MESSAGE_SHOW_CALENDAR_SUCCESS, view));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCalendarCommand
                && this.view.equals(((ViewCalendarCommand) other).view)); // instanceof handles nulls
    }

}
