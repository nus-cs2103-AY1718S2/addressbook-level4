//@@author Kyholmes
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyImdb;
import seedu.address.model.patient.Patient;

/** Indicates the Appointment in the model has changed*/
public class AppointmentChangedEvent extends BaseEvent {

    public final Patient data;
    public final ReadOnlyImdb readOnlyImdb;

    public AppointmentChangedEvent(Patient data, ReadOnlyImdb readOnlyImdb) {
        this.data = data;
        this.readOnlyImdb = readOnlyImdb;
    }

    @Override
    public String toString() {
        return "Appointment records changed";
    }
}
