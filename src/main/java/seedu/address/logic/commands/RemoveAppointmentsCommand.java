package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEARCH_TEXT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.calendar.exceptions.AppointmentNotFoundException;

/**
 * removes appointments associated with the searchText in the address book's calendar.
 */

public class RemoveAppointmentsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove_appointments";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": remove appointments associated with the search text in the calendar. "
            + "Parameters: "
            + PREFIX_SEARCH_TEXT + "SEARCH TEXT "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_SEARCH_TEXT + "title of the appointment ";

    public static final String MESSAGE_SUCCESS = "Appointments with title %1$s removed";
    public static final String MESSAGE_NO_SUCH_APPOINTMENT = "No such appointment exists in the calendar";

    private final String searchText;

    /**
     * Creates an AddCommand to add the specified {@code AppointmentEntry}
     */
    public RemoveAppointmentsCommand(String searchText) {
        requireNonNull(searchText);
        this.searchText = searchText;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.removeAppointments(searchText);
            return new CommandResult(String.format(MESSAGE_SUCCESS, searchText));
        } catch (AppointmentNotFoundException e) {
            throw new CommandException(MESSAGE_NO_SUCH_APPOINTMENT);
        }
    }
}
