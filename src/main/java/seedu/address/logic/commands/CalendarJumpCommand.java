package seedu.address.logic.commands;
//@@author SuxianAlicia
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET_DATE;

import java.time.LocalDate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeCalendarDateRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays specified date in Calendar.
 */
public class CalendarJumpCommand extends Command {

    public static final String COMMAND_WORD = "calendarjump";
    public static final String COMMAND_ALIAS = "caljump";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_TARGET_DATE + "TARGET_DATE";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays given date in Calendar.\n"
            + "Parameters: " + PREFIX_TARGET_DATE + "TARGET_DATE (must be in format DD-MM-YYY)\n"
            + "Example: " + COMMAND_WORD + " 06-06-2018";

    public static final String MESSAGE_CALENDAR_JUMP_SUCCESS = "Displayed Date: %1$s in Calendar.";

    private final LocalDate date;
    private final String dateString;

    /**
     * Creates CalendarJumpCommand with specified {@code LocalDate} to display in Calendar.
     */
    public CalendarJumpCommand(LocalDate date, String dateString) {
        requireAllNonNull(date, dateString);
        this.date = date;
        this.dateString = dateString;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(date);
        EventsCenter.getInstance().post(new ChangeCalendarDateRequestEvent(date));
        return new CommandResult(String.format(MESSAGE_CALENDAR_JUMP_SUCCESS, dateString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarJumpCommand // instanceof handles nulls
                && date.equals(((CalendarJumpCommand) other).date)
                && dateString.equals(((CalendarJumpCommand) other).dateString));
    }
}
