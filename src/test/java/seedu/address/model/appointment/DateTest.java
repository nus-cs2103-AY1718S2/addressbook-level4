package seedu.address.model.appointment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author jlks96
public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDateAdded = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDateAdded));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("12/34")); // invalid date
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("12 Feb 2018")); // alphabets within date
        assertFalse(Date.isValidDate("12 /12/2018")); // spaces within date
        assertFalse(Date.isValidDate("13/13/2018")); // invalid month

        // valid date
        assertTrue(Date.isValidDate("12/12/2018"));
        assertTrue(Date.isValidDate("01/04/2017"));
    }

    @Test
    public void isEqual_equalDates_success() {
        assertTrue(new Date("01/04/2017").equals(new Date("01/04/2017")));
    }
}
