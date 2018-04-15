//@@author ZhangYijiong
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Implementation follows {@code Price}
 * Represents an Task's past order count number
 * Guarantees: immutable; is valid as declared in {@link #isValidCount(String)}
 */
public class Count {


    public static final String MESSAGE_COUNT_CONSTRAINTS =
            "Count numbers can only be positive integers";
    public static final String COUNT_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Count}.
     *
     * @param count A valid count number.
     */
    public Count(String count) {
        requireNonNull(count);
        checkArgument(isValidCount(count), MESSAGE_COUNT_CONSTRAINTS);
        this.value = count;
    }

    /**
     * Returns true if a given string is a valid task count number.
     */
    public static boolean isValidCount(String test) {
        return test.matches(COUNT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Count // instanceof handles nulls
                && this.value.equals(((Count) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     *  Returns count in integer form to be able used by {@code compareTo} in Task
     */
    public int toInt() {
        return Integer.parseInt(value);
    }
}
