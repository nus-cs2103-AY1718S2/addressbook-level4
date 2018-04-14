//@@author LeonidAgarth
package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String venue;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String name, String venue, String date, String startTime, String endTime) {
        this.name = name;
        this.venue = venue;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        name = source.getName();
        venue = source.getVenue();
        date = source.getDate();
        startTime = source.getStartTime();
        endTime = source.getEndTime();
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name"));
        }
        if (!Event.isValidName(this.name)) {
            throw new IllegalValueException(Event.MESSAGE_NAME_CONSTRAINTS);
        }
        if (this.venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Venue"));
        }
        if (!Event.isValidName(this.venue)) {
            throw new IllegalValueException(Event.MESSAGE_VENUE_CONSTRAINTS);
        }
        if (this.date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }
        if (!Event.isValidDate(this.date)) {
            throw new IllegalValueException(Event.MESSAGE_DATE_CONSTRAINTS);
        }
        if (this.startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "StartTime"));
        }
        if (!Event.isValidTime(this.startTime)) {
            throw new IllegalValueException(Event.MESSAGE_TIME_CONSTRAINTS);
        }
        if (this.endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "EndTime"));
        }
        if (!Event.isValidTime(this.endTime)) {
            throw new IllegalValueException(Event.MESSAGE_TIME_CONSTRAINTS);
        }

        return new Event(name, venue, date, startTime, endTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherEvent = (XmlAdaptedEvent) other;
        return Objects.equals(name, otherEvent.name)
                && Objects.equals(venue, otherEvent.venue)
                && Objects.equals(date, otherEvent.date)
                && Objects.equals(startTime, otherEvent.startTime)
                && Objects.equals(endTime, otherEvent.endTime);
    }
}
