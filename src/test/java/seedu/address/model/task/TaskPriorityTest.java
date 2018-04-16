package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author CYX28
public class TaskPriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskPriority(null));
    }

    @Test
    public void constructor_inValidTaskPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskPriority(invalidPriority));
    }

    @Test
    public void isValidTaskPriority() {
        // null task priority
        Assert.assertThrows(NullPointerException.class, () -> TaskPriority.isValidTaskPriority(null));

        // invalid task priority
        assertFalse(TaskPriority.isValidTaskPriority("")); // empty string
        assertFalse(TaskPriority.isValidTaskPriority(" ")); // spaces only
        assertFalse(TaskPriority.isValidTaskPriority("random xyz")); // random string
        assertFalse(TaskPriority.isValidTaskPriority("12345")); // numbers
        assertFalse(TaskPriority.isValidTaskPriority("random 123")); // alphanumeric

        // valid task priority (high, medium, low)
        assertTrue(TaskPriority.isValidTaskPriority("high"));
        assertTrue(TaskPriority.isValidTaskPriority("medium"));
        assertTrue(TaskPriority.isValidTaskPriority("low"));
    }

}
