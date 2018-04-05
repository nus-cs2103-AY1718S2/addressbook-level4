package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates request to display calendar entry list.
 */
//@@author SuxianAlicia
public class DisplayCalendarEntryListEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
