package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Represents a page change in the Browser Panel
 */
public class PageLoadChangedEvent extends BaseEvent {


    public final int targetIndex;

    public PageLoadChangedEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getOneBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public int getPageIndex() {
        return targetIndex;
    }
}
