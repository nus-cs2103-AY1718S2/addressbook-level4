package seedu.address.model.job;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PositionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    public void constructor_invalidPosition_throwsIllegalArgumentException() {
        String invalidPosition = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Position(invalidPosition));
    }

    @Test
    public void isValidPosition() {
        // null position
        Assert.assertThrows(NullPointerException.class, () -> Position.isValidPosition(null));

        // invalid position
        assertFalse(Position.isValidPosition("")); // empty string
        assertFalse(Position.isValidPosition(" ")); // spaces only
        assertFalse(Position.isValidPosition("^")); // only non-alphanumeric characters
        assertFalse(Position.isValidPosition("engineer*")); // contains non-alphanumeric characters

        // valid position
        assertTrue(Position.isValidPosition("intern")); // alphabets only
        assertTrue(Position.isValidPosition("12345")); // numbers only
        assertTrue(Position.isValidPosition("2nd associate")); // alphanumeric characters
        assertTrue(Position.isValidPosition("Software Engineer")); // with capital letters
        assertTrue(Position.isValidPosition("Computer Science undergraduate")); // long positions
    }

}