//@@author deeheenguyen
package seedu.address.model.interview;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidDate = "0.0.0";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date number
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid phone numbers
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("91")); // less than 3 numbers
        assertFalse(Date.isValidDate("phone")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("9312 1534")); // spaces within digits
        assertFalse(Date.isValidDate("31.14.2018")); // invalid month

        // valid phone numbers
        assertTrue(Date.isValidDate("01.01.2018")); // exactly 3 numbers
        assertTrue(Date.isValidDate("31.12.2018"));
        assertTrue(Date.isValidDate("28.02.2018")); // long phone numbers
    }
}
