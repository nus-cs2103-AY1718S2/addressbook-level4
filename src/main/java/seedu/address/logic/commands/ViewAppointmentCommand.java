//@@author Kyholmes
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowCalendarViewRequestEvent;
import seedu.address.commons.events.ui.ShowPatientAppointmentRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.patient.Patient;

/**
 * List all the patient appointments
 */
public class ViewAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "viewappt";
    public static final String COMMAND_ALIAS = "va";

    public static final String MESSAGE_USAGE_PATIENT_WITH_INDEX = COMMAND_WORD
            + ": View list of appointments of a patient. "
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all patient appointments. ";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";
    public static final String MESSAGE_SUCCESS_PATIENT = "Listed all appointments made by %1$s";
    private Index targetIndex;
    private final boolean indexArgumentIsExist;

    /**
     * Creates an ViewAppointmentCommand to view list of appointments
     */
    public ViewAppointmentCommand() {
        this.indexArgumentIsExist = false;
    }

    /**
     * Creates an ViewAppointmentCommand to view list of appointments the specified {@code Patient}
     */
    public ViewAppointmentCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.indexArgumentIsExist = true;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        preprocess();

        Patient targetPatient;

        if (indexArgumentIsExist) {
            targetPatient = model.getPatientFromListByIndex(targetIndex);
            EventsCenter.getInstance().post(new ShowPatientAppointmentRequestEvent(targetPatient));
            return new CommandResult(String.format(MESSAGE_SUCCESS_PATIENT, targetPatient.getName().fullName));
        } else {
            EventsCenter.getInstance().post(new ShowCalendarViewRequestEvent(model.getAppointmentEntryList()));
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Preprocess checking if index is valid and not out of bound of the patient list
     */
    private void preprocess() throws CommandException {
        if (indexArgumentIsExist) {
            List<Patient> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewAppointmentCommand
                && this.indexArgumentIsExist == ((ViewAppointmentCommand) other).indexArgumentIsExist
                && this.targetIndex.equals(((ViewAppointmentCommand) other).targetIndex));
    }
}
