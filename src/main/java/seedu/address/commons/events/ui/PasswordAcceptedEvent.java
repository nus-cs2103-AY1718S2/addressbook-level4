package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author limzk1994-reused
public class PasswordAcceptedEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
