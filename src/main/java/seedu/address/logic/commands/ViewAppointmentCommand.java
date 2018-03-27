package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowPatientAppointmentRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;

/**
 * List all the patient appointments
 */
public class ViewAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "viewappt";
    public static final String COMMAND_ALIAS = "va";

    public static final String MESSAGE_USAGE_PATIENT = COMMAND_WORD + ": View list of appointments of a patient. "
            + "Parameters: "
            + "NAME";

    public static final String MESSAGE_SUCCESS = "Listed all appointments";
    public static final String MESSAGE_SUCCESS_PATIENT = "Listed all appointments made by %1$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "This patient cannot be found in the database";
    private final NameContainsKeywordsPredicate predicate;

    /**
     * Creates an ViewAppointmentCommand to view list of appointments
     */
    public ViewAppointmentCommand() {
        this.predicate = null;
    }

    /**
     * Creates an ViewAppointmentCommand to view list of appointments the specified {@code Patient}
     */
    public ViewAppointmentCommand(NameContainsKeywordsPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (this.predicate != null) {

            Patient patientFound = model.getPatientFromList(predicate);

            if (patientFound == null) {
                throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
            }

            EventsCenter.getInstance().post(new ShowPatientAppointmentRequestEvent(patientFound));

            return new CommandResult(String.format(MESSAGE_SUCCESS_PATIENT, patientFound.getName()));
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private static void printOutAppointmentInLog(ObservableList<Appointment> appointments) {
        appointments.forEach(appointment -> {
        });
    }
}
