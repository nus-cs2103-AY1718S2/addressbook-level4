package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author wayneong95

/**
 * An event requesting to view calendar.
 */
public class ShowCalendarEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
