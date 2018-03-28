package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StartDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new StartDate(null));
    }

    @Test
    public void constructor_invalidStartDate_throwsIllegalArgumentException() {
        String invalidStartDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new StartDate(invalidStartDate));
    }

    @Test
    public void isValidStartDate() {
        // null start date
        Assert.assertThrows(NullPointerException.class, () -> StartDate.isValidStartDate(null));

        // invalid start date
        assertFalse(StartDate.isValidStartDate("")); // empty string
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only
        assertFalse(StartDate.isValidStartDate("wejo*21")); // invalid string
        assertFalse(StartDate.isValidStartDate("12/12/2012")); // invalid format
        assertFalse(StartDate.isValidStartDate("0-1-98")); // invalid date
        assertFalse(StartDate.isValidStartDate("50-12-1998")); // invalid day
        assertFalse(StartDate.isValidStartDate("10-15-2013")); // invalid month
        assertFalse(StartDate.isValidStartDate("09-08-10000")); // invalid year

        // valid start date
        assertTrue(StartDate.isValidStartDate("01-01-2001")); // valid date
        assertTrue(StartDate.isValidStartDate("29-02-2000")); // leap year
    }
}
