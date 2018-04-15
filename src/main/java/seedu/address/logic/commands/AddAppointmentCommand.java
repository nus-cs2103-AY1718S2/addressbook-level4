//@@author Kyholmes
package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.DateTime;
import seedu.address.model.appointment.UniqueAppointmentEntryList;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;

/**
 * Add patient appointment
 */
public class AddAppointmentCommand extends Command {
    public static final String COMMAND_WORD = "addappt";
    public static final String COMMAND_ALIAS = "aa";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a patient appointment. "
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "DATE TIME (24-hour clock) \n "
            + "*DATE must be after today's date \n"
            + "Example: " + COMMAND_WORD + " 1 2/4/2018 1300";

    public static final String MESSAGE_SUCCESS = "A new appointment is added.\n"
            + "Enter view appointment command to get updated calendar view.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exist.";
    private final Index targetPatientIndex;
    private final DateTime dateTime;

    public AddAppointmentCommand(Index targetPatientIndex, DateTime dateTime) {
        requireAllNonNull(targetPatientIndex, dateTime);
        this.targetPatientIndex = targetPatientIndex;

        this.dateTime = dateTime;
    }

    @Override
    public CommandResult execute() throws CommandException {

        preprocess();

        Patient patientFound = model.getPatientFromListByIndex(targetPatientIndex);

        try {
            model.addPatientAppointment(patientFound, dateTime);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UniqueAppointmentList.DuplicatedAppointmentException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        } catch (UniqueAppointmentEntryList.DuplicatedAppointmentEntryException e) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
    }

    /**
     * Preprocess checking if index is valid and not out of bound of the patient list
     */
    private void preprocess() throws CommandException {
        List<Patient> lastShownList = model.getFilteredPersonList();

        if (targetPatientIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddAppointmentCommand
                && this.targetPatientIndex.equals(((AddAppointmentCommand) other).targetPatientIndex)
                && this.dateTime.equals(((AddAppointmentCommand) other).dateTime));
    }
}
