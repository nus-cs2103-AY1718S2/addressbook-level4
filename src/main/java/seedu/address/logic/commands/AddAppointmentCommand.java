package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWNER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

/**
 * Adds an appointment.
 */
public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "appointment";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment. "
            + "Parameters: "
            + PREFIX_OWNER + "OWNER "
            + PREFIX_REMARK + "REMARK "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_TAG + "TYPE OF APPOINTMENT]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_OWNER + "John Doe "
            + PREFIX_REMARK + "nil "
            + PREFIX_DATE + "2018-12-31 12:30 "
            + PREFIX_TAG + "checkup "
            + PREFIX_TAG + "vaccination";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This particular appointment already exists";

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
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AddAppointmentCommand) other).toAdd));
    }
}