package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.util.DateUtil;
import seedu.address.testutil.Assert;

//@@author SuxianAlicia
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
        Assert.assertThrows(NullPointerException.class, () -> DateUtil.isValidDate(null));

        // invalid start date
        assertFalse(DateUtil.isValidDate("")); // empty string
        assertFalse(DateUtil.isValidDate(" ")); // spaces only
        assertFalse(DateUtil.isValidDate("wejo*21")); // invalid string
        assertFalse(DateUtil.isValidDate("12/12/2012")); // invalid format
        assertFalse(DateUtil.isValidDate("0-1-98")); // invalid date
        assertFalse(DateUtil.isValidDate("50-12-1998")); // invalid day
        assertFalse(DateUtil.isValidDate("10-15-2013")); // invalid month
        assertFalse(DateUtil.isValidDate("09-08-10000")); // invalid year

        // valid start date
        assertTrue(DateUtil.isValidDate("01-01-2001")); // valid date
        assertTrue(DateUtil.isValidDate("29-02-2000")); // leap year
    }
}
