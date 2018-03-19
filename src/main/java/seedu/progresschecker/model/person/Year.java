package seedu.progresschecker.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.progresschecker.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's year of study in the ProgressChecker.
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(String)}
 */
public class Year {

    public static final String MESSAGE_YEAR_CONSTRAINTS =
            "Person years of study can take digits ranging from 1 to 5, it can be left blank";

    /*
     * It accepts single digits ranging from 1 to 5.
     * empty string will be accepted as well, as "year" is an optional field.
     */
    public static final String YEAR_VALIDATION_REGEX = "(^$|^[1-5]$)";

    public final String value;

    /**
     * Constructs an {@code Year}.
     *
     * @param year A valid year.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_YEAR_CONSTRAINTS);
        this.value = year;
    }

    /**
     * Returns true if a given string is a valid year of study.
     */
    public static boolean isValidYear(String test) {
        return test.matches(YEAR_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && this.value.equals(((Year) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
