package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Patient#equals(Object)
 */
public class UniquePatientList implements Iterable<Patient> {

    private final ObservableList<Patient> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent patient as the given argument.
     */
    public boolean contains(Patient toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a patient to the list.
     *
     * @throws DuplicatePatientException if the patient to add is a duplicate of an existing patient in the list.
     */
    public void add(Patient toAdd) throws DuplicatePatientException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePatientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the patient {@code target} in the list with {@code editedPatient}.
     *
     * @throws DuplicatePatientException if the replacement is equivalent to another existing patient in the list.
     * @throws PatientNotFoundException if {@code target} could not be found in the list.
     */
    public void setPerson(Patient target, Patient editedPatient)
            throws DuplicatePatientException, PatientNotFoundException {
        requireNonNull(editedPatient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PatientNotFoundException();
        }

        if (!target.equals(editedPatient) && internalList.contains(editedPatient)) {
            throw new DuplicatePatientException();
        }

        internalList.set(index, editedPatient);
    }

    /**
     * Removes the equivalent patient from the list.
     *
     * @throws PatientNotFoundException if no such patient could be found in the list.
     */
    public boolean remove(Patient toRemove) throws PatientNotFoundException {
        requireNonNull(toRemove);
        final boolean personFoundAndDeleted = internalList.remove(toRemove);
        if (!personFoundAndDeleted) {
            throw new PatientNotFoundException();
        }
        return personFoundAndDeleted;
    }

    public void setPersons(UniquePatientList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setPersons(List<Patient> patients) throws DuplicatePatientException {
        requireAllNonNull(patients);
        final UniquePatientList replacement = new UniquePatientList();
        for (final Patient patient : patients) {
            replacement.add(patient);
        }
        setPersons(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Patient> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    public Patient getPatientByIndex(int index) {
        return internalList.get(index);
    }

    @Override
    public Iterator<Patient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePatientList // instanceof handles nulls
                        && this.internalList.equals(((UniquePatientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
