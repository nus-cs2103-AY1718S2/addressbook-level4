package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DisplayCalendarRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays Calendar in App.
 */
public class ViewCalendarCommand extends Command {
    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";

    /* Not Implemented yet.
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + "INDEX"; */
    public static final String COMMAND_SYNTAX = COMMAND_WORD;

    /* Not Implemented yet.
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays Calendar in a specified format.\n"
            + "Parameters: [VIEW_FORMAT] (must be either \"week\" or \"month\")\n"
            + "If no parameters are given, calendar will display in Month-View.\n"
            + "Example: " + COMMAND_WORD + " month"; */

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays Calendar in Month-View format.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SHOW_CALENDAR_SUCCESS = "Displayed Calendar in Month-View.";

    /* Not Implemented yet.
    public static final String MESSAGE_SHOW_CALENDAR_SUCCESS = "Display Calendar in %1$s-View.";
    */

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new DisplayCalendarRequestEvent(model.getFilteredCalendarEventList()));
        return new CommandResult(MESSAGE_SHOW_CALENDAR_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCalendarCommand); // instanceof handles nulls
    }

}
