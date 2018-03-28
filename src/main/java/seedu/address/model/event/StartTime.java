package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents starting Time of a {@code CalendarEvent}.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartTime(String)}
 */
public class StartTime {
    public static final String MESSAGE_START_TIME_CONSTRAINTS =
            "Start Time should be HH:mm (24Hour Format), and it should not be blank";

    public static final String START_TIME_VALIDATION_REGEX = "\\d{2}:\\d{2}"; // format
    public static final String START_TIME_VALIDATION_FORMAT = "HH:mm"; // legal dates

    private final String startTime;

    /**
     * Constructs {@code StartTime}.
     *
     * @param startTime Valid start time.
     */
    public StartTime (String startTime) {
        requireNonNull(startTime);
        checkArgument(isValidStartTime(startTime), MESSAGE_START_TIME_CONSTRAINTS);
        this.startTime = startTime;
    }

    /**
     * Returns true if given string is a valid start time.
     */
    public static boolean isValidStartTime(String startTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(START_TIME_VALIDATION_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(startTime);
        } catch (ParseException e) {
            return false;
        }

        return startTime.matches(START_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return startTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartTime // instanceof handles nulls
                && this.startTime.equals(((StartTime) other).startTime)); // state check
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }
}
