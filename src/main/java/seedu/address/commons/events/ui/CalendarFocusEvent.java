package seedu.address.commons.events.ui;

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;
//@@author yuxiangSg
/**
 * Represents a calendar GUI focus date request
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
