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
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Code(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Code.isValidName(null));

        // invalid name
        assertFalse(Code.isValidName("")); // empty string
        assertFalse(Code.isValidName(" ")); // spaces only
        assertFalse(Code.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Code.isValidName("btc*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Code.isValidName("btc")); // alphabets only
        assertTrue(Code.isValidName("BTC")); // with capital letters
        assertTrue(Code.isValidName("some random coin name or misc account")); // long names
    }
}
