package seedu.organizer.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.organizer.testutil.Assert;

public class PriorityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Priority(null));
    }

    @Test
    public void constructor_invalidPriority_throwsIllegalArgumentException() {
        String invalidPriority = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Priority(invalidPriority));
    }

    @Test
    public void isValidPriority() {
        // null priority number
        Assert.assertThrows(NullPointerException.class, () -> Priority.isValidPriority(null));

        // invalid priority numbers
        assertFalse(Priority.isValidPriority("")); // empty string
        assertFalse(Priority.isValidPriority(" ")); // spaces only
        assertFalse(Priority.isValidPriority("91")); // less than 3 numbers
        assertFalse(Priority.isValidPriority("priority")); // non-numeric
        assertFalse(Priority.isValidPriority("9011p041")); // alphabets within digits
        assertFalse(Priority.isValidPriority("9312 1534")); // spaces within digits

        // valid priority numbers
        assertTrue(Priority.isValidPriority("0"));
        assertTrue(Priority.isValidPriority("1"));
        assertTrue(Priority.isValidPriority("2"));
        assertTrue(Priority.isValidPriority("3"));
        assertTrue(Priority.isValidPriority("4"));
        assertTrue(Priority.isValidPriority("5"));
        assertTrue(Priority.isValidPriority("6"));
        assertTrue(Priority.isValidPriority("7"));
        assertTrue(Priority.isValidPriority("8"));
        assertTrue(Priority.isValidPriority("9"));
    }
}
