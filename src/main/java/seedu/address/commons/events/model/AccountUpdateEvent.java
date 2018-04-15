package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Account;

/**
 * Indicates the user account has changed.
 */
public class AccountUpdateEvent extends BaseEvent {
    public final Account data;

    public AccountUpdateEvent(Account data) {
        this.data = data;
    }

    public String toString() {
        return "username is " + data.getUsername() + " and password is " + data.getPassword();
    }
}
