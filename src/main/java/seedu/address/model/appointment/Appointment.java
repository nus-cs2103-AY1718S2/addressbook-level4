package seedu.address.model.appointment;

/**
 * Patient appointment in Imdb
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class Appointment {

    private DateTime appointmentDateTime;
    private String patientName;
    public Appointment(String appointmentDateTime) {
        this.patientName = "";
        this.appointmentDateTime = new DateTime(appointmentDateTime);
    }

    public String getPatientName() {
        return this.patientName;
    }

    public DateTime getAppointmentDateTime() {
        return this.appointmentDateTime;
    }

    public String getAppointmentDateTimeString() {
        return this.appointmentDateTime.toString();
    }
}
