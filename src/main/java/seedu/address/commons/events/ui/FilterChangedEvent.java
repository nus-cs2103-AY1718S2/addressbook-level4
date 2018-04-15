package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Notifies the UI that the list filter has changed
 */
public class FilterChangedEvent extends BaseEvent {

    public final String status;

    public FilterChangedEvent(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Filter changed to: " + status;
    }
}
