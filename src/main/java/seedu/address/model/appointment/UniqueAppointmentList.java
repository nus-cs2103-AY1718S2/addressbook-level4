package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
//@@author kengsengg
/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty AppointmentList.
     */
    public UniqueAppointmentList() {}

    public UniqueAppointmentList(Set<Appointment> appointments) {
        requireAllNonNull(appointments);
        internalList.addAll(appointments);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Adds an Appointment to the list.
     *
     * @throws DuplicateAppointmentException if the Appointment to add is a duplicate of an existing Appointment
     * in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException {
        requireNonNull(toAdd);
        if (isAppointmentOverlapped(toAdd)) {
            throw new DuplicateAppointmentException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes the equivalent appointment from the list.
     *
     * @throws AppointmentNotFoundException if no such appointment could be found in the list.
     */
    public boolean remove(Appointment toRemove) throws AppointmentNotFoundException {
        final boolean appointmentFoundAndDeleted = internalList.remove(toRemove);
        if (!appointmentFoundAndDeleted) {
            throw new AppointmentNotFoundException();
        }
        return appointmentFoundAndDeleted;
    }

    public void setAppointments(UniqueAppointmentList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setAppointments(List<Appointment> appointments) throws DuplicateAppointmentException {
        requireAllNonNull(appointments);
        final UniqueAppointmentList replacement = new UniqueAppointmentList();
        for (final Appointment appointment : appointments) {
            replacement.add(appointment);
        }
        setAppointments(replacement);
    }

    /**
     * Returns true if the list contains an equivalent Appointment as the given argument or there is an overlap
     * in appointments
     */
    public boolean isAppointmentOverlapped(Appointment toAdd) {
        for (Appointment appointment : internalList) {
            if (toAdd.getDate().equals(appointment.getDate())) {
                if ((Integer.valueOf(toAdd.getStartTime()) > Integer.valueOf(appointment.getStartTime()))
                    && (Integer.valueOf(toAdd.getStartTime()) < Integer.valueOf(appointment.getEndTime()))) {
                    return true;
                }
                if ((Integer.valueOf(toAdd.getEndTime()) > Integer.valueOf(appointment.getStartTime()))
                    && (Integer.valueOf(toAdd.getEndTime()) < Integer.valueOf(appointment.getEndTime()))) {
                    return true;
                }
                if ((Integer.valueOf(toAdd.getStartTime()) <= Integer.valueOf(appointment.getStartTime()))
                    && (Integer.valueOf(toAdd.getEndTime()) >= Integer.valueOf(appointment.getEndTime()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }
}
//@@author
