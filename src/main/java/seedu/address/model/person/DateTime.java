package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a task's remark in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Remark of Tasks can take any values.";

    public static final String DATETIME_VALIDATION_REGEX = "*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param time A valid address.
     */
    public DateTime(String time) {
        requireNonNull(time);
        checkArgument(isValidDateTime(time), MESSAGE_DATETIME_CONSTRAINTS);
        this.value = time;
    }

    /**
     * Returns true if a given string is a valid activity email.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && this.value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

