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
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.appointmentDateTime.equals(((Appointment) other).appointmentDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return appointmentDateTime.hashCode();
    }
}
