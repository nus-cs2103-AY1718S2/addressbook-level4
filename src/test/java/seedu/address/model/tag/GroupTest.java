package seedu.address.model.tag;
//@@author SuxianAlicia
import org.junit.Test;

import seedu.address.testutil.Assert;

public class GroupTest {

    @Test
    public void constructor_nullGroupTagName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Group(null));
    }

    @Test
    public void constructor_invalidGroupTagName_throwsIllegalArgumentException() {
        String invalidGroupTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Group(invalidGroupTagName));
    }

    @Test
    public void isValidGroupTagName() {
        // null group name
        Assert.assertThrows(NullPointerException.class, () -> Group.isValidTagName(null));
    }
}
