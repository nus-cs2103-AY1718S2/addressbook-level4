package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view calendar and patients' appointments.
 */
public class ShowCalendarViewRequestEvent extends BaseEvent {

    public ShowCalendarViewRequestEvent() {

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
