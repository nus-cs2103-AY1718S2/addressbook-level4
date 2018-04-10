// @@author kush1509
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.job.UniqueJobList;

public class UniqueJobListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueJobList uniqueJobList = new UniqueJobList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueJobList.asObservableList().remove(0);
    }
}
