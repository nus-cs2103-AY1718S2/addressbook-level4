//@@author IzHoBX
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the request to toggle Notification Center
 */
public class ToggleNotificationCenterEvent extends BaseEvent {
    public ToggleNotificationCenterEvent() {
        super();
    }

    @Override
    public String toString() {
        return "Toggling Notification Center";
    }
}
