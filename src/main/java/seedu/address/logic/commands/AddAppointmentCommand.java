package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.ClashingAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

//@@author jlks96
/**
 * Adds an appointment to the address book.
 */
public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addappointment";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE (must be in the format: dd/MM/yyyy) "
            + PREFIX_STARTTIME + "STARTTIME (must be in the 24 format: HH:mm) "
            + PREFIX_ENDTIME + "ENDTIME (must be in the 24 format: HH:mm) "
            + PREFIX_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "03/04/2018 "
            + PREFIX_STARTTIME + "10:30 "
            + PREFIX_ENDTIME + "11:30 "
            + PREFIX_LOCATION + "Century Garden";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the address book";
    public static final String MESSAGE_CLASHING_APPOINTMENT =
            "This appointment clashes with another appointment in the address book";

    private final Appointment toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addAppointment(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (ClashingAppointmentException e) {
            throw new CommandException(MESSAGE_CLASHING_APPOINTMENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd)); // state check
    }
}
