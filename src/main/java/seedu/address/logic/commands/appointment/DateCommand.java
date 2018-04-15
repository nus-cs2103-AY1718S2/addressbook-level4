package seedu.address.logic.commands.appointment;

import java.time.LocalDate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowDateRequestEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

//@@author trafalgarandre
/**
 * Change view of calendar to specific date.
 */
public class DateCommand extends Command {
    public static final String COMMAND_WORD = "date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View date. "
            + "Parameters: DATE (optional, but must be a valid date in format YYYY-MM-DD if have)\n"
            + "Example: " + COMMAND_WORD + " 2018-03-26";

    public static final String MESSAGE_SUCCESS = "View date: %1$s";
    public static final String DATE_VALIDATION_REGEX =
            "^$|^[1-3][0-9][0-9][0-9]-(1[0-2]|0[1-9])-(0[1-9]|[1-2][0-9]|3[0-1])";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date needs to be null or a valid date in format YYYY-MM-DD";

    private final LocalDate date;

    /**
     * Creates an DateCommand to view the specified {@code Date} or current if null
     */
    public DateCommand(LocalDate date) {
        this.date = date;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowDateRequestEvent(date));

        if (date != null) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, date));
        } else {
            return new CommandResult(String.format(MESSAGE_SUCCESS, ""));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateCommand // instanceof handles nulls
                && ((date == null && ((DateCommand) other).date == null)
                || (date != null && ((DateCommand) other).date != null && date.equals(((DateCommand) other).date))));

    }
}
