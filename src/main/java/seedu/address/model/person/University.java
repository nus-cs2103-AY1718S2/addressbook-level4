package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's university in HR+.
 * Guarantees: immutable; is valid as declared in {@link #isValidUniversity(String)}
 */
public class University {
    public static final String MESSAGE_UNIVERSITY_CONSTRAINTS =
            "Universities should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the university must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String UNIVERSITY_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs a {@code University}.
     *
     * @param university A valid university.
     */
    public University(String university) {
        requireNonNull(university);
        checkArgument(isValidUniversity(university), MESSAGE_UNIVERSITY_CONSTRAINTS);
        this.value = university;
    }

    /**
     * Returns true if a given string is a valid person university.
     */
    public static boolean isValidUniversity(String test) {
        return test.matches(UNIVERSITY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof University // instanceof handles nulls
                && this.value.equals(((University) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
