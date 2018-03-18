package seedu.recipe.commons.events.model;

import seedu.recipe.commons.events.BaseEvent;
import seedu.recipe.model.ReadOnlyRecipeBook;

/** Indicates the RecipeBook in the model has changed*/
public class RecipeBookChangedEvent extends BaseEvent {

    public final ReadOnlyRecipeBook data;

    public RecipeBookChangedEvent(ReadOnlyRecipeBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of persons " + data.getPersonList().size() + ", number of tags " + data.getTagList().size();
    }
}
