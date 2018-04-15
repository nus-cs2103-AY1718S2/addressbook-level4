package seedu.address.model.CalendarEvent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author wayneong95

/**
 * Represents the end date of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */

public class EventEndDate {

    public static final String MESSAGE_END_DATE_CONSTRAINTS =
            "Event end date should only contain numbers in the format YYYY-MM-DD. Eg. 2008-11-30";

    public static final String INVALID_END_DATE_MESSAGE = "End Date cannot be earlier than Start Date!";

    public static final String DATE_VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

    public final String endDate;

    /**
     * Constructs an {@code EventEndDate}.
     *
     * @param endDate A valid endDate.
     */

    public EventEndDate(String endDate) {
        requireNonNull(endDate);
        checkArgument(isValidDate(endDate), MESSAGE_END_DATE_CONSTRAINTS);
        this.endDate = endDate;
    }

    /**
     * Returns true if a given string is a valid event end date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return endDate;
    }
}
