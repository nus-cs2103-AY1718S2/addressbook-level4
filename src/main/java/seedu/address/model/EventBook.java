package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.ReadOnlyEventBook;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.DuplicateEventException;

//@@author x3tsunayh

/**
 * Wraps all data at the event-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class EventBook implements ReadOnlyEventBook {

    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new UniqueEventList();
    }

    public EventBook() {}

    /**
     * Creates an EventBook using the Events in the {@code toBeCopied}
     */
    public EventBook(ReadOnlyEventBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setEvents(List<? extends ReadOnlyEvent> events) {
        try {
            this.events.setEvents(events);
        } catch (DuplicateEventException dee) {
            throw new AssertionError("Eventbooks should not have duplicate events");
        }
    }

    /**
     * Resets the existing data of this {@code EventBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEventBook newData) {
        requireNonNull(newData);
        try {
            this.events.setEvents(newData.getEventList());
        } catch (DuplicateEventException dee) {
            throw new AssertionError("Eventbooks should not have duplicated events");
        }
    }

    /**
     * Adds an event to the event book.
     *
     * @throws CommandException if an equivalent event already exists.
     */
    public void addEvent(ReadOnlyEvent e) throws CommandException, DuplicateEventException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Removes {@code key} from this {@code EventBook}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws CommandException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new CommandException("");
        }
    }

    /**
     * Sorts list of all events in the event Book based on the specified parameter.
     */
    public void sortBy(String parameter) throws CommandException {
        events.sortBy(parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventBook // instanceof handles nulls
                && this.events.equals(((EventBook) other).events));
    }

    @Override
    public String toString() {
        return events.asObservableList().size() + " events";
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
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
    }
}
