package seedu.progresschecker.commons.events.ui;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.commons.events.BaseEvent;

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
