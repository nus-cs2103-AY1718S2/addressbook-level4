package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TodoCard;

/**
 * Represents a selection change in the TodoList Panel
 */
public class TodoPanelSelectionChangedEvent extends BaseEvent {


    private final TodoCard newSelection;

    public TodoPanelSelectionChangedEvent(TodoCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TodoCard getNewSelection() {
        return this.newSelection;
    }
}
