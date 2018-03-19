package seedu.address.model;

import java.util.LinkedList;

import seedu.address.model.patient.Patient;

/**
 * Patient visiting queue in Imdb
 * Gurantees: immutable
 */
public class UniquePatientVisitingQueue {

    private LinkedList<Patient> visitingQueue;

    public UniquePatientVisitingQueue() {
        visitingQueue = new LinkedList<Patient>();
    }

    public void add(Patient patient) {
        visitingQueue.add(patient);
    }

    public Patient getNextPatient() {
        return visitingQueue.peekFirst();
    }

    public Patient removePatient() {
        return visitingQueue.removeFirst();
    }
}
