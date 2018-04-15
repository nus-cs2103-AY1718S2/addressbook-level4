package seedu.address.commons.events.ui;
//@@author SuxianAlicia
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates request to display calendar.
 */
public class ChangeCalendarViewRequestEvent extends BaseEvent {

    private final String calendarView;

    public ChangeCalendarViewRequestEvent(String calendarView) {
        this.calendarView = calendarView;
    }

    public String getView() {
        return calendarView;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
