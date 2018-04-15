package seedu.progresschecker.model.issue;

import org.junit.Test;

import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.testutil.Assert;

//@@author adityaa1998
public class AssigneesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Assignees(null));
    }

    @Test
    public void constructor_invalidAssigneeName_throwsIllegalArgumentException() {
        String invalidAssigneeName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Assignees(invalidAssigneeName));
    }

    @Test
    public void isValidAssigneeName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Assignees.isValidAssignee(null));
    }
}
