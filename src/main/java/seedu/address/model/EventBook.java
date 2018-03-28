package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.CalendarEvent;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventList;
import seedu.address.model.event.ReadOnlyEventBook;

//@@author kaiyu92

/**
 * Wraps all data at the event-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class EventBook implements ReadOnlyEventBook {

    private final EventList events;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        events = new EventList();
    }

    public EventBook() {
    }

    /**
     * Creates an EventBook using the Events in the {@code toBeCopied}
     */
    public EventBook(ReadOnlyEventBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setEvents(List<? extends CalendarEvent> events) throws CommandException {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code EventBook} with {@code newData}.
     */
    public void resetData(ReadOnlyEventBook newData) {
        requireNonNull(newData);
        try {
            setEvents(newData.getEventList());
        } catch (CommandException e) {
            assert false : "EventBooks should not have duplicate events";
        }
    }

    /**
     * Adds an event to the event book.
     *
     * @throws CommandException if an equivalent event already exists.
     */
    public void addEvent(CalendarEvent e) throws CommandException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedReadOnlyEvent}.
     *
     */
    public void updateEvent(CalendarEvent target, CalendarEvent editedReadOnlyEvent)
            throws CommandException {
        requireNonNull(editedReadOnlyEvent);

        Event editedPerson = new Event(editedReadOnlyEvent);
        events.setEvent(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code EventBook}.
     */
    public boolean removeEvent(CalendarEvent key) throws CommandException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new CommandException("");
        }
    }

    /**
     * Order list of all events in the event Book based on the parameter.
     */
    public void orderList(String parameter) throws CommandException {
        events.orderBy(parameter);
    }

    //// util methods

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
    public ObservableList<CalendarEvent> getEventList() {
        return events.asObservableList();
    }
}
