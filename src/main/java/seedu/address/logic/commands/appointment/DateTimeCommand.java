package seedu.address.logic.commands.appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowDateTimeRequestEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

//@@author trafalgarandre
/**
 * Change view of calendar to specific dateTime.
 */
public class DateTimeCommand extends Command {
    public static final String COMMAND_WORD = "datetime";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View date time. "
            + "Parameters: DATE_TIME (but must be in format YYYY-MM-DD HH:mm)\n"
            + "Example: " + COMMAND_WORD + " 2018-03-26 12:00";

    public static final String MESSAGE_SUCCESS = "View datetime: %1$s";
    public static final String DATE_TIME_VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}";
    public static final String MESSAGE_DATE_TIME_CONSTRAINTS = "Date Time needs to be in format YYYY-MM-DD HH:mm";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final LocalDateTime dateTime;

    /**
     * Creates an DateTimeCommand to view the specified {@code DateTime} or current if null
     * @param dateTime
     */
    public DateTimeCommand(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowDateTimeRequestEvent(dateTime));

        return new CommandResult(String.format(MESSAGE_SUCCESS, dateTime.format(formatter)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTimeCommand // instanceof handles nulls
                && ((dateTime == null && ((DateTimeCommand) other).dateTime == null)
                || (dateTime != null
                && ((DateTimeCommand) other).dateTime != null && dateTime.equals(((DateTimeCommand) other).dateTime))));

    }
}
