package seedu.address.commons.events.ui;

import java.util.Optional;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates request to display calendar.
 */
//@@author SuxianAlicia
public class DisplayCalendarRequestEvent extends BaseEvent {

    private final Optional<String> calendarView;

    public DisplayCalendarRequestEvent(Optional<String> calendarView) {
        if (calendarView.isPresent()) {
            this.calendarView = calendarView;
        } else {
            this.calendarView = null;
        }
    }

    public String getView() {
        return calendarView.isPresent() ? calendarView.get() : null;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
