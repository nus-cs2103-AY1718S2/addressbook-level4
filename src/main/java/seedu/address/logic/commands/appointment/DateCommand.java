package seedu.address.logic.commands.appointment;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowDateRequestEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

import java.time.LocalDate;

//@@author trafalgarandre
/**
 * Change view of calendar to specific date.
 */
public class DateCommand extends Command {
    public static final String COMMAND_WORD = "date";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View date. "
            + "Parameters: DATE (optional, but must be in format YYYY-MM-DD if have)\n"
            + "Example: " + COMMAND_WORD + " 2018-03-26";

    public static final String MESSAGE_SUCCESS = "View date: %1$s";
    public static final String DATE_VALIDATION_REGEX = "^$|^\\d{4}-\\d{2}-\\d{2}";
    public static final String MESSAGE_DATE_CONSTRAINTS = "Date needs to be null or in format YYYY-MM-DD";

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

        return new CommandResult(String.format(MESSAGE_SUCCESS, date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateCommand // instanceof handles nulls
                && date.equals(((DateCommand) other).date));
    }
}
