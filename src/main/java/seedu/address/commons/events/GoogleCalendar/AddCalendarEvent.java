package seedu.address.commons.events.GoogleCalendar;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.CalendarEvent.CalendarEvent;

//@@author wayneong95

/**
 * An event requesting to add an event to the calendar.
 */
public class AddCalendarEvent extends BaseEvent {

    private CalendarEvent calendarEvent;

    public AddCalendarEvent(CalendarEvent calendarEvent) { this.calendarEvent = calendarEvent; }

    public CalendarEvent getCalendarEvent() {
        return calendarEvent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
