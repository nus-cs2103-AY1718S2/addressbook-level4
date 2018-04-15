package seedu.address.model.CalendarEvent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author wayneong95

/**
 * Represents the start date of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */

public class EventStartDate {

    public static final String MESSAGE_START_DATE_CONSTRAINTS =
            "Event start date should only contain numbers in the format YYYY-MM-DD. Eg. 2008-11-30";

    public static final String DATE_VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    public final String startDate;

    /**
     * Constructs an {@code EventStartDate}.
     *
     * @param startDate A valid startDate.
     */

    public EventStartDate(String startDate) {
        requireNonNull(startDate);
        checkArgument(isValidDate(startDate), MESSAGE_START_DATE_CONSTRAINTS);
        this.startDate = startDate;
    }

    /**
     * Returns true if a given string is a valid event start date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startDate;
    }
}
