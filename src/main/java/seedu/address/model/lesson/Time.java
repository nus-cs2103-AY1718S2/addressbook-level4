package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime String)}
 */
public class Time {

    public static final String MESSAGE_EMAIL_CONSTRAINTS = "Time should be of the format HH:MM "
            + "and adhere to the following constraints:\n"
            + "1. The hour HH should only contain numbers and be in range [00, 24] inclusive\n"
            + "2. This is followed by a ':' and then minutes MM. "
            + "3. The minutes MM should only contain numbers and be in range [00, 60] inclusive\n";
    // alphanumeric and special characters
    private static final String HOUR_PART_REGEX = "^(\\d?[1-2]|[1-9]0)$";
    private static final String MINUTE_PART_REGEX = "^(\\d?[1-6]|[1-9]0)$";
    public static final String EMAIL_VALIDATION_REGEX = HOUR_PART_REGEX + ":"
            + MINUTE_PART_REGEX;

    public final String value;

    /**
     * Constructs an {@code Email}.
     *
     * @param time A valid time string.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_EMAIL_CONSTRAINTS);
        this.value = time;
    }

    /**
     * Returns if a given string is a valid student email.
     */
    public static boolean isValidTime(String test) {
        return test.matches(EMAIL_VALIDATION_REGEX);
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
