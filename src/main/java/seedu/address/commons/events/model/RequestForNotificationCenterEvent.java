package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Will be raised if Model Manager has null NotificationCenter.
 */
public class RequestForNotificationCenterEvent extends BaseEvent {

    @Override
    public String toString() {
        return "Requesting for Notification Center";
    }
}
