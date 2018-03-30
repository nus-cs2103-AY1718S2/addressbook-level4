package seedu.address.model.policy;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test public void constructor_invalidValue_throwsIllegalArgumentException() {
        double invalidValue = -1.0;
        Assert.assertThrows(IllegalArgumentException.class, () -> new Price(invalidValue));
    }

    @Test
    public void isValidPrice() {
        // null price
        Assert.assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price
        assertFalse(Price.isValidPrice(-1.0)); // Negative price

        // valid price
        assertTrue(Price.isValidPrice(0.0)); // Zero price
        assertTrue(Price.isValidPrice(1.0)); // Positive price
    }
}
