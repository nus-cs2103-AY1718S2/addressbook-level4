package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to a particular index in the list of recently selected books.
 */
public class JumpToRecentBooksIndexRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToRecentBooksIndexRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
