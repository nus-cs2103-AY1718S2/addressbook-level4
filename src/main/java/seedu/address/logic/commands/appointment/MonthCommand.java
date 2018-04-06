package seedu.address.logic.commands.appointment;

import java.time.YearMonth;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMonthRequestEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

//@@author trafalgarandre
/**
 * Change view of calendar to specific month.
 */
public class MonthCommand extends Command {
    public static final String COMMAND_WORD = "month";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View month. "
            + "Parameters: MONTH (optional, but must be in format YYYY-MM if have)\n"
            + "Example: " + COMMAND_WORD + " 2018-03";

    public static final String MESSAGE_SUCCESS = "View month: %1$s";
    public static final String YEAR_MONTH_VALIDATION_REGEX = "^$|^[1-3][0-9][0-9][0-9]-(1[0-2]|0[1-9])";
    public static final String MESSAGE_YEAR_MONTH_CONSTRAINTS = "Month needs to be null or in format YYYY-MM";

    private final YearMonth yearMonth;

    /**
     * Creates an MonthCommand to view the specified {@code yearMonth} or current if null
     */
    public MonthCommand(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowMonthRequestEvent(yearMonth));
        if (yearMonth != null) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, yearMonth));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, ""));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MonthCommand // instanceof handles nulls
                && ((yearMonth == null && ((MonthCommand) other).yearMonth == null)
                || (yearMonth != null && ((MonthCommand) other).yearMonth != null
                && yearMonth.equals(((MonthCommand) other).yearMonth))));
    }
}
