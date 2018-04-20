//@@author Kyholmes
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.DateTime;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;

/**
 * Delete a patient's appointment
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "delappt";
    public static final String COMMAND_ALIAS = "da";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a patient's appointment."
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "DATE TIME (24-hour clock) \n "
            + "Example: " + COMMAND_WORD + " 1 2/4/2018 1300";

    public static final String MESSAGE_DELETE_SUCCESS = "The appointment is canceled. \n "
            + "Enter view appointment command to get updated calendar view.";

    public static final String MESSAGE_APPOINTMENT_CANNOT_BE_FOUND = "Appointment cannot be found.";

    private final Index targetPatientIndex;

    private final DateTime targetAppointmentDateTime;

    public DeleteAppointmentCommand(Index targetPatientIndex, DateTime targetAppointmentDateTime) {
        this.targetPatientIndex = targetPatientIndex;
        this.targetAppointmentDateTime = targetAppointmentDateTime;
    }

    @Override
    public CommandResult execute() throws CommandException {

        preprocess();

        Patient patientFound = model.getPatientFromListByIndex(targetPatientIndex);

        try {
            model.deletePatientAppointment(patientFound, targetAppointmentDateTime);
            return new CommandResult(MESSAGE_DELETE_SUCCESS);
        } catch (UniqueAppointmentList.AppoinmentNotFoundException e) {
            throw new CommandException(MESSAGE_APPOINTMENT_CANNOT_BE_FOUND);
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
                || (other instanceof DeleteAppointmentCommand)
                && this.targetPatientIndex.equals(((DeleteAppointmentCommand) other).targetPatientIndex)
                && this.targetAppointmentDateTime.equals(((DeleteAppointmentCommand) other).targetAppointmentDateTime);
    }
}
