package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.StudentCard;

/**
 * Represents a selection change in the Student List Panel
 */
public class StudentPanelSelectionChangedEvent extends BaseEvent {


    private final StudentCard newSelection;

    public StudentPanelSelectionChangedEvent(StudentCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public StudentCard getNewSelection() {
        return newSelection;
    }
}
