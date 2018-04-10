package seedu.address.logic.commands;

import seedu.address.commons.events.logic.CalendarGoForwardEvent;

//@@author jlks96
/**
 * Makes the calendar view go forward in time from the currently displaying date
 */
public class GoForwardCommand extends Command {

    public static final String COMMAND_WORD = "goforward";
    public static final String COMMAND_ALIAS = "gf";

    public static final String MESSAGE_SUCCESS = "Calendar view moved forward in time";

    @Override
    public CommandResult execute() {
        raise(new CalendarGoForwardEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
