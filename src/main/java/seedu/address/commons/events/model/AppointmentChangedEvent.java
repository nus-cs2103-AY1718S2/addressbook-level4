package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.patient.Patient;

/** Indicates the Appointment in the model has changed*/
public class AppointmentChangedEvent extends BaseEvent {

    public final Patient data;

    public AppointmentChangedEvent(Patient data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Appointment records changed";
    }
}
