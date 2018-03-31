package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the request to toggle Notification Center
 */
public class AddressBookUnlockedEvent extends BaseEvent {
    public AddressBookUnlockedEvent() {
        super();
    }

    @Override
    public String toString() {
        return "AddressBook unlocked!";
    }
}
