//@@author IzHoBX
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.notification.Notification;

/**
 * Indicates timetable entry added/removed*/
public class NotificationAddedEvent extends BaseEvent {

    public final Notification notification;

    public NotificationAddedEvent(Notification notification) {
        this.notification = notification;
    }

    @Override
    public String toString() {
        return "timetable entry added: " + notification.toString();
    }
}
