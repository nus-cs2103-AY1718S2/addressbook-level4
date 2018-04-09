//@@author nhatquang3112
package seedu.address.model.todo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_DONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_UNDONE;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        String invalidStatus = "invalid status";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Status(invalidStatus));
    }

    @Test
    public void isValidStatus() {
        // null status
        Assert.assertThrows(NullPointerException.class, () -> Status.isValidStatus(null));

        // invalid status
        assertFalse(Status.isValidStatus("")); // empty string
        assertFalse(Status.isValidStatus(" ")); // spaces only
        assertFalse(Status.isValidStatus("^")); // only non-alphanumeric characters
        assertFalse(Status.isValidStatus("Some status*")); // contains non-alphanumeric characters
        assertFalse(Status.isValidStatus("invalid status")); // is neither "done" or "undone"

        // valid status
        assertTrue(Status.isValidStatus("done"));
        assertTrue(Status.isValidStatus("undone"));
    }

    @Test
    public void isSameStatusHashCode() {
        Status firstStatus = new Status(VALID_STATUS_DONE);
        Status secondStatus = new Status(VALID_STATUS_DONE);
        assertTrue(firstStatus.hashCode() == secondStatus.hashCode());

        Status thirdStatus = new Status(VALID_STATUS_UNDONE);
        Status forthStatus = new Status(VALID_STATUS_UNDONE);
        assertTrue(thirdStatus.hashCode() == forthStatus.hashCode());
    }

    @Test
    public void equals() {
        Status firstStatus = new Status(VALID_STATUS_DONE);
        Status secondStatus = new Status(VALID_STATUS_DONE);
        assertTrue(firstStatus.equals(firstStatus));
        assertTrue(firstStatus.equals(secondStatus));
    }

    @Test
    public void isSameStatusString() {
        Status status = new Status(VALID_STATUS_DONE);
        assertTrue(status.toString().equals(VALID_STATUS_DONE));
    }
}
