package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Title of a {@code CalendarEvent} in Event list of Address Book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEventTitle(String)}
 */
public class EventTitle {
    public static final String MESSAGE_EVENT_TITLE_CONSTRAINTS =
            "Event title should only contain alphanumeric characters and spaces of up to 40 characters, "
                    + "and it should not be blank";

    public static final String EVENT_TITLE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final int CHARACTER_LIMIT = 40;

    private final String eventTitle;

    /**
     * Constructs {@code EventTitle}.
     *
     * @param eventTitle Valid event title.
     */
    public EventTitle(String eventTitle) {
        requireNonNull(eventTitle);
        checkArgument(isValidEventTitle(eventTitle), MESSAGE_EVENT_TITLE_CONSTRAINTS);
        this.eventTitle = eventTitle;
    }

    /**
     * Returns true if a given string is a valid event title.
     */
    public static boolean isValidEventTitle(String test) {
        return test.matches(EVENT_TITLE_VALIDATION_REGEX) && test.length() <= CHARACTER_LIMIT;
    }


    @Override
    public String toString() {
        return eventTitle;
    }

    /**
     * eventTitle matching is non case-sensitive
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTitle // instanceof handles nulls
                && this.eventTitle.equalsIgnoreCase(((EventTitle) other).eventTitle)); // state check
    }

    @Override
    public int hashCode() {
        return eventTitle.hashCode();
    }
}
