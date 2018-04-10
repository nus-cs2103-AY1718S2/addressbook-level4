package seedu.address.model.student.miscellaneousinfo;

//@@ author samuel

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's allergies component of his/her miscellaneous information.
 * Guarantees: immutable; is valid as declared in {@link #isValidAllergies(String)}
 */
public class Allergies {

    public static final String MESSAGE_ALLERGIES_CONSTRAINTS =
            "Student allergies can take any values, and it should not be blank";
    /*
     * The first character of the allergies must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ALLERGIES_VALIDATION_REGEX = "[^\\s].*";

    private final String value;

    /**
     * Construct {@code Allergies} with initial default value
     */
    public Allergies() {
        value = "Not updated";
    }

    /**
     * Constructs an {@code Allergies} instance.
     *
     * @param allergies A valid name string of allergies.
     */
    public Allergies(String allergies) {
        requireNonNull(allergies);
        checkArgument(isValidAllergies(allergies), MESSAGE_ALLERGIES_CONSTRAINTS);
        this.value = allergies;
    }

    /**
     * Returns true if a given string is a valid student allergies.
     */
    public static boolean isValidAllergies(String test) {
        return test.matches(ALLERGIES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Allergies // instanceof handles nulls
                && this.value.equals(((Allergies) other).value)); // state check
    }
}
//@@ author
