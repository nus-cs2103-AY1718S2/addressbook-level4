package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
/**
 * Indicates that a the text field has been updated.
 */
public class CommandCorrectedEvent extends BaseEvent {

    public final String message;

    public CommandCorrectedEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
