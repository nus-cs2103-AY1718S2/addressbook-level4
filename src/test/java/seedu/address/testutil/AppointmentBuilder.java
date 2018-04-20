package seedu.address.testutil;

import seedu.address.model.appointment.Appointment;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_DATE_TIME = "2018-04-08 10:30";
    private String dateTime;
    public AppointmentBuilder() {
        dateTime = DEFAULT_DATE_TIME;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        dateTime = appointmentToCopy.getAppointmentDateTimeString();
    }

    /**
     * Sets the {@code patientName} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatientName(String name) {
        return this;
    }

    /**
     * Sets the {@code dateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public Appointment build() {
        return new Appointment(dateTime);
    }
}
