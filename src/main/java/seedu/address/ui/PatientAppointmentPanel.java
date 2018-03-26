package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;

/**
 * Panel containing the list of patient's appointments.
 */
public class PatientAppointmentPanel extends UiPart<Region> {
    private static final String FXML = "PatientAppointmentPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PatientAppointmentPanel.class);

    @FXML
    private ListView<AppointmentCard> pastAppointmentCardListView;

    @FXML
    private ListView<AppointmentCard> upcomingAppointmentCardListView;

    public PatientAppointmentPanel(ObservableList<Appointment> pastAppointmentList,
                                   ObservableList<Appointment> upcomingAppointmentList) {
        super(FXML);
        setConnections(pastAppointmentList, upcomingAppointmentList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Appointment> pastAppointmentList,
                                ObservableList<Appointment> upcomingAppointmentList) {

        int pastAppointmentListSize = pastAppointmentList.size();
        ObservableList<AppointmentCard> pastMappedList = EasyBind.map(
                pastAppointmentList, (appointment -> new AppointmentCard(appointment,
                        pastAppointmentListSize + 1)));
        pastAppointmentCardListView.setItems(pastMappedList);
        ObservableList<AppointmentCard> upcomingMappedList = EasyBind.map(
                upcomingAppointmentList, (appointment -> new AppointmentCard(appointment,
                        pastAppointmentListSize + upcomingAppointmentList.indexOf(appointment) + 1)));
        upcomingAppointmentCardListView.setItems(upcomingMappedList);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<AppointmentCard> {

        @Override
        protected void updateItem(AppointmentCard appointmentCard, boolean empty) {
            super.updateItem(appointmentCard, empty);

            if (empty || appointmentCard == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(appointmentCard.getRoot());
            }
        }
    }
}
