package seedu.address.logic.commands.appointment;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowMonthRequestEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

import java.time.YearMonth;

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
    public static final String YEAR_MONTH_VALIDATION_REGEX = "^$|^\\d{4}-\\d{2}";
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

        return new CommandResult(String.format(MESSAGE_SUCCESS, yearMonth));
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
