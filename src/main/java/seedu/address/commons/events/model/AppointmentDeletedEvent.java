package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.appointment.Appointment;

//@@author jlks96
/**
 * Indicates that an appointment is deleted.
 */
public class AppointmentDeletedEvent extends BaseEvent {
    private final ObservableList<Appointment> updatedAppointments;

    public AppointmentDeletedEvent(ObservableList<Appointment> updatedAppointments) {
        this.updatedAppointments = updatedAppointments;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<Appointment> getUpdatedAppointments() {
        return updatedAppointments;
    }
}
