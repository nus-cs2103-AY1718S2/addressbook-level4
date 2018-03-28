package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents ending Time of a {@code CalendarEvent}.
 * Guarantees: immutable; is valid as declared in {@link #isValidEndTime(String)}
 */
public class EndTime {

    public static final String MESSAGE_END_TIME_CONSTRAINTS =
            "End Time should be HH:mm (24Hour Format), and it should not be blank";

    public static final String END_TIME_VALIDATION_REGEX = "\\d{2}:\\d{2}"; // format
    public static final String END_TIME_VALIDATION_FORMAT = "HH:mm"; // legal dates

    private final String endTime;

    /**
     * Constructs {@code EndTime}.
     * @param endTime Valid end time.
     */
    public EndTime (String endTime) {
        requireNonNull(endTime);
        checkArgument(isValidEndTime(endTime), MESSAGE_END_TIME_CONSTRAINTS);
        this.endTime = endTime;
    }

    /**
     * Returns true if given string is a valid end time.
     */
    public static boolean isValidEndTime(String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(END_TIME_VALIDATION_FORMAT);
        simpleDateFormat.setLenient(false);

        try {
            simpleDateFormat.parse(endTime);
        } catch (ParseException e) {
            return false;
        }

        return endTime.matches(END_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EndTime // instanceof handles nulls
                && this.endTime.equals(((EndTime) other).endTime)); // state check
    }

    @Override
    public int hashCode() {
        return endTime.hashCode();
    }
}
