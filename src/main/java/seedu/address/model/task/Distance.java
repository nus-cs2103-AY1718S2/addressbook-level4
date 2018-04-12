//@@author ZhangYijiong
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Implementation follows {@code Count}
 * Represents an Task's distance
 * Guarantees: immutable; is valid as declared in {@link #isValidDistance(String)}
 */
public class Distance {


    public static final String MESSAGE_DISTANCE_CONSTRAINTS =
            "Distance numbers can only be positive integers, in terms of km";
    public static final String DISTANCE_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Distance}.
     *
     * @param distance A valid distance number.
     */
    public Distance(String distance) {
        requireNonNull(distance);
        checkArgument(isValidDistance(distance), MESSAGE_DISTANCE_CONSTRAINTS);
        this.value = distance;
    }

    /**
     * Returns true if a given string is a valid task distance number.
     */
    public static boolean isValidDistance(String test) {
        return test.matches(DISTANCE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Distance // instanceof handles nulls
                && this.value.equals(((Distance) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     *  Returns distance in integer form to be able used by {@code compareTo} in Task
     */
    public int toInt() {
        return Integer.parseInt(value);
    }
}

