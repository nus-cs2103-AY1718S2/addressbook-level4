//@@author jaronchan
package seedu.address.commons.events.ui;

import java.util.List;

import com.google.api.services.calendar.model.Event;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Scheduled Events shown.
 */
public class DailyScheduleShownChangedEvent extends BaseEvent {


    private final List<Event> dailyEventsList;

    public DailyScheduleShownChangedEvent(List<Event> dailyEventsList) {
        this.dailyEventsList = dailyEventsList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public List<Event> getDailyEventsList() {
        return dailyEventsList;
    }
}
