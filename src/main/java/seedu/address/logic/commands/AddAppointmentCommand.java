package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_INTERVAL;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.calendar.AppointmentEntry;
import seedu.address.model.calendar.exceptions.DuplicateAppointmentException;
//@@author yuxiangSg
/**
 * Adds a appointment to the address book's calendar.
 */

public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the calendar. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_INTERVAL + "START DATE TIME "
            + PREFIX_END_INTERVAL + "END DATE TIME"
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Meet John "
            + PREFIX_START_INTERVAL + "14/08/2018 06:12 "
            + PREFIX_END_INTERVAL + "14/08/2018 07:12 ";

    public static final String MESSAGE_SUCCESS = "New Appointment Added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT =
            "appointment with the same title already exists in the calendar";

    private final AppointmentEntry toAdd;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code AppointmentEntry}
     */
    public AddAppointmentCommand(AppointmentEntry appointmentEntry) {
        requireNonNull(appointmentEntry);
        toAdd = appointmentEntry;
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
