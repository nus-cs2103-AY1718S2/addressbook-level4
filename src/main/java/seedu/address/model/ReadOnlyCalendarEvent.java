package seedu.address.model;

import seedu.address.model.CalendarEvent.EventEndDate;
import seedu.address.model.CalendarEvent.EventEndTime;
import seedu.address.model.CalendarEvent.EventName;
import seedu.address.model.CalendarEvent.EventStartDate;
import seedu.address.model.CalendarEvent.EventStartTime;

//@@author wayneong95

/**
 * Unmodifiable view of an event.
 */
public interface ReadOnlyCalendarEvent {
    /**
     * Returns an unmodifiable view of the event.
     */
    EventName getEventName();
    EventStartDate getEventStartDate();
    EventStartTime getEventStartTime();
    EventEndDate getEventEndDate();
    EventEndTime getEventEndTime();

}
