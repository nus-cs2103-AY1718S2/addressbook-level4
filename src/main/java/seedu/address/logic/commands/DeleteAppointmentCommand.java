package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDTIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTTIME;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;

//@@author jlks96
/**
 * Deletes an appointment that matches all the input fields from the address book.
 */
public class DeleteAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteappointment";
    public static final String COMMAND_ALIAS = "da";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the appointment that matches all the input fields from the address book.\n"
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

    public static final String MESSAGE_DELETE_APPT_SUCCESS = "Deleted Appointment: %1$s";

    private Appointment appointmentToDelete;

    public DeleteAppointmentCommand(Appointment appointmentToDelete) {
        this.appointmentToDelete = appointmentToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(appointmentToDelete);
        try {
            model.deleteAppointment(appointmentToDelete);
        } catch (AppointmentNotFoundException anfe) {
            throw new CommandException(Messages.MESSAGE_APPT_NOT_FOUND);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_APPT_SUCCESS, appointmentToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppointmentCommand // instanceof handles nulls
                && this.appointmentToDelete.equals(((DeleteAppointmentCommand) other).appointmentToDelete));
    }
}
