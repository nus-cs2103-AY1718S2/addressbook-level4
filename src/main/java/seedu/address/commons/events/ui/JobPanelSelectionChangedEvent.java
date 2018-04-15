package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.JobCard;

/**
 * Represents a selection change in the Job List Panel
 */
public class JobPanelSelectionChangedEvent extends BaseEvent {

    private final JobCard newSelection;

    public JobPanelSelectionChangedEvent(JobCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public JobCard getNewSelection() {
        return newSelection;
    }
}
