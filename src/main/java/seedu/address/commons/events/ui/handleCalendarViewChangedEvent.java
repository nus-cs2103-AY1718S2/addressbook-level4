package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author cambioforma-reused
/**
 * Represents a change in the Google Calendar View
 */
public class handleCalendarViewChangedEvent extends BaseEvent {

    public handleCalendarViewChangedEvent() {

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
