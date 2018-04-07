package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.student.dashboard.UniqueMilestoneList;

//@@author yapni
/**
 * Indicates a request to show the milestones in a student's dashboard
 */
public class ShowMilestonesEvent extends BaseEvent {

    private final UniqueMilestoneList milestoneList;

    public ShowMilestonesEvent(UniqueMilestoneList milestoneList) {
        this.milestoneList = milestoneList;
    }

    public UniqueMilestoneList getMilestoneList() {
        return milestoneList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
