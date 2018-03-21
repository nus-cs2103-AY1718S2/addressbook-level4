package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's time in a lesson in the Schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime String)}
 */
public class Time implements Comparable<Time> {

    public static final String MESSAGE_TIME_CONSTRAINTS = "Time should be of the format HH:MM "
            + "and adhere to the following constraints:\n"
            + "1. The hour HH should only contain numbers and be in range [00, 24] inclusive\n"
            + "2. This is followed by a ':' and then minutes MM. "
            + "3. The minutes MM should only contain numbers and be in range [00, 59] inclusive\n";
    // Numeric characters in Hour or Minute ranges
    private static final String HOUR_PART_REGEX = "([01]?[0-9]|2[0-3])";
    private static final String MINUTE_PART_REGEX = "([0-5][0-9])";
    private static final String TIME_DELIMITER = ":";
    public static final String TIME_VALIDATION_REGEX = HOUR_PART_REGEX
            + TIME_DELIMITER
            + MINUTE_PART_REGEX;

    public final String value;
    private final Hour hour;
    private final Min min;
    private final int HOUR_INDEX = 0;
    private final int MIN_INDEX = 1;

    /**
     * Constructs an {@code Time}.
     *
     * @param time A valid time string.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.value = time;
        this.hour = new Hour(value.split(TIME_DELIMITER)[HOUR_INDEX]);
        this.min = new Min(value.split(TIME_DELIMITER)[MIN_INDEX]);
    }

    /**
     * Returns if a given string is a valid student time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    public Hour getHour() { return this.hour; }
    public Min getMin() { return this.min; }

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
    public int compareTo(Time other){
        return this.getHour().compareTo(other.getHour()) != 0 ?
                this.getHour().compareTo(other.getHour()) :
                this.getMin().compareTo(other.getMin());
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
