package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.notification.Notification;

/**
 * This listEvent is raised when we need to display notification in Windows 10 notification tray
 */
public class ShowNotificationEvent extends BaseEvent {
    private String ownerName;
    private Notification notification;
    private boolean isFirstSatge;

    public ShowNotificationEvent(String ownerName, Notification notification) {
        this.ownerName = ownerName;
        this.notification = notification;
        isFirstSatge = false;
    }

    public ShowNotificationEvent(String ownerName, Notification notification, boolean isFirstSatge) {
        this.ownerName = ownerName;
        this.notification = notification;
        this.isFirstSatge = isFirstSatge;
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

    public boolean isFirstSatge() {
        return isFirstSatge;
    }
}
