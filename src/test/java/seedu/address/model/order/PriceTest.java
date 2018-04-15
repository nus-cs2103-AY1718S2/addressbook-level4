//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
    public void isValidPrice_nullPrice_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));
    }

    @Test
    public void isValidPrice_invalidPrice_returnsFalse() {
        assertFalse(Price.isValidPrice("")); // empty string
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("sj)")); // non numeric characters
        assertFalse(Price.isValidPrice("2.3.20")); // more than one decimal place
        assertFalse(Price.isValidPrice("10.1234")); // more than two digits after decimal point
        assertFalse(Price.isValidPrice("10,00,000")); // commas
        assertFalse(Price.isValidPrice("-1.0")); // negative
        assertFalse(Price.isValidPrice("+10.0")); // plus sign
        assertFalse(Price.isValidPrice("1000000.01")); // out of allowed range
    }

    @Test
    public void isValidPrice_validPrice_returnsTrue() {
        assertTrue(Price.isValidPrice("10.0")); // one digit after decimal point
        assertTrue(Price.isValidPrice("500.75")); // two digits after decimal point
        assertTrue(Price.isValidPrice("015.50")); // leading zero
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Price price = new Price("12.00");
        Price priceCopy = new Price("12.00");
        assertEquals(price, priceCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Price price = new Price("12.00");
        assertEquals(price, price);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Price price = new Price("12.00");
        assertNotEquals(null, price);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Price firstPrice = new Price("12.00");
        Price secondPrice = new Price("500.00");
        assertNotEquals(firstPrice, secondPrice);
    }
}
