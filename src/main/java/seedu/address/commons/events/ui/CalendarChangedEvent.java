package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.CalendarDate;

/**
 * Represents a selection change in the Person List Panel
 */
public class CalendarChangedEvent extends BaseEvent {

    private final CalendarDate newSelection;

    public CalendarChangedEvent() {
        this.newSelection = null;
    }

    public CalendarChangedEvent(CalendarDate newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public CalendarDate getNewSelection() {
        return newSelection;
    }
}
