package seedu.address.model.student.dashboard;

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
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        Assert.assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // missing parts
        assertFalse(Date.isValidDate("01/02/2018")); // missing time
        assertFalse(Date.isValidDate("22:11")); // missing date
        assertFalse(Date.isValidDate("01/02 22:11")); // missing year
        assertFalse(Date.isValidDate("01/2018 22:11")); // missing day

        // incorrect number of digits
        assertFalse(Date.isValidDate("1/02/2018 22:11")); // day with 1 digit
        assertFalse(Date.isValidDate("111/02/2018 22:11")); // day with 3 digits
        assertFalse(Date.isValidDate("01/2/2018 22:11")); // month with 1 digit
        assertFalse(Date.isValidDate("01/222/2018 22:11")); // month with 3 digit
        assertFalse(Date.isValidDate("01/02/18 22:11")); // year with 2 digits
        assertFalse(Date.isValidDate("01/02/18181 22:11")); // year with 5 digits
        assertFalse(Date.isValidDate("01/02/2018 2:11")); // hour with 1 digit
        assertFalse(Date.isValidDate("01/02/2018 2222:11")); // hour with 3 digits
        assertFalse(Date.isValidDate("01/02/2018 22:1")); // minute with 1 digit
        assertFalse(Date.isValidDate("01/02/2018 22:111")); // minute with 3 digits

        // invalid values
        assertFalse(Date.isValidDate("32/02/2018 22:11")); // invalid day
        assertFalse(Date.isValidDate("01/13/2018 22:11")); // invalid month
        assertFalse(Date.isValidDate("01/02/2018 24:11")); // invalid hour
        assertFalse(Date.isValidDate("01/02/2018 22:61")); // invalid minutes

        // valid dates
        assertTrue(Date.isValidDate("01/01/2018 22:11"));
        assertTrue(Date.isValidDate("31/12/2020 23:59"));
        assertTrue(Date.isValidDate("31/12/2020 00:00"));
    }
}
