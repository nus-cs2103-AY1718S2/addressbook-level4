//@@author Kyholmes
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.patient.Patient;

/**
 * An event requesting to view the list of appointments of the patient.
 */
public class ShowPatientAppointmentRequestEvent extends BaseEvent {

    public final Patient data;

    public ShowPatientAppointmentRequestEvent(Patient data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
