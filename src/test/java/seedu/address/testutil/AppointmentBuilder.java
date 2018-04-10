package seedu.address.testutil;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.EndDateTime;
import seedu.address.model.appointment.StartDateTime;
import seedu.address.model.appointment.Title;

//@@author trafalgarandre
/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_TITLE = "Meeting";
    public static final String DEFAULT_START_DATE_TIME = "2018-03-26 14:00";
    public static final String DEFAULT_END_DATE_TIME = "2018-03-26 16:00";

    private Title title;
    private StartDateTime startDateTime;
    private EndDateTime endDateTime;

    public AppointmentBuilder() {
        title = new Title(DEFAULT_TITLE);
        startDateTime = new StartDateTime(DEFAULT_START_DATE_TIME);
        endDateTime = new EndDateTime(DEFAULT_END_DATE_TIME);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        title = appointmentToCopy.getTitle();
        startDateTime = appointmentToCopy.getStartDateTime();
        endDateTime = appointmentToCopy.getEndDateTime();
    }

    /**
     * Sets the {@code Title} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code StartDateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartDateTime(String startDateTime) {
        this.startDateTime = new StartDateTime(startDateTime);
        return this;
    }

    /**
     * Sets the {@code EndDateTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withEndDateTime(String endDateTime) {
        this.endDateTime = new EndDateTime(endDateTime);
        return this;
    }

    public Appointment build() {
        return new Appointment(title, startDateTime, endDateTime);
    }
}
