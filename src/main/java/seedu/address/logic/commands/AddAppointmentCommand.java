package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.UniqueAppointmentEntryList;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;

/**
 * Add patient appointment
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "addappt";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient appointment. "
            + "Parameters: "
            + "NAME "
            + "DATE "
            + "TIME";

    public static final String MESSAGE_SUCCESS = "A new appointment is added.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exist.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This patient cannot be found in the database.";
    private final NameContainsKeywordsPredicate predicate;
    private final String dateTimeString;

    public AddAppointmentCommand(NameContainsKeywordsPredicate predicate, String dateString, String timeString) {
        requireAllNonNull(predicate, dateString, timeString);
        this.predicate = predicate;

        this.dateTimeString = dateString + " " + timeString;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Patient patientFound = model.getPatientFromList(predicate);

        if (patientFound == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        try {
            model.addPatientAppointment(patientFound, dateTimeString);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UniqueAppointmentList.DuplicatedAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (UniqueAppointmentEntryList.DuplicatedAppointmentEntryException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAppointmentCommand
                && predicate.equals(((AddAppointmentCommand) other).predicate)
                && dateTimeString.equals(((AddAppointmentCommand) other).dateTimeString));
    }
}
