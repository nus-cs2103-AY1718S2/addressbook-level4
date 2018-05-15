package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to a particular index in the list of books.
 */
public class JumpToBookListIndexRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToBookListIndexRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
