package seedu.ptman.commons.events.ui;

import seedu.ptman.commons.core.index.Index;
import seedu.ptman.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of employees
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public final boolean isNewSelection;

    public JumpToListRequestEvent(Index targetIndex, boolean isNewSelection) {
        this.targetIndex = targetIndex.getZeroBased();
        this.isNewSelection = isNewSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
