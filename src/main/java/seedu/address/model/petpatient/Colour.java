package seedu.address.model.petpatient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author chialejing
/**
 * Represents a PetPatient's colour in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidColour(String)}
 */
public class Colour {

    public static final String MESSAGE_PET_COLOUR_CONSTRAINTS =
            "Pet Patient colour should only contain alphabetic characters and spaces, and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String COLOUR_VALIDATION_REGEX = "[\\p{Alpha}][\\p{Alpha} ]*";

    public final String colour;

    /**
     * Constructs a {@code Colour}.
     *
     * @param colour A valid colour.
     */
    public Colour(String colour) {
        requireNonNull(colour);
        checkArgument(isValidColour(colour), MESSAGE_PET_COLOUR_CONSTRAINTS);
        this.colour = colour;
    }

    /**
     * Returns true if a given string is a valid colour.
     */
    public static boolean isValidColour(String test) {
        return test.matches(COLOUR_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return colour;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Colour // instanceof handles nulls
                && this.colour.equals(((Colour) other).colour)); // state check
    }

    @Override
    public int hashCode() {
        return colour.hashCode();
    }
}
