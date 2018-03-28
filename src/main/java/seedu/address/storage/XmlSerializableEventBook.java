package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.ReadOnlyEventBook;
/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "eventbook")
public class XmlSerializableEventBook implements ReadOnlyEventBook {

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

    @Override
    public ObjectProperty<String> titleProperty() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public ObjectProperty<String> descriptionProperty() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public ObjectProperty<String> locationProperty() {
        return null;
    }

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public ObjectProperty<String> datetimeProperty() {
        return null;
    }

    @Override
    public String getDatetime() {
        return null;
    }

    @Override
    public ObservableList<CalendarEvent> getEventList() {
        final ObservableList<CalendarEvent> events = this.events.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(events);
    }
}
