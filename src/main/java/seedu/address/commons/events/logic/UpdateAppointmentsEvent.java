package seedu.address.commons.events.logic;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.appointment.Appointment;

//@@author jlks96
/**
 * Indicates that appointment list is updated.
 */
public class UpdateAppointmentsEvent extends BaseEvent {

    private final ObservableList<Appointment> updatedAppointments;

    public UpdateAppointmentsEvent(ObservableList<Appointment> updatedAppointments) {
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
