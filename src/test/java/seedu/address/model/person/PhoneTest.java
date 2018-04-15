package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    /*not invalid
    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }
    */

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        //not invalid -- assertFalse(Phone.isValidPhone("")); // empty string
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

    //@@author jonleeyz
    @Test
    public void testHashcode_symmetric() {
        Phone phoneA = new Phone();
        Phone phoneB = new Phone();
        Phone phoneC = new Phone("999");
        Phone phoneD = new Phone("999");

        assertEquals(phoneA.hashCode(), phoneB.hashCode());
        assertEquals(phoneC.hashCode(), phoneD.hashCode());
        assertNotEquals(phoneA.hashCode(), phoneC.hashCode());
        assertNotEquals(phoneA.hashCode(), phoneD.hashCode());
        assertNotEquals(phoneB.hashCode(), phoneC.hashCode());
        assertNotEquals(phoneB.hashCode(), phoneD.hashCode());
    }
    //@@author
}
