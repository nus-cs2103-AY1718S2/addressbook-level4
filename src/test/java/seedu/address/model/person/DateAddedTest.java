package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DateAddedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateAdded(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDateAdded = "";
        Assert.assertThrows(AssertionError.class, () -> new DateAdded(invalidDateAdded));
    }

    @Test
    public void isValidDate() {
        // null date added
        Assert.assertThrows(NullPointerException.class, () -> DateAdded.isValidDate(null));

        // invalid date added
        assertFalse(DateAdded.isValidDate("")); // empty string
        assertFalse(DateAdded.isValidDate(" ")); // spaces only
        assertFalse(DateAdded.isValidDate("12/34")); // invalid date
        assertFalse(DateAdded.isValidDate("date")); // non-numeric
        assertFalse(DateAdded.isValidDate("12 Feb 2018")); // alphabets within date
        assertFalse(DateAdded.isValidDate("12 /12/2018")); // spaces within date

        // valid date added
        assertTrue(DateAdded.isValidDate("12/12/2018"));
        assertTrue(DateAdded.isValidDate("01/04/2017"));
    }
}
