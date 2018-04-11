package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

//@@author wynonaK
/**
 * Indicates a request to change to the day view of the date requested.
 */
public class ChangeDayViewRequestEvent extends BaseEvent {

    public final LocalDate date;

    public ChangeDayViewRequestEvent(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
