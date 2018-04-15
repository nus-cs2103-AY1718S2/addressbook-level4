package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author olimhc
/**
 * Represents an event when the user wants to switch the list
 */
public class ClientListSwitchEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
