package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.CalendarViewEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author chenxing1992
/**
 * Command to change calendar view
 */
public class CalendarViewCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes calendar view. \n"
            + COMMAND_ALIAS + ": Short hand equivalent for calendar. \n"
            + "Parameter: \n"
            + "Day view: d\n"
            + "Week view: w\n"
            + "Month view: m\n"
            + "Year view: y\n";

    public static final String MESSAGE_SUCCESS = "View changed.";

    private Character arg;
    //@@author chenxing1992
    public CalendarViewCommand(Character c) {
        this.arg = c;
    }
    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new CalendarViewEvent(arg));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
