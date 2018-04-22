package seedu.address.commons.events.ui;
//@@author crizyli
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to show the set password dialog.
 */
public class ShowSetPasswordDialogEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
