package seedu.address.logic.commands.appointment;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowWeekRequestEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

import java.time.Year;

//@@author trafalgarandre
/**
 * Change view of calendar to specific week.
 */
public class WeekCommand extends Command {
    public static final String COMMAND_WORD = "week";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View week. "
            + "Parameters: YEAR WEEK (optional, but must be in format YYYY WW if have)\n"
            + "Example: " + COMMAND_WORD + " 2018";

    public static final String MESSAGE_SUCCESS = "View week: %1$s";
    public static final String WEEK_VALIDATION_REGEX = "^$|^\\d{4}\\s\\d{2}";
    public static final String MESSAGE_WEEK_CONSTRAINTS = "Week needs to be null or in format YYYY DD";

    private final Year year;
    private final int week;

    /**
     * Creates an WeekCommand to view the specified {@code week, year} or current if null
     */
    public WeekCommand(Year year, int week) {
        this.year = year;
        this.week = week;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowWeekRequestEvent(year, week));
        return new CommandResult(String.format(MESSAGE_SUCCESS, week + " " + year));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WeekCommand // instanceof handles nulls
                && ((year == null && ((WeekCommand) other).year == null)
                || (year != null && ((WeekCommand) other).year != null && year.equals(((WeekCommand) other).year))
                && week == (((WeekCommand) other).week)));
    }
}
