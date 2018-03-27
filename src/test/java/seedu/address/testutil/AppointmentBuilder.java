package seedu.address.testutil;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.EndTime;
import seedu.address.model.appointment.Location;
import seedu.address.model.appointment.PersonName;
import seedu.address.model.appointment.StartTime;

//@@author jlks96
/**
 * A utility class to help with building Person objects.
 */
public class AppointmentBuilder {
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_DATE = "03/03/2018";
    public static final String DEFAULT_START_TIME = "12:30";
    public static final String DEFAULT_END_TIME = "13:30";
    public static final String DEFAULT_LOCATION = "Parkway Parade";

    private PersonName name;
    private Date date;
    private StartTime startTime;
    private EndTime endTime;
    private Location location;

    public AppointmentBuilder() {
        name = new PersonName(DEFAULT_NAME);
        date = new Date(DEFAULT_DATE);
        startTime = new StartTime(DEFAULT_START_TIME);
        endTime = new EndTime(DEFAULT_END_TIME);
        location = new Location(DEFAULT_LOCATION);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        name = appointmentToCopy.getName();
        date = appointmentToCopy.getDate();
        startTime = appointmentToCopy.getStartTime();
        endTime = appointmentToCopy.getEndTime();
        location = appointmentToCopy.getLocation();
    }

    /**
     * Sets the {@code PersonName} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPersonName(String name) {
        this.name = new PersonName(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartTime (String startTime) {
        this.startTime = new StartTime(startTime);
        return this;
    }

    /**
     * Sets the {@code EndTime} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withEndTime (String endTime) {
        this.endTime = new EndTime(endTime);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    public Appointment build() {
        return new Appointment(name, date, startTime, endTime, location);
    }
}
