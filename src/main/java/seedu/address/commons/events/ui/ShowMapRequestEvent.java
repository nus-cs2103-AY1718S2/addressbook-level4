package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Articho28
/**
 * An event requesting to view a map showing the nearest ATM.
 */
public class ShowMapRequestEvent extends BaseEvent {

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
