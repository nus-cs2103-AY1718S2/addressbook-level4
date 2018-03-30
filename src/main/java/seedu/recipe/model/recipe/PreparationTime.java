//@@author kokonguyen191
package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's preparation time in the Recipe Book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPreparationTime(String)}
 */
public class PreparationTime {

    public static final String NULL_PREPARATION_TIME_REFERENCE = "-";
    public static final String MESSAGE_PREPARATION_TIME_CONSTRAINTS =
            "Preparation time can be in any of these format: 1h20m, 80m, 1 hour 20 minutes, 1 h 20 min, 80.";
    public static final String PREPARATION_TIME_VALIDATION_REGEX =
            "([\\d]+[ ]{0,1}(minute[s]{0,1}|min[s]{0,1}|m|hour[s]{0,1}|h){0,1}[ ]{0,1}){1,2}";
    public final String value;

    /**
     * Constructs a {@code PreparationTime}.
     *
     * @param preparationTime A valid preparation time.
     */
    public PreparationTime(String preparationTime) {
        requireNonNull(preparationTime);
        checkArgument(isValidPreparationTime(preparationTime), MESSAGE_PREPARATION_TIME_CONSTRAINTS);
        this.value = preparationTime;
    }

    /**
     * Returns true if a given string is a valid recipe preparation time.
     */
    public static boolean isValidPreparationTime(String test) {
        return test.equals(NULL_PREPARATION_TIME_REFERENCE) || test.matches(PREPARATION_TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PreparationTime // instanceof handles nulls
                && this.value.equals(((PreparationTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
