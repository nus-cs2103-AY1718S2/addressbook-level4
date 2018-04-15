//@@author deeheenguyen
package seedu.address.model.interview;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Interview's location in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date must be in format dd.mm.yyyy, and it should not be blank";


    /*
     * The first character of the date  must not be a whitespace,
     * be format dd-mm-yyyy.
     */
    public static final String DATE_VALIDATION_REGEX =
            "^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$";

    public final String value;

    /**
     * Constructs an {@code Location}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_DATE_CONSTRAINTS);
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid interview location.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.interview.Date // instanceof handles nulls
                && this.value.equals(((seedu.address.model.interview.Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
//author@@ deeheenguyen
