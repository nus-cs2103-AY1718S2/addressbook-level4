package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.NameContainsKeywordsPredicate;

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
    public CommandResult execute() {
        ObservableList<Appointment> appointmentList = model.getImdb().getAppointmentList();

        printOutAppointmentInLog(appointmentList);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private static void printOutAppointmentInLog(ObservableList<Appointment> appointments) {
        appointments.forEach(appointment -> {
        });
    }
}
