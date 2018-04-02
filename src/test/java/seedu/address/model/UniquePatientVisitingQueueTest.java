package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;

public class UniquePatientVisitingQueueTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private UniquePatientVisitingQueue queueToTest = new UniquePatientVisitingQueue();

    @Test
    public void execute_addPatient_addSuccessful() throws Exception {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(1);
        queueToTest.add(1);
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_removePatient_removeSuccessful() throws Exception {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(1);
        queueToTest.add(1);
        queueToTest.removePatient();
        anotherQueue.removePatient();
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_duplicatePatient_throwsDuplicateException() throws Exception {
        queueToTest.add(1);

        thrown.expect(DuplicatePatientException.class);

        queueToTest.add(1);
    }

    @Test
    public void execute_removeEmptyQueue_throwsNoSuchElementException() throws Exception {
        thrown.expect(PatientNotFoundException.class);
        queueToTest.removePatient();
    }

    @Test
    public void execute_setVisitingQueue_setSuccessful() {
        Set<Integer> queueNo = new HashSet<>(Arrays.asList(3, 1, 2));
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        queueToTest.setVisitingQueue(queueNo);
        anotherQueue.setVisitingQueue(queueNo);
        assertEquals(queueToTest, anotherQueue);
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePatientVisitingQueue uniquePatientQueue = new UniquePatientVisitingQueue();
        thrown.expect(UnsupportedOperationException.class);
        uniquePatientQueue.asObservableList().remove(0);
    }
}
