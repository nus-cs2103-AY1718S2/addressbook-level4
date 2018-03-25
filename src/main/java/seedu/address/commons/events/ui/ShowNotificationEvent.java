package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * This event is raised when we need to display notification in Windows 10 notification tray
 */
public class ShowNotificationEvent extends BaseEvent {
    private String ownerName;
    private String endTime;
    private String title;

    public ShowNotificationEvent(String ownerName, String endTime, String title) {
        this.ownerName = ownerName;
        this.endTime = endTime;
        this.title = title;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "ShowNotificationEvent: " + ownerName + " " + title + " " + endTime;
    }
}
