package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an event related to a person
 */
public class Event {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Event names should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String MESSAGE_VENUE_CONSTRAINTS =
            "Venues should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String MESSAGE_DATE_CONSTRAINTS = "DATE must be a valid date in the format of DD/MM/YYYY";
    public static final String MESSAGE_TIME_CONSTRAINTS = "TIME must be a valid time in the format of HHmm";

    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String DATE_VALIDATION_REGEX = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/"
            + "|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9"
            + "]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\"
            + "d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
    public static final String TIME_VALIDATION_REGEX = "^(0[0-9]|1[0-9]|2[0-4])[0-5][0-9]";

    protected String name;
    protected String venue;
    protected String date;
    protected String startTime;
    protected String endTime;

    /**
     * Default constructor, creating a blank Event.
     */
    public Event() {
        this("blank", "blank", "19/07/2017", "0000", "2359");
    }

    /**
     * Every field must be present and not null
     */
    public Event(String name, String venue, String date, String start, String end) {
        requireAllNonNull(name, start, end);
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.startTime = start;
        this.endTime = end;
    }

    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getName().equals(this.getName())
                && otherEvent.getVenue().equals(this.getVenue())
                && otherEvent.getDate().equals(this.getDate())
                && otherEvent.getStartTime().equals(this.getStartTime())
                && otherEvent.getEndTime().equals(this.getEndTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, venue, date, startTime, endTime);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Venue: ")
                .append(getVenue())
                .append(" Date: ")
                .append(getDate())
                .append(" Start time: ")
                .append(getStartTime())
                .append(" End time: ")
                .append(getEndTime());
        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
