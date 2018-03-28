package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskStatus(null));
    }

    @Test
    public void constructor_invalidTaskStatus_throwsIllegalArgumentException() {
        String invalidStatus = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskStatus(invalidStatus));
    }

    @Test
    public void isValidTaskStatus() {
        // null task status
        Assert.assertThrows(NullPointerException.class, () -> TaskStatus.isValidTaskStatus(null));

        // invalid task status
        assertFalse(TaskStatus.isValidTaskStatus("")); // empty string
        assertFalse(TaskStatus.isValidTaskStatus(" ")); // spaces only
        assertFalse(TaskStatus.isValidTaskStatus("random xyz")); // random string
        assertFalse(TaskStatus.isValidTaskStatus("12345")); // numbers
        assertFalse(TaskStatus.isValidTaskStatus("random 123")); // alphanumeric

        // valid task status (done or undone starting with upper or lowercase)
        assertTrue(TaskStatus.isValidTaskStatus("Done"));
        assertTrue(TaskStatus.isValidTaskStatus("done"));
        assertTrue(TaskStatus.isValidTaskStatus("Undone"));
        assertTrue(TaskStatus.isValidTaskStatus("undone"));
    }

}

