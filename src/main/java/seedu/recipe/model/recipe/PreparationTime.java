package seedu.recipe.model.recipe;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

/**
 * Represents a Recipe's preparationTime number in the recipe book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPreparationTime(String)}
 */
public class PreparationTime {


    public static final String MESSAGE_PREPARATION_TIME_CONSTRAINTS =
            "PreparationTime numbers can only contain numbers, and should be at least 3 digits long";
    public static final String PREPARATION_TIME_VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code PreparationTime}.
     *
     * @param preparationTime A valid preparationTime number.
     */
    public PreparationTime(String preparationTime) {
        requireNonNull(preparationTime);
        checkArgument(isValidPreparationTime(preparationTime), MESSAGE_PREPARATION_TIME_CONSTRAINTS);
        this.value = preparationTime;
    }

    /**
     * Returns true if a given string is a valid recipe preparationTime number.
     */
    public static boolean isValidPreparationTime(String test) {
        return test.matches(PREPARATION_TIME_VALIDATION_REGEX);
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
