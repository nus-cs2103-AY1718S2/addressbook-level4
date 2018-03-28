package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.Event;

//@@author kaiyu92

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String datetime;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {
    }

    public XmlAdaptedEvent(CalendarEvent source) {
        title = source.getTitle();
        description = source.getDescription();
        location = source.getLocation();
        datetime = source.getDatetime();
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final String title = new String(this.title);
        final String description = new String(this.description);
        final String location = new String(this.location);
        final String datetime = new String(this.datetime);
        return new Event(title, description, location, datetime);
    }
}
