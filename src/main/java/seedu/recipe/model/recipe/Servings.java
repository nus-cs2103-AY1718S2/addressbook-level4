//@@author kokonguyen191
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's number of servings in the Recipe Book.
 * Guarantees: immutable; is valid as declared in {@link #isValidServings(String)}
 */
public class Servings {

    public static final String NULL_SERVINGS_REFERENCE = "-";
    public static final String MESSAGE_SERVINGS_CONSTRAINTS = "The number of servings must a positive integer.";
    public static final String SERVINGS_VALIDATION_REGEX = "[\\d]+";
    public final String value;

    /**
     * Constructs a {@code Servings} object.
     *
     * @param servings A valid number of servings.
     */
    public Servings(String servings) {
        requireNonNull(servings);
        checkArgument(isValidServings(servings), MESSAGE_SERVINGS_CONSTRAINTS);
        this.value = servings;
    }

    /**
     * Returns true if a given string is a valid number of servings.
     */
    public static boolean isValidServings(String test) {
        return test.equals(NULL_SERVINGS_REFERENCE) || test.matches(SERVINGS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Servings // instanceof handles nulls
                && this.value.equals(((Servings) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
