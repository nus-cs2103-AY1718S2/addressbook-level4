package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of appointment that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Appointment#equals(Object)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {
    private final ObservableList internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AppointmentList.
     */
    public UniqueAppointmentList() {}

    /**
     * Creates a UniqueAppointmentList using given appointments
     * Enforces no nulls
     */
    public UniqueAppointmentList(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.addAll(appointments);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all appointments in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Appointment> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Appointment int this list with those in the argument appointment list.
     */
    public void setAppointment(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.setAll(appointments);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every appointment in the argument list exists in this object.
     */
    public void mergeFrom(UniqueAppointmentList from) {
        final Set<Appointment> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(appointment -> !alreadyInside.contains(appointment))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an quivalent Appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireAllNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a Appointment to the list.
     *
     * @throws DuplicatedAppointmentException if the Appointment to add
     * is a duplicate of an existing appointment in the list.
     */
    public void add(Appointment toAdd) throws DuplicatedAppointmentException {
        requireAllNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatedAppointmentException();
        }

        internalList.addAll(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this
                || (other instanceof UniqueAppointmentList
                    && this.internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    /**
     * Returns true if the element in this list or equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueAppointmentList other) {
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
     * Signals that an operation would have violated the 'no duplicates' property of the list
     */
    public static class DuplicatedAppointmentException extends DuplicateDataException {
        protected DuplicatedAppointmentException() {
            super("Operation would result in duplicate appointments.");
        }
    }
}
