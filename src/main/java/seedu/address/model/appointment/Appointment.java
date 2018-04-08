package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@@author jlks96
/**
 * Represents an Appointment in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    public static final String MESSAGE_TIMES_CONSTRAINTS = "Start time must be before end time";

    private final PersonName personName;
    private final Date date;
    private final StartTime startTime;
    private final EndTime endTime;
    private final Location location;

    /**
     * Every field must be present and not null.
     */
    public Appointment(PersonName personName, Date date, StartTime startTime, EndTime endTime, Location location) {
        checkArgument(areValidTimes(startTime, endTime), MESSAGE_TIMES_CONSTRAINTS);
        this.personName = personName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public PersonName getName() {
        return personName;
    }

    public Date getDate() {
        return date;
    }

    public StartTime getStartTime() {
        return startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Checks if a given {@code startTime} is before a given {@code endTime}.
     * @param startTime A startTime to check.
     * @param endTime An endTime to check.
     * @return {@code true} if a given start time is before a given end time.
     */
    public static boolean areValidTimes(StartTime startTime, EndTime endTime) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        timeFormatter.setLenient(false);
        java.util.Date startTimeDate;
        java.util.Date endTimeDate;

        try {
            startTimeDate = timeFormatter.parse(startTime.time);
            endTimeDate = timeFormatter.parse(endTime.time);
        } catch (java.text.ParseException e) { //if fail return false
            return false;
        }
        requireNonNull(startTimeDate);
        requireNonNull(endTimeDate);
        return startTimeDate.before(endTimeDate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getName().equals(this.getName())
                && otherAppointment.getDate().equals(this.getDate())
                && otherAppointment.getStartTime().equals(this.getStartTime())
                && otherAppointment.getEndTime().equals(this.getEndTime())
                && otherAppointment.getLocation().equals(this.getLocation());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personName, date, startTime, endTime, location);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Date: ")
                .append(getDate())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" End Time: ")
                .append(getEndTime())
                .append(" Location: ")
                .append(getLocation());
        return builder.toString();
    }

    /**
     * Returns a list of Strings which represents all the appointment's attributes
     */
    public List<String> toStringList() {
        final List<String> result = new ArrayList<>();
        result.add(getName().toString());
        result.add(getDate().toString());
        result.add(getStartTime().toString());
        result.add(getEndTime().toString());
        result.add(getLocation().toString());
        return result;

    }
}
