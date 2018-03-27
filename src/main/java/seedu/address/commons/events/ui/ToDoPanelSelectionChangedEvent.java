package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.ToDoCard;

/**
 * Represents a selection change in the Person List Panel
 */
public class ToDoPanelSelectionChangedEvent extends BaseEvent {


    private final ToDoCard newSelection;

    public ToDoPanelSelectionChangedEvent(ToDoCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
