package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.appointment.AppointmentEntry;

/**
 * An event requesting to view calendar and patients' appointments.
 */
public class ShowCalendarViewRequestEvent extends BaseEvent {

    public final ObservableList<AppointmentEntry> appointmentEntries;

    public ShowCalendarViewRequestEvent() {
        appointmentEntries = null;
    }

    public ShowCalendarViewRequestEvent(ObservableList<AppointmentEntry> appointmentEntries) {
        this.appointmentEntries = appointmentEntries;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
