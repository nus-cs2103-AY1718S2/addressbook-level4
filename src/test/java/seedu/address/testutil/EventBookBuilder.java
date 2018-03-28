package seedu.address.testutil;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.event.CalendarEvent;

/**
 * A utility class to help with building Eventbook objects.
 * Example usage: <br>
 * {@code EventBook ab = new EventBookBuilder().withEvent(Sentosa).build();}
 */
public class EventBookBuilder {

    private EventBook eventBook;

    public EventBookBuilder() {
        eventBook = new EventBook();
    }

    public EventBookBuilder(EventBook eventBook) {
        this.eventBook = eventBook;
    }

    /**
     * Adds a new {@code Event} to the {@code EventBook} that we are building.
     */
    public EventBookBuilder withEvent(CalendarEvent event) {
        try {
            eventBook.addEvent(event);
        } catch (CommandException e) {
            throw new IllegalArgumentException("event is expected to be unique.");
        }
        return this;
    }


    public EventBook build() {
        return eventBook;
    }
}
