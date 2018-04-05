package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.CalendarEntryCard;

/**
 * Represents a selection change in the Calendar Entry List Panel
 */
//@@author SuxianAlicia
public class CalendarEntryPanelSelectionChangedEvent extends BaseEvent {

    private final CalendarEntryCard newSelection;

    public CalendarEntryPanelSelectionChangedEvent(CalendarEntryCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public CalendarEntryCard getNewSelection() {
        return newSelection;
    }
}
