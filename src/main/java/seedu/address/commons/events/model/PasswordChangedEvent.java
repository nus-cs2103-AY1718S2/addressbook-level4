package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/** Indicates a change in the password */
public class PasswordChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
