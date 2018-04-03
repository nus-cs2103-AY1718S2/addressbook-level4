//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeliveryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DeliveryDate(null));
    }

    @Test
    public void constructor_invalidDeliveryDate_throwsIllegalArgumentException() {
        String invalidDeliveryDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DeliveryDate(invalidDeliveryDate));
    }

    @Test
    public void isValidDeliveryDate() {
        // null delivery date
        Assert.assertThrows(NullPointerException.class, () -> DeliveryDate.isValidDeliveryDate(null));

        // invalid delivery date
        assertFalse(DeliveryDate.isValidDeliveryDate("")); // empty string
        assertFalse(DeliveryDate.isValidDeliveryDate(" ")); // spaces only
        assertFalse(DeliveryDate.isValidDeliveryDate("wejo*21")); // invalid string
        assertFalse(DeliveryDate.isValidDeliveryDate("12/12/2012")); // invalid format
        assertFalse(DeliveryDate.isValidDeliveryDate("0-1-98")); // invalid date
        assertFalse(DeliveryDate.isValidDeliveryDate("50-12-1998")); // invalid day
        assertFalse(DeliveryDate.isValidDeliveryDate("10-15-2013")); // invalid month
        assertFalse(DeliveryDate.isValidDeliveryDate("09-08-10000")); // invalid year

        // valid delivery date
        assertTrue(DeliveryDate.isValidDeliveryDate("01-01-2001")); // valid date
        assertTrue(DeliveryDate.isValidDeliveryDate("29-02-2000")); // leap year
    }
}
