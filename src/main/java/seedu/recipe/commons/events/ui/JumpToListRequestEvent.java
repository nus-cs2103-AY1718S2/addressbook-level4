package seedu.recipe.commons.events.ui;

import seedu.recipe.commons.core.index.Index;
import seedu.recipe.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of recipes
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
