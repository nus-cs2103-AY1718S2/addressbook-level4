package seedu.address.testutil;

import seedu.address.model.CalendarEvent.CalendarEvent;
import seedu.address.model.CalendarEvent.EventEndDate;
import seedu.address.model.CalendarEvent.EventEndTime;
import seedu.address.model.CalendarEvent.EventName;
import seedu.address.model.CalendarEvent.EventStartDate;
import seedu.address.model.CalendarEvent.EventStartTime;

//@@author wayneong95

/**
 * A utility class to help with building CalendarEvent objects.
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Weights Training";
    public static final String DEFAULT_START_DATE = "2018-05-20";
    public static final String DEFAULT_START_TIME = "15:30";
    public static final String DEFAULT_END_DATE = "2018-05-20";
    public static final String DEFAULT_END_TIME = "16:30";


    private EventName eventName;
    private EventStartDate eventStartDate;
    private EventStartTime eventStartTime;
    private EventEndDate eventEndDate;
    private EventEndTime eventEndTime;

    public EventBuilder() {
        eventName = new EventName(DEFAULT_EVENT_NAME);
        eventStartDate = new EventStartDate(DEFAULT_START_DATE);
        eventStartTime = new EventStartTime(DEFAULT_START_TIME);
        eventEndDate = new EventEndDate(DEFAULT_END_DATE);
        eventEndTime = new EventEndTime(DEFAULT_END_TIME);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(CalendarEvent eventToCopy) {
        eventName = eventToCopy.getEventName();
        eventStartDate = eventToCopy.getEventStartDate();
        eventStartTime = eventToCopy.getEventStartTime();
        eventEndDate = eventToCopy.getEventEndDate();
        eventEndTime = eventToCopy.getEventEndTime();
    }

    /**
     * Sets the {@code EventName} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Sets the {@code EventStartDate} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withStartDate(String eventStartDate) {
        this.eventStartDate = new EventStartDate(eventStartDate);
        return this;
    }

    /**
     * Sets the {@code EventStartTime} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withStartTime(String eventStartTime) {
        this.eventStartTime = new EventStartTime(eventStartTime);
        return this;
    }

    /**
     * Sets the {@code EventEndDate} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withEndDate(String eventEndDate) {
        this.eventEndDate = new EventEndDate(eventEndDate);
        return this;
    }

    /**
     * Sets the {@code EventEndTime} of the {@code CalendarEvent} that we are building.
     */
    public EventBuilder withEndTime(String eventEndTime) {
        this.eventEndTime = new EventEndTime(eventEndTime);
        return this;
    }

    /**
     * Builds a new CalendarEvent.
     * */
    public CalendarEvent build() {
        return new CalendarEvent(eventName, eventStartDate, eventStartTime,
                eventEndDate, eventEndTime);
    }

}
