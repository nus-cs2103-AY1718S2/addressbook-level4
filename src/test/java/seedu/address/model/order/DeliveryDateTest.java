//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DeliveryDateTest {

    @Test
    public void constructor_nullDeliveryDate_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DeliveryDate(null));
    }

    @Test
    public void constructor_invalidDeliveryDate_throwsIllegalArgumentException() {
        String invalidDeliveryDate = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DeliveryDate(invalidDeliveryDate));
    }

    @Test
    public void isValidDeliveryDate_nullOrderStatus_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> DeliveryDate.isValidDeliveryDate(null));
    }

    @Test
    public void isValidDeliveryDate_invalidDeliveryDate_returnsFalse() {
        assertFalse(DeliveryDate.isValidDeliveryDate("")); // empty string
        assertFalse(DeliveryDate.isValidDeliveryDate(" ")); // spaces only
        assertFalse(DeliveryDate.isValidDeliveryDate("wejo*21")); // invalid string
        assertFalse(DeliveryDate.isValidDeliveryDate("12/12/2012")); // invalid format
        assertFalse(DeliveryDate.isValidDeliveryDate("0-1-98")); // invalid date
        assertFalse(DeliveryDate.isValidDeliveryDate("50-12-1998")); // invalid day
        assertFalse(DeliveryDate.isValidDeliveryDate("10-15-2013")); // invalid month
        assertFalse(DeliveryDate.isValidDeliveryDate("09-08-10000")); // invalid year
        assertFalse(DeliveryDate.isValidDeliveryDate("29-02-2001")); // leap day doesn't exist
    }

    @Test
    public void isValidDeliveryDate_validDeliveryDate_returnsTrue() {
        assertTrue(DeliveryDate.isValidDeliveryDate("01-01-2001")); // valid date
        assertTrue(DeliveryDate.isValidDeliveryDate("29-02-2000")); // leap year
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        DeliveryDate deliveryDate = new DeliveryDate("10-10-2020");
        DeliveryDate deliveryDateCopy = new DeliveryDate("10-10-2020");
        assertEquals(deliveryDate, deliveryDateCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        DeliveryDate deliveryDate = new DeliveryDate("10-10-2020");
        assertEquals(deliveryDate, deliveryDate);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        DeliveryDate deliveryDate = new DeliveryDate("10-10-2020");
        assertNotEquals(null, deliveryDate);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        DeliveryDate firstDeliveryDate = new DeliveryDate("10-10-2020");
        DeliveryDate secondDeliveryDate = new DeliveryDate("11-10-2020");
        assertNotEquals(firstDeliveryDate, secondDeliveryDate);
    }
}
