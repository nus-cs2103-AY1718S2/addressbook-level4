//@@author Kyholmes
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;

/**
 * An UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {
    private static final String FXML = "AppointmentCard.fxml";

    public final Appointment appointment;

    @FXML
    private Label id;

    @FXML
    private Label dateTime;

    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");
        dateTime.setText(appointment.getAppointmentDateTimeString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        AppointmentCard card = (AppointmentCard) other;

        return id.getText().equals(card.id.getText())
                && appointment.equals(card.appointment);
    }

}
