package seedu.address.commons.events.ui;
//@@author crizyli
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show the password input dialog.
 */
public class ShowPasswordFieldEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
