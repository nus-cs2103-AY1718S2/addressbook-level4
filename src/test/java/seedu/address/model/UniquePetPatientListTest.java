package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.petpatient.UniquePetPatientList;

//@@author chialejing
public class UniquePetPatientListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePetPatientList uniquePetPatientList = new UniquePetPatientList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePetPatientList.asObservableList().remove(0);
    }
}
