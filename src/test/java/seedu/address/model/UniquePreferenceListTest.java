package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.UniquePreferenceList;

public class UniquePreferenceListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePreferenceList uniquePreferenceList = new UniquePreferenceList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePreferenceList.asObservableList().remove(0);
    }
}
