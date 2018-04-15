package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    //@@author shadow2496
    public final boolean hasError;

    //@@author
    public final String message;

    public NewResultAvailableEvent(String message, boolean hasError) {
        //@@author shadow2496
        this.hasError = hasError;

        //@@author
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
