//@@author jas5469
package seedu.address.model.group;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.testutil.GroupBuilder;
import seedu.address.testutil.TypicalGroups;

public class DuplicateGroupTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void deleteGroup_groupNotFound_throwsException() throws Exception {
        Group groupToDelete = new GroupBuilder().withInformation("Group Z").build();
        UniqueGroupList groupList = TypicalGroups.getTypicalGroupsList();
        thrown.expect(GroupNotFoundException.class);
        groupList.remove(groupToDelete);
    }
}
