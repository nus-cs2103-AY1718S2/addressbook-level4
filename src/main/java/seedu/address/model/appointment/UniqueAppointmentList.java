package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.ConcurrentAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateDateTimeException;
import seedu.address.model.appointment.exceptions.PastAppointmentException;

//@@author wynonaK
/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#equals(Object)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds an appointment to the list.
     *
     * @throws DuplicateAppointmentException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException, DuplicateDateTimeException,
        PastAppointmentException, ConcurrentAppointmentException {
        requireNonNull(toAdd);

        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }

        ArrayList<LocalDateTime> timeList = new ArrayList<>();

        for (Appointment a : internalList) {
            timeList.add(a.getDateTime());
            if (a.getDateTime().equals(toAdd.getDateTime())) {
                throw new DuplicateDateTimeException();
            }
        }

        for (LocalDateTime dateTime : timeList) {
            if (toAdd.getDateTime().isAfter(dateTime) && toAdd.getDateTime().isBefore(dateTime.plusMinutes(30))) {
                throw new ConcurrentAppointmentException();
            }
            if (toAdd.getDateTime().isBefore(dateTime) && toAdd.getDateTime().plusMinutes(30).isAfter(dateTime)) {
                throw new ConcurrentAppointmentException();
            }
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code editedAppointment}.
     *
     * @throws DuplicateAppointmentException if the replacement is equivalent to
     * another existing appointment in the list.
     * @throws AppointmentNotFoundException if {@code target} could not be found in the list.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment)
            throws DuplicateAppointmentException, AppointmentNotFoundException {
        requireNonNull(editedAppointment);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        if (!target.equals(editedAppointment) && internalList.contains(editedAppointment)) {
            throw new DuplicateAppointmentException();
        }

        internalList.set(index, editedAppointment);
    }

    /**
     * Removes the equivalent pet patient from the list.
     *
     * @throws AppointmentNotFoundException if no such pet patient could be found in the list.
     */
    public boolean remove(Appointment toRemove) throws AppointmentNotFoundException {
        requireNonNull(toRemove);
        final boolean appointmentFoundAndDeleted = internalList.remove(toRemove);
        if (!appointmentFoundAndDeleted) {
            throw new AppointmentNotFoundException();
        }
        return appointmentFoundAndDeleted;
    }

    public void setAppointments(UniqueAppointmentList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setAppointments(List<Appointment> appointments)
            throws DuplicateAppointmentException, DuplicateDateTimeException,
        ConcurrentAppointmentException, PastAppointmentException {
        requireAllNonNull(appointments);
        final UniqueAppointmentList replacement = new UniqueAppointmentList();
        for (final Appointment appointment : appointments) {
            replacement.add(appointment);
        }
        setAppointments(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && this.internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
