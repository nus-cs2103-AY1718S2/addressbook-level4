package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's delivery date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DelivDate {

    /**
     * Represents a Person's address in the address book.
     * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
     */

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person delivery date can take dates in the form YYYY-MM-DD, in only the valid date range, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    //public static final String DATE_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    public static final String DATE_VALIDATION_REGEX = "((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param date A valid date.
     */
    public DelivDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_ADDRESS_CONSTRAINTS);
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid person date.
     */
    public static boolean isValidDate(String test) {
        boolean itMatches = test.matches(DATE_VALIDATION_REGEX);
        return itMatches;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.person.DelivDate // instanceof handles nulls
                && this.value.equals(((seedu.address.model.person.DelivDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}



