package seedu.address.logic.commands.appointment;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;

//@@author trafalgarandre
/**
 * delete appointment from calendar of addressbook
 */
public class DeleteAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "delapp";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_TITLE + " "
            + PREFIX_START_DATE_TIME + " "
            + PREFIX_END_DATE_TIME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment to calendar. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_START_DATE_TIME + "START DATE TIME "
            + PREFIX_END_DATE_TIME + "END DATE TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Birthday "
            + PREFIX_START_DATE_TIME + "2018-03-26 12:00 "
            + PREFIX_END_DATE_TIME + "2018-03-26 12:30 ";

    public static final String MESSAGE_SUCCESS = "Appointment deleted: %1$s";
    public static final String MESSAGE_NOT_FOUND_APPOINTMENT =
            "This appointment does not exist in the calendar or has already been deleted";

    private final Appointment toDelete;

    /**
     * Creates an DeleteAppointmentCommand to add the specified {@code Appointment}
     */
    public DeleteAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toDelete = appointment;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.deleteAppointment(toDelete);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
        } catch (AppointmentNotFoundException e) {
            throw new CommandException(MESSAGE_NOT_FOUND_APPOINTMENT);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAppointmentCommand // instanceof handles nulls
                && toDelete.equals(((DeleteAppointmentCommand) other).toDelete));
    }
}
