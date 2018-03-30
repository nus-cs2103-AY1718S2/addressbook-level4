//@@author kokonguyen191
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's number of calories in the Recipe Book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCalories(String)}
 */
public class Calories {

    public static final String NULL_CALORIES_REFERENCE = "-";
    public static final String MESSAGE_CALORIES_CONSTRAINTS = "The number of calories must a positive integer.";
    public static final String CALORIES_VALIDATION_REGEX = "[\\d]+";
    public final String value;

    /**
     * Constructs a {@code Calories} object.
     *
     * @param calories A valid number of calories.
     */
    public Calories(String calories) {
        requireNonNull(calories);
        checkArgument(isValidCalories(calories), MESSAGE_CALORIES_CONSTRAINTS);
        this.value = calories;
    }

    /**
     * Returns true if a given string is a valid number of calories.
     */
    public static boolean isValidCalories(String test) {
        return test.equals(NULL_CALORIES_REFERENCE) || test.matches(CALORIES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calories // instanceof handles nulls
                && this.value.equals(((Calories) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
