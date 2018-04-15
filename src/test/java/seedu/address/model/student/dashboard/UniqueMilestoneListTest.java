package seedu.address.model.student.dashboard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author yapni
public class UniqueMilestoneListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueMilestoneList uniqueMilestonelist = new UniqueMilestoneList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueMilestonelist.asObservableList().remove(0);
    }
}
