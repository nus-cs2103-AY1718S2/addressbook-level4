package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author AzuraAiR
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
        // null birthday
        Assert.assertThrows(NullPointerException.class, () -> Birthday.isValidBirthday(null));

        // invalid birthdays
        assertFalse(Birthday.isValidBirthday("")); // empty string
        assertFalse(Birthday.isValidBirthday(" ")); // spaces only
        assertFalse(Birthday.isValidBirthday("123456")); // less than 8 numbers
        assertFalse(Birthday.isValidBirthday("12345678")); // more than 8 numbers
        assertFalse(Birthday.isValidBirthday("32011995")); // invalid day
        assertFalse(Birthday.isValidBirthday("01131995")); // invalid month
        assertFalse(Birthday.isValidBirthday("phonezzz")); // non-numeric
        assertFalse(Birthday.isValidBirthday("9011p041")); // alphabets within digits
        assertFalse(Birthday.isValidBirthday("12 04 1995")); // spaces within digits

        // valid birthday
        assertTrue(Birthday.isValidBirthday("01011995")); // exactly 6 numbers
    }

    @Test
    public void getValidDayMonth() {
        Birthday birthdayStub = new Birthday("01121995");

        assertTrue(birthdayStub.getDay() == 1); // check Day
        assertTrue(birthdayStub.getMonth() == 12); // check Month
        assertTrue(birthdayStub.getYear() == 1995); // check Year
    }
}

