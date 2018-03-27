package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class PopulateRequestEvent extends BaseEvent {

    public final String command;

    public PopulateRequestEvent(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + command;
    }
}
