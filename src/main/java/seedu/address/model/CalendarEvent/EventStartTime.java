package seedu.address.model.CalendarEvent;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author wayneong95

/**
 * Represents the start time of an event in the Personal Trainer Pro app.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */

public class EventStartTime {

    public static final String MESSAGE_START_TIME_CONSTRAINTS =
            "Event start time should only contain numbers in the HH:MM format. Eg. 11:30";

    public static final String TIME_VALIDATION_REGEX = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";

    public final String startTime;

    /**
     * Constructs an {@code EventStartTime}.
     *
     * @param startTime A valid startTime.
     */

    public EventStartTime(String startTime) {
        requireNonNull(startTime);
        checkArgument(isValidTime(startTime), MESSAGE_START_TIME_CONSTRAINTS);
        this.startTime = startTime;
    }

    /**
     * Returns true if a given string is a valid event start time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startTime;
    }
}
