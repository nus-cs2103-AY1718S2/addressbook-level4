package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.StudentCard;

/**
 * Represents a browser display event when a student is selected
 */
public class BrowserDisplayEvent extends BaseEvent {

    private final StudentCard studentSelection;

    public BrowserDisplayEvent(StudentCard studentSelection) {
        this.studentSelection = studentSelection;
    }

    public StudentCard getStudentSelection() {
        return studentSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
