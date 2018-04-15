package seedu.address.commons.events.ui;
//@@author SuxianAlicia
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates request to change page that calendar is displaying.
 * A page represents a day, week or month, depending on the current view of the calendar.
 */
public class ChangeCalendarPageRequestEvent extends BaseEvent {

    private final String requestType;

    public ChangeCalendarPageRequestEvent(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
