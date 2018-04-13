//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrderStatusTest {

    @Test
    public void constructor_nullOrderStatus_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new OrderStatus(null));
    }

    @Test
    public void constructor_invalidOrderStatus_throwsIllegalArgumentException() {
        String invalidOrderStatus = "fulfill3d";
        Assert.assertThrows(IllegalArgumentException.class, () -> new OrderStatus(invalidOrderStatus));
    }

    @Test
    public void isValidOrderStatus_nullOrderStatus_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> OrderStatus.isValidOrderStatus(null));
    }

    @Test
    public void isValidOrderStatus_invalidOrderStatus_returnsFalse() {
        assertFalse(OrderStatus.isValidOrderStatus("0ngo!ng_order"));
        assertFalse(OrderStatus.isValidOrderStatus("orderd0ne"));
    }

    @Test
    public void isValidOrderStatus_validOrderStatus_returnsTrue() {
        assertTrue(OrderStatus.isValidOrderStatus("ongoing"));
        assertTrue(OrderStatus.isValidOrderStatus("done"));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        OrderStatus orderStatus = new OrderStatus("ongoing");
        OrderStatus orderStatusCopy = new OrderStatus("ongoing");
        assertEquals(orderStatus, orderStatusCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        OrderStatus orderStatus = new OrderStatus("ongoing");
        assertEquals(orderStatus, orderStatus);
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        OrderStatus orderStatus = new OrderStatus("ongoing");
        assertNotEquals(null, orderStatus);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        OrderStatus firstOrderStatus = new OrderStatus("ongoing");
        OrderStatus secondOrderStatus = new OrderStatus("done");
        assertNotEquals(firstOrderStatus, secondOrderStatus);
    }
}
