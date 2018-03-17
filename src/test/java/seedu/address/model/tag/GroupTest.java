package seedu.address.model.tag;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class GroupTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Group(null));
    }

    @Test
    public void constructor_invalidGroupName_throwsIllegalArgumentException() {
        String invalidGroupName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Group(invalidGroupName));
    }

    @Test
    public void isValidGroupName() {
        // null group name
        Assert.assertThrows(NullPointerException.class, () -> Group.isValidTagName(null));
    }

}
