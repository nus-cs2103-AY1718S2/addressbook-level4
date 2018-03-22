package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event request to list recently selected books.
 */
public class SwitchToRecentBooksRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
