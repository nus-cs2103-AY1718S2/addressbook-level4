//@@author LeonidAgarth
package seedu.address.testutil;

import seedu.address.model.event.Event;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_NAME = "F1 Race";
    public static final String DEFAULT_VENUE = "Marina Bay Street Circuit";
    public static final String DEFAULT_DATE = "19/07/2018";
    public static final String DEFAULT_START_TIME = "1000";
    public static final String DEFAULT_END_TIME = "1300";

    private String name;
    private String venue;
    private String date;
    private String startTime;
    private String endTime;

    public EventBuilder() {
        name = DEFAULT_NAME;
        venue = DEFAULT_VENUE;
        date = DEFAULT_DATE;
        startTime = DEFAULT_START_TIME;
        endTime = DEFAULT_END_TIME;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        venue = eventToCopy.getVenue();
        date = eventToCopy.getDate();
        startTime = eventToCopy.getStartTime();
        endTime = eventToCopy.getEndTime();
    }

    /**
     * Sets the {@code String name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the {@code String venue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = venue;
        return this;
    }

    /**
     * Sets the {@code String date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code String startTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the {@code String endTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public Event build() {
        return new Event(name, venue, date, startTime, endTime);
    }

}
