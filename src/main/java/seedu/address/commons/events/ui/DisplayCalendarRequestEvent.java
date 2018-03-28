package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.CalendarEvent;

/**
 * Indicates request to display calendar.
 */
public class DisplayCalendarRequestEvent extends BaseEvent {

    private final ObservableList<CalendarEvent> calendarEvents;

    public DisplayCalendarRequestEvent(ObservableList<CalendarEvent> calendarEvents) {
        this.calendarEvents = calendarEvents;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<CalendarEvent> getCalendarEvents() {
        return calendarEvents;
    }

}
