package seedu.address.model;
//@@author jas5469

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.UniqueGroupList;

public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asObservableList().remove(0);
    }

    @Test
    public void hashCode_sameList_sameResult() throws Exception {
        UniqueGroupList uniqueGroupList1 = new UniqueGroupList();
        UniqueGroupList uniqueGroupList2 = new UniqueGroupList();
        assertEquals(uniqueGroupList1.hashCode(), uniqueGroupList2.hashCode());

    }
}
