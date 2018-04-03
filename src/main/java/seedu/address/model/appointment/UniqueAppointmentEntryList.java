package seedu.address.model.appointment;

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
 * A list of appointment entries that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Appointment#equals(Object)
 */
public class UniqueAppointmentEntryList implements Iterable<AppointmentEntry> {

    private final ObservableList internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AppointmentEntryList.
     */
    public UniqueAppointmentEntryList() {}

    /**
     * Returns all appointment entries in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<AppointmentEntry> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the appointment entries in this list with those in the argument appointment entry list.
     */
    public void setAppointmentEntry(Set<AppointmentEntry> appointmentEntries) {
        requireAllNonNull(appointmentEntries);
        internalList.setAll(appointmentEntries);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent appointment entry as the given argument.
     */
    public boolean contains(AppointmentEntry toCheck) {
        requireAllNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a AppointmentEntry to the list.
     *
     * @throws DuplicatedAppointmentEntryException if the AppointmentEntry to add
     * is a duplicate of an existing appointment in the list.
     */
    public void add(AppointmentEntry toAdd) throws DuplicatedAppointmentEntryException {
        requireAllNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatedAppointmentEntryException();
        }

        internalList.addAll(toAdd);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<AppointmentEntry> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<AppointmentEntry> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this
                || (other instanceof UniqueAppointmentEntryList
                && this.internalList.equals(((UniqueAppointmentEntryList) other).internalList));
    }

    /**
     * Returns true if the element in this list or equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueAppointmentEntryList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    /**
     * Ensures every appointment entry in the argument list exists in this object.
     */
    public void mergeFrom(UniqueAppointmentList from, String patientName) throws DuplicatedAppointmentEntryException {
        final Set<Appointment> alreadyInside = from.toSet();

        for (Appointment appt : alreadyInside) {
            AppointmentEntry newEntry = new AppointmentEntry(appt, patientName);

            if (!contains(newEntry)) {
                add(newEntry);
            }
        }

        assert CollectionUtil.elementsAreUnique(internalList);
    }
    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Returns true if the element is deleted..
     */
    public boolean remove(AppointmentEntry toRemove) {
        requireNonNull(toRemove);
        return internalList.remove(toRemove);
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list
     */
    public static class DuplicatedAppointmentEntryException extends DuplicateDataException {
        protected DuplicatedAppointmentEntryException() {
            super("Operation would result in duplicate appointments.");
        }
    }
}
