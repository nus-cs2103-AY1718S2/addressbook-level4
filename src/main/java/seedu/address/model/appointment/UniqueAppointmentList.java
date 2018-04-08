package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.ClashingAppointmentException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

//@@author jlks96
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
     * Returns true if the list contains an appointment that clashes in time with the given argument.
     */
    public boolean clashesWith(Appointment toCheck) {
        requireNonNull(toCheck);
        if (!internalList.isEmpty()) {
            return internalList.stream().filter(appointment -> checkClashes(appointment, toCheck)).count() > 0;
        } else {
            return false;
        }
    }

    /**
     * Checks if the arguments clashes in time.
     */
    public boolean checkClashes(Appointment firstAppointment, Appointment secondAppointment) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        timeFormatter.setLenient(false);
        java.util.Date firstStartTime;
        java.util.Date firstEndTime;
        java.util.Date secondStartTime;
        java.util.Date secondEndTime;

        try {
            firstStartTime = timeFormatter.parse(
                    firstAppointment.getDate().date + " " + firstAppointment.getStartTime().time);
            firstEndTime = timeFormatter.parse(
                    firstAppointment.getDate().date + " " + firstAppointment.getEndTime().time);
            secondStartTime = timeFormatter.parse(
                    secondAppointment.getDate().date + " " + secondAppointment.getStartTime().time);
            secondEndTime = timeFormatter.parse(
                    secondAppointment.getDate().date + " " + secondAppointment.getEndTime().time);
        } catch (ParseException e) { //this should not happen
            return false;
        }
        if (firstStartTime.compareTo(secondEndTime) < 0 && secondStartTime.compareTo(firstEndTime) < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds a appointment to the list.
     *
     * @throws DuplicateAppointmentException if the appointment to add is a duplicate of an existing appointment
     * in the list.
     * @throws ClashingAppointmentException if the appointment to add clashes with an existing appointment
     * in the list.
     */
    public void add(Appointment toAdd) throws DuplicateAppointmentException, ClashingAppointmentException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }
        if (clashesWith(toAdd)) {
            throw new ClashingAppointmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent appointment from the list.
     *
     * @throws AppointmentNotFoundException if no such appointment could be found in the list.
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
            throws DuplicateAppointmentException, ClashingAppointmentException {
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
