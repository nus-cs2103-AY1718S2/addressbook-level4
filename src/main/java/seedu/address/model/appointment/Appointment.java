//@@author Kyholmes
package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

/**
 * Patient appointment in Imdb
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class Appointment {

    private DateTime appointmentDateTime;

    public Appointment(String appointmentDateTime) {
        requireNonNull(appointmentDateTime);
        this.appointmentDateTime = new DateTime(appointmentDateTime);
    }

    public DateTime getAppointmentDateTime() {
        return this.appointmentDateTime;
    }

    public String getAppointmentDateTimeString() {
        return this.appointmentDateTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Appointment
                && this.appointmentDateTime.equals(((Appointment) other).appointmentDateTime));
    }

    @Override
    public int hashCode() {
        return appointmentDateTime.hashCode();
    }
}
