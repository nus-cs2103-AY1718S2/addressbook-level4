package seedu.recipe.commons.events.ui;

import seedu.recipe.commons.events.BaseEvent;
import seedu.recipe.ui.RecipeCard;

/**
 * Represents a selection change in the Recipe List Panel
 */
public class RecipePanelSelectionChangedEvent extends BaseEvent {


    private final RecipeCard newSelection;

    public RecipePanelSelectionChangedEvent(RecipeCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public RecipeCard getNewSelection() {
        return newSelection;
    }
}
