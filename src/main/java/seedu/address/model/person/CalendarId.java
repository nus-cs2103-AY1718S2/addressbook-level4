package seedu.address.model.person;

/**
 * Represents a Person's calendarId
 */
public class CalendarId {

    public final String value;

    public CalendarId(String calendarId) {
        this.value = calendarId;
    }

    @Override
    public String toString() {
        return value;
    }
}
