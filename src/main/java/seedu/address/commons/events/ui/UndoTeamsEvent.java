package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Event handler for undoing clearing of all teams.
 */
// @@author Codee
public class UndoTeamsEvent extends BaseEvent {

    public UndoTeamsEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
