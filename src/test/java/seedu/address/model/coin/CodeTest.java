package seedu.address.model.coin;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class CodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Code(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Code(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        Assert.assertThrows(NullPointerException.class, () -> Code.isValidCode(null));

        // invalid phone numbers
        assertFalse(Code.isValidCode("")); // empty string
        assertFalse(Code.isValidCode(" ")); // spaces only
        assertFalse(Code.isValidCode("TA")); // less than 3 letters
        assertFalse(Code.isValidCode("9011p041")); // alphabets within digits
        assertFalse(Code.isValidCode("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Code.isValidCode("AAA")); // exactly 3 letters
        assertTrue(Code.isValidCode("BTCBTC"));
        assertTrue(Code.isValidCode("QWERTYASDFGHZXCVB")); // long phone numbers
    }
}
