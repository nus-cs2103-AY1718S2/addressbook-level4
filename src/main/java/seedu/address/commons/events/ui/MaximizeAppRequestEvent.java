package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Ang-YC
/**
 * Indicates a request for App minimize
 */
public class MaximizeAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
