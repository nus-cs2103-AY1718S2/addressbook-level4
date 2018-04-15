package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.InterviewCard;


/**
 * Represents a selection change in the Job List Panel
 */
public class InterviewPanelSelectionChangedEvent  extends BaseEvent {
    private final InterviewCard newSelection;

    public InterviewPanelSelectionChangedEvent(InterviewCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public InterviewCard getNewSelection() {
        return newSelection;
    }
}
