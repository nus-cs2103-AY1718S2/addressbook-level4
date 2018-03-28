package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * <p>
 * Supports a minimal set of list operations.
 */
public class EventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<CalendarEvent> mappedList = EasyBind.map(internalList, (event) -> event);

    /**
     * Returns true if the list contains an equivalent event as the given argument.
     */
    public boolean contains(CalendarEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a event to the list.
     */
    public void add(CalendarEvent toAdd) throws CommandException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new CommandException("");
        }
        internalList.add(new Event(toAdd));
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     */
    public void setEvent(CalendarEvent target, CalendarEvent editedEvent)
            throws CommandException {
        requireNonNull(editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CommandException("");
        }

        if (!target.equals(editedEvent) && internalList.contains(editedEvent)) {
            throw new CommandException("");
        }

        internalList.set(index, new Event(editedEvent));
    }

    /**
     * Removes the equivalent event from the list.
     *
     * @throws CommandException if no such event could be found in the list.
     */
    public boolean remove(CalendarEvent toRemove) throws CommandException {
        requireNonNull(toRemove);
        final boolean eventFoundAndDeleted = internalList.remove(toRemove);
        if (!eventFoundAndDeleted) {
            throw new CommandException("");
        }
        return eventFoundAndDeleted;
    }

    public void setEvents(EventList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setEvents(List<? extends CalendarEvent> events) throws CommandException {
        final EventList replacement = new EventList();
        for (final CalendarEvent event : events) {
            replacement.add(new Event(event));
        }
        setEvents(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CalendarEvent> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedList);
    }

    /**
     * Order the list.
     */
    public void orderBy(String parameter) throws CommandException {
        requireNonNull(parameter);
        Comparator<Event> orderByTitle = (Event a, Event b) -> a.getTitle().toString()
                .compareToIgnoreCase(b.getTitle().toString());
        Comparator<Event> orderByLocation = (Event a, Event b) -> a.getLocation().toString()
                .compareToIgnoreCase(b.getLocation().toString());
        Comparator<Event> orderByDatetime = (Event a, Event b) -> {

            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy hhmm");
            try {
                Date datetime1 = formatDate.parse(a.getDatetime());
                Date datetime2 = formatDate.parse(b.getDatetime());

                return datetime2.compareTo(datetime1);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0;

        };

        switch (parameter) {
        case "TITLE":
            internalList.sort(orderByTitle);
            break;

        case "LOCATION":
            internalList.sort(orderByLocation);
            break;

        case "DATETIME":
            internalList.sort(orderByDatetime);
            break;

        default:
            throw new CommandException("");
        }


    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList // instanceof handles nulls
                && this.internalList.equals(((EventList) other).internalList));
    }
}
