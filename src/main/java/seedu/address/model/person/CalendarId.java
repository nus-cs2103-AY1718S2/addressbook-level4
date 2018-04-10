package seedu.address.model.person;

//@@author crizyli-unused

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's calendarId
 */
public class CalendarId {

    public final String value;

    public CalendarId(String calendarId) {
        requireNonNull(calendarId);
        this.value = calendarId;
    }

    public String getValue() {
        return value;
    }
}
