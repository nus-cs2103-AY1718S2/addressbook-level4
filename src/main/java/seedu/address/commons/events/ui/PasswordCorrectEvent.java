package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author yeggasd
/**
 * Indicates a request for App start for Correct Password
 */
public class PasswordCorrectEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
