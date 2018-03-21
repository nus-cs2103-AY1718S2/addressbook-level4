package seedu.recipe.commons.events.ui;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.BaseEvent;

/**
 * Indicates a request to share a recipe
 */
public class ShareRecipeEvent extends BaseEvent {

    public final int targetIndex;

    public ShareRecipeEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
