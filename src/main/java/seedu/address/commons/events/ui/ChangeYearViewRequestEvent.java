package seedu.address.commons.events.ui;

import java.time.Year;

import seedu.address.commons.events.BaseEvent;

//@@author wynonaK
/**
 * Indicates a request to change to the year view of the year requested.
 */
public class ChangeYearViewRequestEvent extends BaseEvent {

    public final Year year;

    public ChangeYearViewRequestEvent(Year year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
