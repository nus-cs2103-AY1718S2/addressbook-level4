package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

//@@author wynonaK
/**
 * Indicates a request to change to the week view of the date requested.
 */
public class ChangeWeekViewRequestEvent extends BaseEvent {

    public final LocalDate date;

    public ChangeWeekViewRequestEvent(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
