package seedu.address.logic.commands;

import java.time.YearMonth;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.JumpToCalendarRequestEvent;

//@@author x3tsunayh

/**
 * Jumps to user-defined month and year on the calendar
 */
public class JumpToCommand extends Command {
    public static final String COMMAND_WORD = "jumpto";
    public static final String COMMAND_ALIAS = "jt";
    public static final String JUMP_TO_MESSAGE = "Jumped to: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Jumps to a specified year (between 1900 to 2300) and month (between 01 to 12) on the calendar.\n"
            + "Parameters: yyyy-mm\n"
            + "Example: " + COMMAND_WORD + " 2018-02";

    private final YearMonth yearMonth;

    /**
     * Creates a JumpToCommand to jump to specified year and month of the calendar
     */
    public JumpToCommand(YearMonth yearMonthValue)  {
        yearMonth = yearMonthValue;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new JumpToCalendarRequestEvent(yearMonth));
        return new CommandResult(String.format(JUMP_TO_MESSAGE, yearMonth.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JumpToCommand // instanceof handles nulls
                && yearMonth.equals(((JumpToCommand) other).yearMonth));
    }
}
