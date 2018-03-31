package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.appointment.Appointment;

//@@author jlks96
/**
 * Indicates that an appointment is deleted.
 */
public class AppointmentDeletedEvent extends BaseEvent {
    private final Appointment appointmentDeleted;

    public AppointmentDeletedEvent(Appointment appointmentDeleted) {
        this.appointmentDeleted = appointmentDeleted;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Appointment getAppointmentDeleted() {
        return appointmentDeleted;
    }
}
