package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EndDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new EndDate(null));
    }

    @Test
    public void constructor_invalidEndDate_throwsIllegalArgumentException() {
        String invalidEndDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new EndDate(invalidEndDate));
    }

    @Test
    public void isValidEndDate() {
        // null end date
        Assert.assertThrows(NullPointerException.class, () -> EndDate.isValidEndDate(null));

        // invalid end date
        assertFalse(EndDate.isValidEndDate("")); // empty string
        assertFalse(EndDate.isValidEndDate(" ")); // spaces only
        assertFalse(EndDate.isValidEndDate("wejo*21")); // invalid string
        assertFalse(EndDate.isValidEndDate("12/12/2012")); // invalid format
        assertFalse(EndDate.isValidEndDate("0-1-98")); // invalid date
        assertFalse(EndDate.isValidEndDate("50-12-1998")); // invalid day
        assertFalse(EndDate.isValidEndDate("10-15-2013")); // invalid month
        assertFalse(EndDate.isValidEndDate("09-08-10000")); // invalid year

        // valid end date
        assertTrue(EndDate.isValidEndDate("01-01-2001")); // valid date
        assertTrue(EndDate.isValidEndDate("29-02-2000")); // leap year
    }
}
