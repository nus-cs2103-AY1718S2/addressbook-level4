package seedu.address.model.petpatient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author chialejing
/**
 * Represents a PetPatient's blood type in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodType(String)}
 */
public class BloodType {

    public static final String MESSAGE_PET_BLOODTYPE_CONSTRAINTS =
            "Pet Patient blood type should only contain alphabetic characters, punctuations and spaces, "
                    + "and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BLOODTYPE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}\\p{Punct}\\p{Blank}]*";

    public final String bloodType;

    /**
     * Constructs a {@code BloodType}.
     *
     * @param bloodType A valid bloodType.
     */
    public BloodType(String bloodType) {
        requireNonNull(bloodType);
        checkArgument(isValidBloodType(bloodType), MESSAGE_PET_BLOODTYPE_CONSTRAINTS);
        this.bloodType = bloodType;
    }

    /**
     * Returns true if a given string is a valid bloodType.
     */
    public static boolean isValidBloodType(String test) {
        return test.matches(BLOODTYPE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return bloodType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodType // instanceof handles nulls
                && this.bloodType.equals(((BloodType) other).bloodType)); // state check
    }

    @Override
    public int hashCode() {
        return bloodType.hashCode();
    }
}
