package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author demitycho
/**
 * Represents a Student's time in a lesson in the Schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime String)}
 */
public class Time implements Comparable<Time> {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time should be of the format HH:MM "
            + "and adhere to the following constraints:\n"
            + "1. The hour HH should only contain numbers and be in range [00, 23] inclusive\n"
            + "2. This is followed by a ':' and then minutes MM.\n"
            + "3. The minutes MM should only contain numbers and be in range [00, 59] inclusive\n";
    // Numeric characters in Hour or Minute ranges
    private static final String HOUR_PART_REGEX = "^(0[0-9]|1[0-9]|2[0-3])";
    private static final String MINUTE_PART_REGEX = "([0-5][0-9])";
    private static final String TIME_DELIMITER = ":";
    public static final String TIME_VALIDATION_REGEX = HOUR_PART_REGEX
            + TIME_DELIMITER
            + MINUTE_PART_REGEX;
    private static final int INDEX_HOUR = 0;
    private static final int INDEX_MIN = 1;

    public final String value;
    private final Hour hour;
    private final Min min;


    /**
     * Constructs an {@code Time}.
     *
     * @param time A valid time string.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.value = time;
        this.hour = new Hour(value.split(TIME_DELIMITER)[INDEX_HOUR]);
        this.min = new Min(value.split(TIME_DELIMITER)[INDEX_MIN]);
    }

    /**
     * Returns if a given string is a valid student time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    public Hour getHour() {
        return this.hour;
    }
    public Min getMin() {
        return this.min;
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
    public int compareTo(Time other) {
        return this.getHour().compareTo(other.getHour()) != 0
                ? this.getHour().compareTo(other.getHour())
                : this.getMin().compareTo(other.getMin());
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
