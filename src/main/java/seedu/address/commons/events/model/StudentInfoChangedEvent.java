package seedu.address.commons.events.model;

//@@author samuelloh
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a paticular student's info has changed
 */
public class StudentInfoChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
