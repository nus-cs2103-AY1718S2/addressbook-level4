package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's nric number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {


    public static final String MESSAGE_NRIC_CONSTRAINTS =
            "Nric should start with a S, should be at 7 digits long" +
                    " and should end with a capital letter.";
    public static final String NRIC_VALIDATION_REGEX = "[S]\\d{7,}[A-Z]";
    public final String value;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid nric number.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_NRIC_CONSTRAINTS);
        this.value = nric;
    }

    /**
     * Returns true if a given string is a valid person nric number.
     */
    public static boolean isValidNric(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && this.value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
