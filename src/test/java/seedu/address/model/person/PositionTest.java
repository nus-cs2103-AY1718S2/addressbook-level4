package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author lithiumlkid
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
        // null position number
        Assert.assertThrows(NullPointerException.class, () -> Position.isValidPosition(null));

        // invalid position numbers
        assertFalse(Position.isValidPosition("")); // empty string
        assertFalse(Position.isValidPosition(" ")); // spaces only
        assertFalse(Position.isValidPosition("0")); // less than 1
        assertFalse(Position.isValidPosition("-1")); // negative
        assertFalse(Position.isValidPosition("position")); // non-numeric
        assertFalse(Position.isValidPosition("1a")); // alphabets within digits
        assertFalse(Position.isValidPosition("1 1")); // spaces within digits

        // valid position numbers
        assertTrue(Position.isValidPosition("1")); // within range
        assertTrue(Position.isValidPosition("4"));
    }

    @Test
    public void isEqualPosition() {
        Position x  = new Position("1");
        Position y = new Position("1");
        assertTrue(x.equals(y) && y.equals(x));
        assertTrue(x.hashCode() == y.hashCode());
    }
}
