package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's time in a lesson in the Schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time should be of the format HH:MM "
            + "and adhere to the following constraints:\n"
            + "1. The hour HH should only contain numbers and be in range [00, 24] inclusive\n"
            + "2. This is followed by a ':' and then minutes MM. "
            + "3. The minutes MM should only contain numbers and be in range [00, 60] inclusive\n";
    // alphanumeric and special characters
    private static final String HOUR_PART_REGEX = "([01]?[0-9]|2[0-3])";
    private static final String MINUTE_PART_REGEX = "([0-5][0-9])";
    public static final String TIME_VALIDATION_REGEX = HOUR_PART_REGEX + ":"
            + MINUTE_PART_REGEX;

    public final String value;

    /**T
     * Constructs an {@code Time}.
     *
     * @param time A valid time string.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.value = time;
    }

    /**
     * Returns if a given string is a valid student time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && this.value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
