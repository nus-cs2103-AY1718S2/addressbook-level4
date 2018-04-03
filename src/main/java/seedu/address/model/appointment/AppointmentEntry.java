package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import com.calendarfx.model.Entry;

/**
 * Patient appointment in Imdb, displayed on calendar
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class AppointmentEntry {
    private static final int TIME_INTERVAL = 1800;
    private Entry<String> appointmentEntry;

    public AppointmentEntry(Appointment appointment, String patientName) {
        requireAllNonNull(appointment, patientName);
        appointmentEntry = new Entry(patientName);
        appointmentEntry.setTitle(patientName);
        appointmentEntry.changeStartDate(appointment.getAppointmentDateTime().getLocalDate());
        appointmentEntry.changeEndDate(appointment.getAppointmentDateTime().getLocalDate());
        appointmentEntry.changeStartTime(appointment.getAppointmentDateTime().getLocalTime());
        appointmentEntry.changeEndTime(appointment.getAppointmentDateTime().getEndLocalTime(TIME_INTERVAL));
    }

    public Entry getAppointmentEntry() {
        return appointmentEntry;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentEntry // instanceof handles nulls
                && this.appointmentEntry.equals(((AppointmentEntry) other).appointmentEntry)); // state check
    }

    @Override
    public int hashCode() {
        return appointmentEntry.hashCode();
    }
}
