package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CurrentPositionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CurrentPosition(null));
    }

    @Test
    public void constructor_invalidCurrentPosition_throwsIllegalArgumentException() {
        String invalidCurrentPosition = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CurrentPosition(invalidCurrentPosition));
    }

    @Test
    public void isValidCurrentPosition() {
        // null current position
        Assert.assertThrows(NullPointerException.class, () -> CurrentPosition.isValidCurrentPosition(null));

        // invalid current position
        assertFalse(CurrentPosition.isValidCurrentPosition("")); // empty string
        assertFalse(CurrentPosition.isValidCurrentPosition(" ")); // spaces only
        assertFalse(CurrentPosition.isValidCurrentPosition("^")); // only non-alphanumeric characters
        assertFalse(CurrentPosition.isValidCurrentPosition("engineer*")); // contains non-alphanumeric characters

        // valid current position
        assertTrue(CurrentPosition.isValidCurrentPosition("engineer")); // alphabets only
        assertTrue(CurrentPosition.isValidCurrentPosition("12345")); // numbers only
        assertTrue(CurrentPosition.isValidCurrentPosition("2nd year student")); // alphanumeric characters
        assertTrue(CurrentPosition.isValidCurrentPosition("Marketing Intern")); // with capital letters
        assertTrue(CurrentPosition.isValidCurrentPosition("First year undergraduate student")); // long position
    }
}
