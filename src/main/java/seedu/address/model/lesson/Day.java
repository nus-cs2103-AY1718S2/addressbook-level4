package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's time in a lesson in the Schedule.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime String)}
 */
public class Day {

    public static final String MESSAGE_DAY_CONSTRAINTS = "Time should be of the format: first 3 letters of Day"
            + "(not case sensitive) i,e.\n"
            + "mon, tue, wed, thu, fri, sat, sun\";\n\n";
            // Numeric characters in Hour or Minute ranges
    private static final String DAY_REGEX = "^(mon|tue|wed|thu|fri|sat|sun)";

    public final String value;

    /**
     * Constructs an {@code Day}.
     *
     * @param day A valid day string.
     */
    public Day(String day) {
        requireNonNull(day);
        checkArgument(isValidDay(day), MESSAGE_DAY_CONSTRAINTS);
        this.value = day;
    }

    /**
     * Returns if a given string is a valid student day.
     */
    public static boolean isValidDay(String test) {
        return test.matches(DAY_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Day // instanceof handles nulls
                && this.value.equals(((Day) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
