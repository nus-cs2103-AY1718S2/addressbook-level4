package seedu.address.model.CalendarEvent;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.ReadOnlyCalendarEvent;

//@@author wayneong95

/**
 * Represents a Calendar event in the Personal Trainer Pro app.
 * Guarantees: details are present and not null, field values are validated.
 */

public class CalendarEvent implements ReadOnlyCalendarEvent {

    private final EventName eventName;
    private final EventStartDate eventStartDate;
    private final EventStartTime eventStartTime;
    private final EventEndDate eventEndDate;
    private final EventEndTime eventEndTime;

    public CalendarEvent (EventName eventName, EventStartDate eventStartDate, EventStartTime eventStartTime,
                          EventEndDate eventEndDate, EventEndTime eventEndTime) {

        requireAllNonNull(eventName, eventStartDate, eventStartTime, eventEndDate, eventEndTime);

        this.eventName = eventName;
        this.eventStartDate = eventStartDate;
        this.eventStartTime = eventStartTime;
        this.eventEndDate = eventEndDate;
        this.eventEndTime = eventEndTime;
    }

    @Override
    public EventName getEventName() { return eventName; }

    @Override
    public EventStartDate getEventStartDate() {
        return eventStartDate;
    }

    @Override
    public EventStartTime getEventStartTime() {
        return eventStartTime;
    }

    @Override
    public EventEndDate getEventEndDate() {
        return eventEndDate;
    }

    @Override
    public EventEndTime getEventEndTime() {
        return eventEndTime;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Start Date: ")
                .append(getEventStartDate())
                .append(" Start Time: ")
                .append(getEventStartTime())
                .append(" End Date: ")
                .append(getEventEndDate())
                .append(" End Time: ")
                .append(getEventEndTime());
        return builder.toString();
    }
}
