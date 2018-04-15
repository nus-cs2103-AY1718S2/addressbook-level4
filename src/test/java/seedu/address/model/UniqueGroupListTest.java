//@@author jas5469
package seedu.address.model;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.testutil.GroupBuilder;

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

    @Test
    public void equal_sameList_sameResult() throws Exception {
        UniqueGroupList uniqueGroupList1 = new UniqueGroupList();
        UniqueGroupList uniqueGroupList2 = new UniqueGroupList();
        assertEquals(uniqueGroupList1, uniqueGroupList2);
    }

    @Test
    public void set_sameGroup_success() throws Exception {
        UniqueGroupList uniqueGroupList1 = new UniqueGroupList();
        UniqueGroupList uniqueGroupList2 = new UniqueGroupList();
        Group group1 = new GroupBuilder().withInformation("Group A").build();
        Group group2 = new GroupBuilder().withInformation("Group B").build();
        uniqueGroupList1.add(group1);
        uniqueGroupList2.add(group2);
        uniqueGroupList1.setGroup(group1, group2);
        assertEquals(uniqueGroupList1, uniqueGroupList2);
    }

    @Test
    public void iterator() throws Exception {
        UniqueGroupList uniqueGroupList1 = new UniqueGroupList();
        UniqueGroupList uniqueGroupList2 = new UniqueGroupList();
        uniqueGroupList1.add(new GroupBuilder().build());

        Iterator<Group> iter = uniqueGroupList1.iterator();
        while (iter.hasNext()) {
            uniqueGroupList2.add(iter.next());
        }
        assertEquals(uniqueGroupList1, uniqueGroupList2);
    }
}
