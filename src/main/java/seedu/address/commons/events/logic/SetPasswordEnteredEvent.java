package seedu.address.commons.events.logic;
//@@author crizyli
import seedu.address.commons.events.BaseEvent;

/**
 * An event to inform UnlockCommand password is entered.
 */
public class SetPasswordEnteredEvent extends BaseEvent {

    private final String mixPsw;

    public SetPasswordEnteredEvent(String mixPsw) {
        this.mixPsw = mixPsw;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getPassword() {
        return mixPsw;
    }
}
