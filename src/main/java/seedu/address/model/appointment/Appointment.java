package seedu.address.model.appointment;

/**
 * Patient appointment in Imdb
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class Appointment {

    private DateTime appointmentDateTime;

    public Appointment(String appointmentDateTime) {
        this.appointmentDateTime = new DateTime(appointmentDateTime);
    }

    public DateTime getAppointmentDateTime() {
        return this.appointmentDateTime;
    }

    public String getAppointmentDateTimeString() {
        return this.appointmentDateTime.toString();
    }
}
