package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    @Test
    public void checkPhoneEquality() {
        //test phone against non-phone type
        assertFalse(new Phone("91009222").equals(null));
        assertFalse(new Phone("91009222").equals(new Address("91009222")));
        //test correctly returns equal if phone string is the same
        assertTrue(new Phone("91009222").equals(new Phone("91009222")));
    }

    @Test
    public void checkPhoneHashCode() {
        Phone phone = new Phone("911");
        assertTrue(phone.hashCode() == phone.value.hashCode());
        phone = new Phone("93121534");
        assertTrue(phone.hashCode() == phone.value.hashCode());
        phone = new Phone("124293842033123");
        assertTrue(phone.hashCode() == phone.value.hashCode());
    }
}
