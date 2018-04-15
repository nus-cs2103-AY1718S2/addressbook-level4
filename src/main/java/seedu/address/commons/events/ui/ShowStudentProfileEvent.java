package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author samuelloh
/**
 * Represents a browser display event when a student's profile page is required to be shown
 */
public class ShowStudentProfileEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
