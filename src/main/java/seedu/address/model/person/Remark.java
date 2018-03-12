package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a task's remark in the task manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Remark of Tasks can take any values.";

    public static final String REMARK_VALIDATION_REGEX = "*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param remark A valid address.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidAddress(remark), MESSAGE_REMARK_CONSTRAINTS);
        this.value = remark;
    }

    /**
     * Returns true if a given string is a valid activity email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(REMARK_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

