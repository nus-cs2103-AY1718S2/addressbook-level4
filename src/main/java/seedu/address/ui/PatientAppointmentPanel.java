package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;

/**
 * Panel containing the list of patient's appointments.
 */
public class PatientAppointmentPanel extends UiPart<Region> {
    private static final String FXML = "PatientAppointmentPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientAppointmentPanel.class);

    public PatientAppointmentPanel(ObservableList<Appointment> appointmentList) {
        super(FXML);
        //setConnections(appointmentList);
        registerAsAnEventHandler(this);
    }
}
