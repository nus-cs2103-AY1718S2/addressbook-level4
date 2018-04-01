package seedu.address.model;

import static junit.framework.TestCase.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.tag.UniqueTagList;

public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }

    @Test
    public void testEquals_sameSubjectObject_returnTrue() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        assertEquals(uniqueTagList, uniqueTagList1);
    }

    @Test
    public void hashCode_checkForHashCode_sameHashCode() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        UniqueTagList uniqueTagList1 = new UniqueTagList();
        assertEquals(uniqueTagList.hashCode(), uniqueTagList1.hashCode());
    }
}
