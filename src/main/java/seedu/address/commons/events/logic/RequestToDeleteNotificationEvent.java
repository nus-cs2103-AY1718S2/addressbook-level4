//@@author IzHoBX
package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates timetable entry added/removed*/
public class RequestToDeleteNotificationEvent extends BaseEvent {

    public final String id;
    public final boolean deleteFromAddressbookOnly;

    public RequestToDeleteNotificationEvent(String id, boolean deleteFromAddressbookOnly) {
        this.id = id;
        this.deleteFromAddressbookOnly = deleteFromAddressbookOnly;
    }

    @Override
    public String toString() {
        return "timetable entry deleted: " + id;
    }
}
