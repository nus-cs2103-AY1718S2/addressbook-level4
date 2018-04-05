package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayCalendarRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays Calendar in App.
 */
//@@author SuxianAlicia
public class ViewCalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";


    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "[VIEW_FORMAT]";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays Calendar in a specified format.\n"
            + "Parameters: [VIEW_FORMAT] (must be either \"day\", \"week\" or \"month\" without captions)\n"
            + "If no parameters are given or given parameter does not follow the required keywords,"
            + " calendar will display in Day-View.\n"
            + "Example: " + COMMAND_WORD + " day";

    public static final String MESSAGE_SHOW_CALENDAR_SUCCESS = "Display Calendar in %1$s-View.";


    public static final String MONTH_VIEW = "Month";
    public static final String DAY_VIEW = "Day";
    public static final String WEEK_VIEW = "Week";

    private final String view;

    public ViewCalendarCommand(String view) {
        String trimmedView = view.trim();
        requireNonNull(trimmedView);

        if (trimmedView.equalsIgnoreCase(MONTH_VIEW)) {
            this.view = MONTH_VIEW;
        } else if (trimmedView.equalsIgnoreCase(WEEK_VIEW)) {
            this.view = WEEK_VIEW;
        } else { //If user input is equal to DAY_VIEW or input does not conform to any of the required keywords
            this.view = DAY_VIEW;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(view);
        Optional<String> selectedView = Optional.of(view); //Guaranteed that view cannot be null

        EventsCenter.getInstance().post(new DisplayCalendarRequestEvent(selectedView));
        return new CommandResult(String.format(MESSAGE_SHOW_CALENDAR_SUCCESS, view));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCalendarCommand); // instanceof handles nulls
    }

}
