package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.patient.UniquePatientList;

public class UniquePatientListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePatientList uniquePatientList = new UniquePatientList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePatientList.asObservableList().remove(0);
    }
}
