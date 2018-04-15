package seedu.address.commons.events.ui;
//@@author SuxianAlicia
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates request to display calendar entry list.
 */
public class DisplayCalendarEntryListEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
