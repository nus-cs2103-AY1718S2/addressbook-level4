package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Event handler for clearing of all teams.
 */
// @@author Codee
public class ClearTeamsEvent extends BaseEvent {

    public ClearTeamsEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}

