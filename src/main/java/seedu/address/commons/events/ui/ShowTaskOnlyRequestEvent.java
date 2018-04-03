package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view only events.
 */
public class ShowTaskOnlyRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
