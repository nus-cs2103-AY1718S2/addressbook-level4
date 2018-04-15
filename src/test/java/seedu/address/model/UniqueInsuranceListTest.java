package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.Insurance.UniqueInsuranceList;

//@@author Sebry9
public class UniqueInsuranceListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueInsuranceList uniqueInsuranceList = new UniqueInsuranceList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueInsuranceList.asObservableList().remove(0);
    }

}
