package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates timetable entry added/removed*/
public class TimetableEntryDeletedEvent extends BaseEvent {

    public final String id;

    public TimetableEntryDeletedEvent(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "timetable entry deleted: " + id;
    }
}
