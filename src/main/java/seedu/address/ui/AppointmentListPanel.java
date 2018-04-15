//@@author ongkuanyang
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.AppointmentPanelSelectionChangedEvent;
import seedu.address.model.appointment.Appointment;

/**
 * Panel containing the list of appointments.
 */
public class AppointmentListPanel extends UiPart<Region> {
    private static final String FXML = "AppointmentListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AppointmentListPanel.class);

    @FXML
    private ListView<AppointmentCard> appointmentListView;

    public AppointmentListPanel(ObservableList<Appointment> appointmentList) {
        super(FXML);
        setConnections(appointmentList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Appointment> appointmentList) {
        ObservableList<AppointmentCard> mappedList = EasyBind.map(
                appointmentList, (appointment) ->
                        new AppointmentCard(appointment, appointmentList.indexOf(appointment) + 1));
        appointmentListView.setItems(mappedList);
        appointmentListView.setCellFactory(listView -> new AppointmentListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        appointmentListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in appointment list panel changed to : '" + newValue + "'");
                        raise(new AppointmentPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code AppointmentCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            appointmentListView.scrollTo(index);
            appointmentListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code AppointmentCard}.
     */
    class AppointmentListViewCell extends ListCell<AppointmentCard> {

        @Override
        protected void updateItem(AppointmentCard appointment, boolean empty) {
            super.updateItem(appointment, empty);

            if (empty || appointment == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(appointment.getRoot());
            }
        }
    }

}
//@@author
