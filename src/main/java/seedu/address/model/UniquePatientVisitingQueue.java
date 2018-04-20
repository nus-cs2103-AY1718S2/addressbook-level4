//@@author Kyholmes
package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

/**
 * Patient visiting queue in Imdb
 * Gurantees: immutable
 */
public class UniquePatientVisitingQueue implements Iterable<Integer> {

    private LinkedList<Integer> visitingQueue;

    public UniquePatientVisitingQueue() {
        visitingQueue = new LinkedList<Integer>();
    }

    /**
     * Adds a patient into visiting queue.
     *
     * @throws DuplicatePatientException if the patient index to add is a duplicate of an existing patient index in the
     * queue.
     */
    public void add(int patientIndex) throws DuplicatePatientException {
        requireNonNull(patientIndex);

        if (contains(patientIndex)) {
            throw new DuplicatePatientException();
        }

        visitingQueue.add(patientIndex);
    }

    /**
     * Removes the first patient from the visiting queue.
     *
     * @throws PatientNotFoundException if the queue is empty.
     */
    public int removePatient() throws PatientNotFoundException {
        if (visitingQueue.isEmpty()) {
            throw new PatientNotFoundException();
        }

        return visitingQueue.removeFirst();
    }

    /**
     * Removes a specific patient from the visiting queue.
     *
     * @throws PatientNotFoundException if the queue is empty.
     */
    public void removePatient(int index) throws PatientNotFoundException {
        if (visitingQueue.isEmpty()) {
            throw new PatientNotFoundException();
        }

        if (!contains(index)) {
            throw new PatientNotFoundException();
        }

        Integer targetIndex = index;

        visitingQueue.remove(targetIndex);
    }

    public LinkedList<Integer> getVisitingQueue() {
        return visitingQueue;
    }

    /**
     * Returns true if the queue contains an equivalent patient index as the given argument.
     */
    public boolean contains(int toCheck) {
        requireNonNull(toCheck);
        return visitingQueue.contains(toCheck);
    }

    @Override
    public Iterator<Integer> iterator() {
        return visitingQueue.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniquePatientVisitingQueue
                && this.visitingQueue.equals(((UniquePatientVisitingQueue) other).visitingQueue));
    }

    @Override
    public int hashCode() {
        return visitingQueue.hashCode();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Integer> asObservableList() {
        ObservableList<Integer> patientList = FXCollections.observableArrayList(this.visitingQueue);
        return FXCollections.unmodifiableObservableList(patientList);
    }

    /**
     * Replaces the visiting queue in this list with those in the argument visiting queue.
     */
    public void setVisitingQueue(Set<Integer> queueNos) {
        requireAllNonNull(queueNos);
        visitingQueue.clear();
        visitingQueue.addAll(queueNos);
        assert CollectionUtil.elementsAreUnique(visitingQueue);
    }
}
