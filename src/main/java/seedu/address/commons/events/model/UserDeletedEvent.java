package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.login.User;

//@@author kaisertanqr
/** Indicates a User in the model has been deleted*/
public class UserDeletedEvent extends BaseEvent {

    public final User data;

    public UserDeletedEvent(User user) {
        this.data = user;
    }

    @Override
    public String toString() {
        return "user deleted: " + data.getUsername().toString();
    }
}
