package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.person.Person;

/**
 * Creates an appointment for the student at the specified index.
 */
public class AppointmentCommand extends Command{

    public static final String COMMAND_WORD = "appointment";
    public static final String COMMAND_ALIAS = "appt";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a consultation appointment for the student "
            + "at the specified index.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE "
            + PREFIX_START_TIME + "START TIME "
            + PREFIX_END_TIME + "END TIME \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "28031998 "
            + PREFIX_START_TIME + "1500 "
            + PREFIX_END_TIME + "1600 \n"
            + "Example: " + COMMAND_ALIAS + " 1 "
            + PREFIX_DATE + "28031998 "
            + PREFIX_START_TIME + "1500 "
            + PREFIX_END_TIME + "1600 ";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in EduBuddy";

    private final Index index;
    private final Appointment toAdd;

    private Person selectedPerson;

    /**
     * Creates an AppointmentCommand to add the specified {@code Appointment}
     */
    public AppointmentCommand(Index index, Appointment appointment) {
        requireNonNull(index);
        requireNonNull(appointment);

        this.index = index;
        this.toAdd = appointment;
    }

    @Override
    public CommandResult execute() throws CommandException, IOException {
        requireNonNull(model);
        try {
            model.addAppointment(toAdd);
            getDetails();
            showEventOnCalendar();
            return new CommandResult(String.format(MESSAGE_SUCCESS, getDetails()));
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
    }

    public String getDetails() {
        List<Person> lastShownList = model.getFilteredPersonList();
        selectedPerson = lastShownList.get(index.getZeroBased());
        return toAdd.getStartTime() + " to " + toAdd.getEndTime() + " on " + toAdd.getDate()
                + " with " + selectedPerson.getName();
    }

    public void showEventOnCalendar() throws IOException {
        calendar.createEvent(toAdd, selectedPerson);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentCommand // instanceof handles nulls
                && toAdd.equals(((AppointmentCommand) other).toAdd));
    }
}

