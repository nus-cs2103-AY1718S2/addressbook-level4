package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.event.ReadOnlyEventBook;

//@@author x3tsunayh

/**
 * An Immutable EventBook that is serializable to XML format
 */
@XmlRootElement(name = "eventbook")
public class XmlSerializableEventBook {

    private static final Logger logger = LogsCenter.getLogger(XmlEventBookStorage.class);

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableEventBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableEventBook() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableEventBook(ReadOnlyEventBook src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this eventbook into the model's {@code EventBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedEvent}.
     */
    public EventBook toModelType() throws IllegalValueException, CommandException {
        EventBook eventBook = new EventBook();
        for (XmlAdaptedEvent e : events) {
            eventBook.addEvent(e.toModelType());
        }
        return eventBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableEventBook)) {
            return false;
        }

        XmlSerializableEventBook otherAb = (XmlSerializableEventBook) other;
        return events.equals(otherAb.events);
    }
}
