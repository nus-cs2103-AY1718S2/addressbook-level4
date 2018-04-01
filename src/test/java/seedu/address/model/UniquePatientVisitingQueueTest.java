package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.model.patient.exceptions.PatientNotFoundException;
import seedu.address.testutil.PatientBuilder;

public class UniquePatientVisitingQueueTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private UniquePatientVisitingQueue queueToTest = new UniquePatientVisitingQueue();

    @Test
    public void execute_addPatient_addSuccessful() throws Exception {
        Patient samplePatient = new PatientBuilder().build();
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(1);
        queueToTest.add(1);
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_removePatient_removeSuccessful() throws Exception {
        Patient samplePatient = new PatientBuilder().build();
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(1);
        queueToTest.add(1);
        queueToTest.removePatient();
        anotherQueue.removePatient();
        assertEquals(anotherQueue, queueToTest);
    }

    @Test
    public void execute_duplicatePatient_throwsDuplicateException() throws Exception {
        Patient samplePatient = new PatientBuilder().build();
        queueToTest.add(1);

        thrown.expect(DuplicatePatientException.class);

        queueToTest.add(1);
    }

    @Test
    public void execute_addNullObject_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        queueToTest.add(Integer.parseInt(null));
    }

    @Test
    public void execute_removeEmptyQueue_throwsNoSuchElementException() throws Exception {
        thrown.expect(PatientNotFoundException.class);
        queueToTest.removePatient();
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePatientVisitingQueue uniquePatientQueue = new UniquePatientVisitingQueue();
        thrown.expect(UnsupportedOperationException.class);
        uniquePatientQueue.asObservableList().remove(0);
    }
}
