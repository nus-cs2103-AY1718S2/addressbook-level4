//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrderInformationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OrderInformation(null));
    }

    @Test
    public void constructor_invalidOrderInformation_throwsIllegalArgumentException() {
        String invalidOrderInformation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OrderInformation(invalidOrderInformation));
    }

    @Test
    public void isValidOrderStatus_nullOrderInformation_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> OrderInformation.isValidOrderInformation(null));
    }

    @Test
    public void isValidOrderStatus_invalidOrderInformation_returnsFalse() {
        assertFalse(OrderInformation.isValidOrderInformation("")); // empty string
        assertFalse(OrderInformation.isValidOrderInformation(" ")); // spaces only
    }

    @Test
    public void isValidOrderInformation_validOrderInformation_returnsTrue() {
        assertTrue(OrderInformation.isValidOrderInformation("Books")); // single word
        assertTrue(OrderInformation.isValidOrderInformation("Confectionery Boxes")); // multiple words
        assertTrue(OrderInformation.isValidOrderInformation("NBA 2k18")); // alphanumeric
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        OrderInformation orderInformation = new OrderInformation("Books");
        OrderInformation orderInformationCopy = new OrderInformation("Books");
        assertEquals(orderInformation, orderInformationCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        OrderInformation orderInformation = new OrderInformation("Books");
        assertEquals(orderInformation, orderInformation);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        OrderInformation orderInformation = new OrderInformation("Books");
        assertNotEquals(null, orderInformation);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        OrderInformation firstOrderInformation = new OrderInformation("Books");
        OrderInformation secondOrderInformation = new OrderInformation("Chocolates");
        assertNotEquals(firstOrderInformation, secondOrderInformation);
    }
}
