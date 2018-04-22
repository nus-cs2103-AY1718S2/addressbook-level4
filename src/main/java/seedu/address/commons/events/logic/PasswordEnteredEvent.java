package seedu.address.commons.events.logic;
//@@author crizyli
import seedu.address.commons.events.BaseEvent;

/**
 * An event to inform UnlockCommand password is entered.
 */
public class PasswordEnteredEvent extends BaseEvent {

    private final String password;

    public PasswordEnteredEvent(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getPassword() {
        return password;
    }
}
