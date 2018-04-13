//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
    public void isValidQuantity_nullQuantity_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));
    }

    @Test
    public void isValidQuantity_invalidQuantity_returnsFalse() {
        assertFalse(Quantity.isValidQuantity("")); // empty string
        assertFalse(Quantity.isValidQuantity(" ")); // spaces only
        assertFalse(Quantity.isValidQuantity("sj)")); // non numeric characters
        assertFalse(Quantity.isValidQuantity("2.3")); // decimal
        assertFalse(Quantity.isValidQuantity("-1")); // negative integer
        assertFalse(Quantity.isValidQuantity("0")); // zero
        assertFalse(Quantity.isValidQuantity("+9")); // plus sign
        assertFalse(Quantity.isValidQuantity("100000000")); // out of allowed range
    }

    @Test
    public void isValidQuantity_validQuantity_returnsTrue() {
        assertTrue(Quantity.isValidQuantity("10")); // positive integer
        assertTrue(Quantity.isValidQuantity("0500")); // leading zero allowed
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Quantity quantity = new Quantity("10");
        Quantity quantityCopy = new Quantity("10");
        assertEquals(quantity, quantityCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Quantity quantity = new Quantity("10");
        assertEquals(quantity, quantity);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Quantity quantity = new Quantity("10");
        assertNotEquals(null, quantity);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Quantity firstQuantity = new Quantity("10");
        Quantity secondQuantity = new Quantity("20");
        assertNotEquals(firstQuantity, secondQuantity);
    }
}
