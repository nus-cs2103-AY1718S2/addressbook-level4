package seedu.address.model.group;
//@@author jas5469

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.GroupBuilder;

public class GroupTest {
    @Test
    public void equals() {
        Group groupA = new GroupBuilder().withInformation("Group A").build();
        Group groupB = new GroupBuilder().withInformation("Group A").build();

        // different types -> returns false
        assertFalse(groupA.equals(1));

        // same content -> returns true
        assertTrue(groupA.hashCode() == groupB.hashCode());
    }
}
