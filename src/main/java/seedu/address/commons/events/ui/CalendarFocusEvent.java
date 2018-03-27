package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a calendar GUI refresh request
 */
public class CalendarFocusEvent extends BaseEvent {
    public final LocalDate dateToLook;

    public CalendarFocusEvent(LocalDate dateToLook) {
        this.dateToLook = dateToLook;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
