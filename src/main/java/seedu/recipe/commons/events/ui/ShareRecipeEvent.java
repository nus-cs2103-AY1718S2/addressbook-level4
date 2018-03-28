package seedu.recipe.commons.events.ui;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.BaseEvent;
import seedu.recipe.model.recipe.Recipe;

//@@author RyanAngJY
/**
 * Indicates a request to share a recipe
 */
public class ShareRecipeEvent extends BaseEvent {

    public final int targetIndex;
    public final Recipe recipe;

    public ShareRecipeEvent(Index targetIndex, Recipe recipeToShare) {
        this.targetIndex = targetIndex.getZeroBased();
        this.recipe = recipeToShare;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Recipe getTargetRecipe() {
        return recipe;
    }
}
//@@author
