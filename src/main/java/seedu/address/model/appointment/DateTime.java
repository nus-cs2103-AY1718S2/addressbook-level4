package seedu.address.model.appointment;

/**
 * Appointment DateTime in Imdb
 * Gurantees: details are present and not null, field values are validated, immutable
 */
public class DateTime {

    public static final int APPOINTMENT_DURATION = 30;
    private String appointmentDateTime;

    public DateTime(String appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public String toString() {
        return this.appointmentDateTime;
    }

}
