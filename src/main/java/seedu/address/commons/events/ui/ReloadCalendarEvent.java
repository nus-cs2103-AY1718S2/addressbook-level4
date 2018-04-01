package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.appointment.Appointment;

/**
 * An event request the Calendar to reload.
 */
public class ReloadCalendarEvent extends BaseEvent {

    public final List<Appointment> appointments;

    public ReloadCalendarEvent(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
