package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a change in the person details panel, but no paramaters.
 *
 /** @@author Codee */
public class PersonDetailsChangedNoParamEvent extends BaseEvent {

    public PersonDetailsChangedNoParamEvent() { }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
