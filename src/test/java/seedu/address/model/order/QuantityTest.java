//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuantityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Quantity(null));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Quantity(invalidQuantity));
    }

    @Test
    public void isValidQuantity() {
        // null quantity
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid quantity
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("sj)")); // non numeric characters
        assertFalse(Quantity.isValidQuantity("2.3")); // decimal
        assertFalse(Quantity.isValidQuantity("-1")); // negative integer
        assertFalse(Quantity.isValidQuantity("0")); // zero
        assertFalse(Quantity.isValidQuantity("+9")); // plus sign

        // valid quantity
        assertTrue(Quantity.isValidQuantity("10")); // positive integer
        assertTrue(Quantity.isValidQuantity("0500")); // leading zero
    }
}
