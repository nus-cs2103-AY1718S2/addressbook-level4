package seedu.address.commons.events.logic;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates timetable entry added/removed*/
public class RequestToDeleteTimetableEntryEvent extends BaseEvent {

    public final String id;

    public RequestToDeleteTimetableEntryEvent(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "timetable entry deleted: " + id;
    }
}
