package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;
//@@author cambioforma
public class BirthdayTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Birthday(null));
    }

    @Test
    public void constructor_invalidBirthday_throwsIllegalArgumentException() {
        String invalidBirthday = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Birthday(invalidBirthday));
    }

    @Test
    public void isValidBirthday() {
        // null birthday date
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // invalid birthday dates
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("20052018")); // without dashes
        assertFalse(Birthday.isValidBirthday("birthday")); // non-numeric
        assertFalse(Birthday.isValidBirthday("2018-09-12")); // yyyy-MM-dd format
        assertFalse(Birthday.isValidBirthday("19- 12-2018")); // spaces within date
        assertFalse(Birthday.isValidBirthday("00-12-2018")); // invalid day
        assertFalse(Birthday.isValidBirthday("19-13-2018")); // invalid month
        assertFalse(Birthday.isValidBirthday("19-12-0000")); // invalid year

        // valid birthday dates
        assertTrue(Birthday.isValidBirthday("01-01-2018"));
        assertTrue(Birthday.isValidBirthday("30-12-1990"));
        assertTrue(Birthday.isValidBirthday("29-02-2016")); //leap year
    }
}
