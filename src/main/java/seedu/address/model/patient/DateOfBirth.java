package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's DOB in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDob(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_DOB_CONSTRAINTS =
            "Patient DOBs should only contain digits and slashes, and it should not be blank";

    /*
     * The first character of the DOB must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DOB_VALIDATION_REGEX = "[\\p{Digit}\\p{Punct}]*";

    public final String value;

    /**
     * Constructs a {@code Dob}.
     *
     * @param dob A valid DOB.
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgument(isValidDob(dob), MESSAGE_DOB_CONSTRAINTS);
        this.value = dob;
    }

    /**
     * Returns true if a given string is a valid patient DOB.
     */
    public static boolean isValidDob(String test) { return test.matches(DOB_VALIDATION_REGEX); }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirth // instanceof handles nulls
                && this.value.equals(((DateOfBirth) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
