package seedu.address.logic.commands;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;

/**
 * List all the patient appointments
 */
public class ViewAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "viewappt";
    public static final String COMMAND_ALIAS = "va";

    public static final String MESSAGE_SUCCESS = "Listed all patient appointments";
    private static final Logger logger = LogsCenter.getLogger(ViewAppointmentCommand.class);

    @Override
    public CommandResult execute() {
        ObservableList<Appointment> appointmentList = model.getIMDB().getAppointmentList();
        logger.info("--get appointment list---");
        printOutAppointmentInLog(appointmentList);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private static void printOutAppointmentInLog(ObservableList<Appointment> appointments) {
        appointments.forEach(appointment -> {
            logger.info(appointment.getPatientName() + ", " + appointment.getAppointmentDateTime() + "\n");
        });
    }
}
