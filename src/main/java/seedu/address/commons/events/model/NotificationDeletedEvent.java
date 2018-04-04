//@@author IzHoBX
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates timetable entry added/removed*/
public class NotificationDeletedEvent extends BaseEvent {

    public final String id;

    public NotificationDeletedEvent(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "timetable entry deleted: " + id;
    }
}
