package seedu.address.model.student.dashboard;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author yapni
public class UniqueTaskListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTaskList.asObservableList().remove(0);
    }
}
