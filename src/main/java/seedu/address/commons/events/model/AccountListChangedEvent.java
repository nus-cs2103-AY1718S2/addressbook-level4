package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.account.UniqueAccountList;

/**
 * Indicates the AccountList in the model has changed
 */
public class AccountListChangedEvent extends BaseEvent {

    public final UniqueAccountList data;

    public AccountListChangedEvent(UniqueAccountList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Number of accounts: " + data.size();
    }
}
