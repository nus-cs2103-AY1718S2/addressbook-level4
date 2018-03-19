package seedu.recipe.model.recipe.exceptions;

import seedu.recipe.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Recipe objects.
 */
public class DuplicateRecipeException extends DuplicateDataException {
    public DuplicateRecipeException() {
        super("Operation would result in duplicate recipes");
    }
}
