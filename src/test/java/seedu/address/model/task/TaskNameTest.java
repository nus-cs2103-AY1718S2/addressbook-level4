package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TaskName(null));
    }

    @Test
    public void constructor_invalidTaskName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskName(invalidName));
    }

    @Test
    public void isValidTaskName() {
        // null task name
        Assert.assertThrows(NullPointerException.class, () -> TaskName.isValidTaskName(null));

        // invalid task name
        assertFalse(TaskName.isValidTaskName("")); // empty string
        assertFalse(TaskName.isValidTaskName(" ")); // spaces only
        assertFalse(TaskName.isValidTaskName("^")); // only non-alphanumeric characters
        assertFalse(TaskName.isValidTaskName("task*one")); // contains non-alphanumeric characters

        // valid task name
        assertTrue(TaskName.isValidTaskName("task random")); // alphabets only
        assertTrue(TaskName.isValidTaskName("12345")); // numbers only
        assertTrue(TaskName.isValidTaskName("task random 5")); // alphanumeric characters
        assertTrue(TaskName.isValidTaskName("Task Fabulous")); // with capital letters
        assertTrue(TaskName.isValidTaskName("Super Tough Task With More Than 100 hours Spent")); // long names
    }

}
