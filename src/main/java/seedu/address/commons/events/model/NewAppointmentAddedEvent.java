package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.appointment.Appointment;

//@@author jlks96
/**
 * Indicates that a new appointment is added.
 */
public class NewAppointmentAddedEvent extends BaseEvent {

    private final Appointment appointmentAdded;

    public NewAppointmentAddedEvent(Appointment appointmentAdded) {
        this.appointmentAdded = appointmentAdded;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Appointment getAppointmentAdded() {
        return appointmentAdded;
    }
}
