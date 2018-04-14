//@@author ewaldhew
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to spawn a pop-up notification with the given message.
 */
public class ShowNotificationRequestEvent extends BaseEvent {

    private static final String MESSAGE_NOTIFYING = "Notifying about: %1$s triggers %2$s";

    /** The index of the coin that triggered this notification */
    public final Index targetIndex;

    /** The code of the coin that triggered this notification */
    public final String codeString;

    private final String message;

    public ShowNotificationRequestEvent(String message, Index index, String codeString) {
        this.message = message;
        this.targetIndex = index;
        this.codeString = codeString;
    }

    @Override
    public String toString() {
        return String.format(MESSAGE_NOTIFYING, codeString, message);
    }
}
