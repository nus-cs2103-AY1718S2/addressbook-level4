package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.UniqueClientList;

//@@author shookshire
public class UniqueClientListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueClientList uniqueClientList = new UniqueClientList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueClientList.asObservableList().remove(0);
    }
}
