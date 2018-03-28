package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of {@code CalendarEvent} that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see CalendarEvent#equals(Object)
 */
public class UniqueCalendarEventList implements Iterable<CalendarEvent> {
    private final ObservableList<CalendarEvent> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty UniqueCalendarEventList.
     */
    public UniqueCalendarEventList() {}

    /**
     * Creates a UniqueCalendarEventList using given calendar events.
     * Enforces no nulls.
     */
    public UniqueCalendarEventList(Set<CalendarEvent> calendarEvents) {
        requireAllNonNull(calendarEvents);
        internalList.addAll(calendarEvents);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all orders in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<CalendarEvent> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the CalendarEvent in this list with those in the argument calendar event list.
     */
    public void setCalEvents(Set<CalendarEvent> calendarEvents) {
        requireAllNonNull(calendarEvents);
        internalList.setAll(calendarEvents);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every calendar event in the argument list exists in this object.
     */
    public void mergeFrom(UniqueCalendarEventList from) {
        final Set<CalendarEvent> existingEvents = this.toSet();
        from.internalList.stream()
                .filter(calEvent -> !existingEvents.contains(calEvent))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent {@code CalendarEvent} as the given argument.
     */
    public boolean contains(CalendarEvent toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an CalendarEvent to the list.
     *
     * @throws DuplicateCalendarEventException if the CalendarEvent to add
     * is a duplicate of an existing CalendarEvent in the list.
     */
    public void add(CalendarEvent toAdd) throws DuplicateCalendarEventException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCalendarEventException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes CalendarEvent from list if it exists.
     */
    public void remove(CalendarEvent toRemove) {
        requireNonNull(toRemove);
        if (contains(toRemove)) {
            internalList.remove(toRemove);
        }
    }

    @Override
    public Iterator<CalendarEvent> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<CalendarEvent> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueCalendarEventList // instanceof handles nulls
                && this.internalList.equals(((UniqueCalendarEventList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueCalendarEventList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateCalendarEventException extends DuplicateDataException {
        public DuplicateCalendarEventException() {
            super("Operation would result in duplicate events");
        }
    }

}
