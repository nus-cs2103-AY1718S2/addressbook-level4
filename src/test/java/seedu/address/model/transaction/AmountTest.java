package seedu.address.model.transaction;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;
//@@author ongkc
public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }

    @Test
    public void isValidAmount() {
        // null amount
        Assert.assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("^")); // only non-alphanumeric characters
        assertFalse(Amount.isValidAmount("123*")); // contains non-alphanumeric characters
        assertFalse(Amount.isValidAmount("1.2.3")); // contains irregular numeric format
        assertFalse(Amount.isValidAmount("one dollars")); // contains alphabet characters
        assertFalse(Amount.isValidAmount("1 2 3")); // contains spacing between numbers
        assertFalse(Amount.isValidAmount("12345.123")); // numbers & more than 2 decimals
        assertFalse(Amount.isValidAmount("12345.")); // numbers & decimal point only

        // valid name
        assertTrue(Amount.isValidAmount("12345")); // numbers only
        assertTrue(Amount.isValidAmount("12345.1")); // numbers & 1 decimal only
        assertTrue(Amount.isValidAmount("12345.78")); // numbers & 2 decimals only
    }
}
