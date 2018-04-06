package seedu.address.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsNumberFormatException() {
        String invalidPriority = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {

        // invalid priority values
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("phone")); // non-numeric
        assertFalse(Priority.isValidPriority("911")); // more than 1 number

        // valid priority values
        assertTrue(Priority.isValidPriority("1")); //only 3 unique cases for priority
        assertTrue(Priority.isValidPriority("2"));
        assertTrue(Priority.isValidPriority("3"));
    }
}

