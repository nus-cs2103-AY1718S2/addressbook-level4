//@@author ewaldhew
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to spawn a pop-up notification with the given message.
 */
public class ShowNotificationRequestEvent extends BaseEvent {

    private final String message;

    public ShowNotificationRequestEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notifying about: " + message;
    }
}
