package seedu.address.logic.commands.appointment;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

//@@author trafalgarandre
/**
 * Add appointment to calendar of addressbook
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "addapp";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_TITLE + " "
            + PREFIX_START_DATE_TIME + " "
            + PREFIX_END_DATE_TIME;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to calendar. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_START_DATE_TIME + "START_DATE_TIME "
            + PREFIX_END_DATE_TIME + "END_DATE_TIME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Birthday "
            + PREFIX_START_DATE_TIME + "2018-03-26 12:00 "
            + PREFIX_END_DATE_TIME + "2018-03-26 12:30 ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the calendar";

    private final Appointment toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    @Override
    public CommandResult execute() throws CommandException {
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
