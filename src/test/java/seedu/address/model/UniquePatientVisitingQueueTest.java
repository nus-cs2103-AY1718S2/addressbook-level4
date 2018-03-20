package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.patient.Patient;
import seedu.address.model.patient.exceptions.DuplicatePatientException;
import seedu.address.testutil.PatientBuilder;

public class UniquePatientVisitingQueueTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Patient samplePatient = new PatientBuilder().build();
    private UniquePatientVisitingQueue queueToTest = new UniquePatientVisitingQueue();

    @Test
    public void execute_addPatient_addSuccessful() throws DuplicatePatientException {
        UniquePatientVisitingQueue anotherQueue = new UniquePatientVisitingQueue();
        anotherQueue.add(samplePatient);
        queueToTest.add(samplePatient);
        assertEquals(anotherQueue, queueToTest);
    }
}
