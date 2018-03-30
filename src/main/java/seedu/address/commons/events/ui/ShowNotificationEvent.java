package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.notification.Notification;

/**
 * This event is raised when we need to display notification in Windows 10 notification tray
 */
public class ShowNotificationEvent extends BaseEvent {
    private String ownerName;
    private Notification notification;

    public ShowNotificationEvent(String ownerName, Notification notification) {
        this.ownerName = ownerName;
        this.notification = notification;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Notification getNotification() {
        return notification;
    }

    @Override
    public String toString() {
        return "ShowNotificationEvent: " + notification.toString();
    }
}
