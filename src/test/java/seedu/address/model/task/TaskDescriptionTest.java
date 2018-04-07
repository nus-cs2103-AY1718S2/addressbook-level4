package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TaskDescriptionTest {

    @Test
    public void constructor_null_throwsAssertionError() {
        Assert.assertThrows(AssertionError.class, () -> new TaskDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TaskDescription(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> TaskDescription.isValidDescription(null));

        // invalid name
        assertFalse(TaskDescription.isValidDescription("")); // empty string
        assertFalse(TaskDescription.isValidDescription(" ")); // spaces only

        // valid name
        assertTrue(TaskDescription.isValidDescription("^")); // only non-alphanumeric characters
        assertTrue(TaskDescription.isValidDescription("peter*")); // contains non-alphanumeric characters
        assertTrue(TaskDescription.isValidDescription("peter jack")); // alphabets only
        assertTrue(TaskDescription.isValidDescription("12345")); // numbers only
        assertTrue(TaskDescription.isValidDescription("peter the 2nd")); // alphanumeric characters
        assertTrue(TaskDescription.isValidDescription("Capital Tan")); // with capital letters
        assertTrue(TaskDescription.isValidDescription("David Roger Jackson Ray Jr 2nd")); // long names
    }
}

