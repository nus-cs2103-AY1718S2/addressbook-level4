package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's blood type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class BloodType {

    public static final String MESSAGE_BLOODTYPE_CONSTRAINTS =
            "Patient blood types should only contain alphabets and punctuations, and it should not be blank";

    /*
     * The first character of the blood type must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BLOODTYPE_VALIDATION_REGEX = "[\\p{Alpha}\\p{Punct}]*";

    public final String value;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodType A valid blood type.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        checkArgument(isValidBloodType(bloodType), MESSAGE_BLOODTYPE_CONSTRAINTS);
        this.value = bloodType;
    }

    /**
     * Returns true if a given string is a valid patient blood type.
     */
    public static boolean isValidBloodType(String test) {
        return test.matches(BLOODTYPE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodType // instanceof handles nulls
                && this.value.equals(((BloodType) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
