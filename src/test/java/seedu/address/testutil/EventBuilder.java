package seedu.address.testutil;

import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.Event;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_TITLE = "Halloween Horror Night";
    public static final String DEFAULT_DESCRIPTION = "Terrifying Night";
    public static final String DEFAULT_LOCATION = "Univsersal Studio";
    public static final String DEFAULT_DATETIME = "13-10-2017 2359";

    private Event event;

    public EventBuilder() {
        String defaultTitle = new String(DEFAULT_TITLE);
        String defaultDescription = new String(DEFAULT_DESCRIPTION);
        String defaultLocation = new String(DEFAULT_LOCATION);
        String defaultDatetime = new String(DEFAULT_DATETIME);
        this.event = new Event(defaultTitle, defaultDescription, defaultLocation, defaultDatetime);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(CalendarEvent eventToCopy) {
        this.event = new Event(eventToCopy);
    }

    /**
     * Sets the {@code title} of the {@code Event} that we are building.
     */
    public EventBuilder withTitle(String title) {
        this.event.setTitle(new String(title));
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.event.setDescription(new String(description));
        return this;
    }

    /**
     * Sets the {@code location} of the {@code Event} that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.event.setLocation(new String(location));
        return this;
    }

    /**
     * Sets the {@code datetime} of the {@code Event} that we are building.
     */
    public EventBuilder withDatetime(String datetime) {
        this.event.setDatetime(new String(datetime));
        return this;
    }

    public Event build() {
        return this.event;
    }
}
