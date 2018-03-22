//@@Author kokonguyen191
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's cookingTime number in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCookingTime(String)}
 */
public class CookingTime {


    public static final String MESSAGE_COOKING_TIME_CONSTRAINTS =
            "CookingTime can be in any of these format: 1h20m, 80m, 1 hour 20 minutes, 1 h 20 min, 80.";
    public static final String COOKING_TIME_VALIDATION_REGEX = "([\\d]+[ ]{0,1}(minute[s]{0,1}|min[s]{0,1}|m|hour[s]{0,1}|h){0,1}[ ]{0,1}){1,2}";
    public final String value;

    /**
     * Constructs a {@code CookingTime}.
     *
     * @param cookingTime A valid cookingTime number.
     */
    public CookingTime(String cookingTime) {
        requireNonNull(cookingTime);
        checkArgument(isValidCookingTime(cookingTime), MESSAGE_COOKING_TIME_CONSTRAINTS);
        this.value = cookingTime;
    }

    /**
     * Returns true if a given string is a valid recipe cookingTime number.
     */
    public static boolean isValidCookingTime(String test) {
        return test.matches(COOKING_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CookingTime // instanceof handles nulls
                && this.value.equals(((CookingTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
