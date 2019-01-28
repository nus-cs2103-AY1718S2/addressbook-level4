package seedu.address.model.petpatient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author chialejing
/**
 * Represents a PetPatient's breed in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBreed(String)}
 */
public class Breed {

    public static final String MESSAGE_PET_BREED_CONSTRAINTS =
            "Pet Patient breed should only contain alphabetic characters and spaces, and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String BREED_VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    public final String breed;

    /**
     * Constructs a {@code Breed}.
     *
     * @param breed A valid breed.
     */
    public Breed(String breed) {
        requireNonNull(breed);
        checkArgument(isValidBreed(breed), MESSAGE_PET_BREED_CONSTRAINTS);
        this.breed = breed;
    }

    /**
     * Returns true if a given string is a valid breed.
     */
    public static boolean isValidBreed(String test) {
        return test.matches(BREED_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return breed;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Breed // instanceof handles nulls
                && this.breed.equals(((Breed) other).breed)); // state check
    }

    @Override
    public int hashCode() {
        return breed.hashCode();
    }
}
