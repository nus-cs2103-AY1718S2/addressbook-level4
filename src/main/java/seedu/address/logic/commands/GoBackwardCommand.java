package seedu.address.logic.commands;

import seedu.address.commons.events.logic.CalendarGoBackwardEvent;

//@@author jlks96
/**
 * Makes the calendar view go backward in time from the currently displaying date
 */
public class GoBackwardCommand extends Command {

    public static final String COMMAND_WORD = "gobackward";
    public static final String COMMAND_ALIAS = "gb";

    public static final String MESSAGE_SUCCESS = "Calendar view moved backward in time";

    @Override
    public CommandResult execute() {
        raise(new CalendarGoBackwardEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
