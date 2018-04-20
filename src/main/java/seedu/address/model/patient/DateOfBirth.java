package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public static boolean isValidDob(String test) {
        try {
            DateFormat sourceFormat = new SimpleDateFormat("dd/MM/yyyy");
            sourceFormat.setLenient(false);
            Date date = sourceFormat.parse(test);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }


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
