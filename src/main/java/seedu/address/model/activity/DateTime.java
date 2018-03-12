package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Activity's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateAndTime(String)}
 */
public class DateTime {


    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and  Time numbers should be a date and should be in the format of ";
    public static final String DATETIME_VALIDATION_REGEX = ".*";
    public final String value;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateAndTime A valid phone number.
     */
    public DateTime(String dateAndTime) {
        requireNonNull(dateAndTime);
        checkArgument(isValidDateAndTime(dateAndTime), MESSAGE_DATETIME_CONSTRAINTS);
        this.value = dateAndTime;
    }

    /**
     * Returns true if a given string is a valid activity phone number.
     */
    public static boolean isValidDateAndTime(String test) {
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
