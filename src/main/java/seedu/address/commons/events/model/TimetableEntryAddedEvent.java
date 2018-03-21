package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.timetableentry.TimetableEntry;

/**
 * Indicates timetable entry added/removed*/
public class TimetableEntryAddedEvent extends BaseEvent {

    public final TimetableEntry timetableEntry;

    public TimetableEntryAddedEvent(TimetableEntry timetableEntry) {
        this.timetableEntry = timetableEntry;
    }

    @Override
    public String toString() {
        return "timetable entry added: " + timetableEntry.toString();
    }
}
