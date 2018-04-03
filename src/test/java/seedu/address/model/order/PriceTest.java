//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("sj)")); // non numeric characters
        assertFalse(Price.isValidPrice("2.3.20")); // more than one decimal place
        assertFalse(Price.isValidPrice("10.1234")); // more than two digits after decimal point
        assertFalse(Price.isValidPrice("10,00,000")); // commas
        assertFalse(Price.isValidPrice("-1.0")); // negative
        assertFalse(Price.isValidPrice("+10.0")); // plus sign

        // valid price
        assertTrue(Price.isValidPrice("10.0")); // one digit after decimal point
        assertTrue(Price.isValidPrice("500.75")); // two digits after decimal point
        assertTrue(Price.isValidPrice("015.50")); // leading zero
    }
}
