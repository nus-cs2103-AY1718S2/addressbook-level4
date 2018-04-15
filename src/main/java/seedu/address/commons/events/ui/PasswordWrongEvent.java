package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author yeggasd

/**
 * Indicates a request to show Wrong Passowrd Dialog
 */
public class PasswordWrongEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
