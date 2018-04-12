package seedu.address.commons.events.ui;
//@@author SuxianAlicia
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.CalendarEntryCard;

/**
 * Represents a selection change in the Calendar Entry List Panel
 */
public class CalendarEntryPanelSelectionChangedEvent extends BaseEvent {

    private final CalendarEntryCard newSelection;

    public CalendarEntryPanelSelectionChangedEvent(CalendarEntryCard newSelection) {
        this.newSelection = newSelection;
    }

    public CalendarEntryCard getNewSelection() {
        return newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
