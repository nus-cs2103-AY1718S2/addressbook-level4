package seedu.address.model.job;

import static org.junit.Assert.*;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NumberOfPositionsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new NumberOfPositions(null));
    }

    @Test
    public void constructor_invalidNumberOfPositions_throwsIllegalArgumentException() {
        String invalidNumberOfPositions = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new NumberOfPositions(invalidNumberOfPositions));
    }

    @Test
    public void isValidNumberOfPositions() {
        // null location
        Assert.assertThrows(NullPointerException.class, () -> NumberOfPositions.isValidNumberOfPositions(null));

        // invalid locations
        assertFalse(NumberOfPositions.isValidNumberOfPositions("")); // empty string
        assertFalse(NumberOfPositions.isValidNumberOfPositions(" ")); // spaces only
        assertFalse(NumberOfPositions.isValidNumberOfPositions("abc")); // non-numeric
        assertFalse(NumberOfPositions.isValidNumberOfPositions("9011p041")); // alphabets within digits
        assertFalse(NumberOfPositions.isValidNumberOfPositions("9312 1534")); // spaces within digits

        // valid locations
        assertTrue(NumberOfPositions.isValidNumberOfPositions("1")); // one digit
        assertTrue(NumberOfPositions.isValidNumberOfPositions("33")); 
        assertTrue(NumberOfPositions.isValidNumberOfPositions("3545")); // long number of positions
    }

}