package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;

/**
 * Show Google map route from HQ to many locations
 */
public class ShowMultiLocationFromHeadQuarterEvent extends BaseEvent {

    public final List<String> sortedList;

    public ShowMultiLocationFromHeadQuarterEvent(List<String> sortedList) {
        this.sortedList = sortedList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
