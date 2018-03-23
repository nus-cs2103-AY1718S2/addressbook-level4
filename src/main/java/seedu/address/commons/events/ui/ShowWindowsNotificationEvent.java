package seedu.address.commons.events.ui;

import java.sql.Time;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.model.TimetableEntryAddedEvent;
import seedu.address.model.timetableentry.TimetableEntry;

/**
 * This event is raised when we need to display notification in Windows 10 notification tray
 */
public class ShowWindowsNotificationEvent extends BaseEvent {
    private TimetableEntry timetableEntry;

    public ShowWindowsNotificationEvent(TimetableEntry timetableEntry) {
        this.timetableEntry = timetableEntry;
    }

    @Override
    public String toString() {
        return "ShowWindowsNotificationEvent{" + "timetableEntry=" + timetableEntry + '}';
    }

    public TimetableEntry getTimetableEntry() {
        return timetableEntry;
    }
}
