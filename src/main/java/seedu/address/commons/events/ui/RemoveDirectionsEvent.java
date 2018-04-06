package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Signals a removal of listed direction.
 */

public class RemoveDirectionsEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
