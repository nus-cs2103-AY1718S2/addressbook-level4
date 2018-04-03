//@@author amad-person
package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
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
    public void isValidOrderInformation() {
        // null order information
        Assert.assertThrows(NullPointerException.class, () -> OrderInformation.isValidOrderInformation(null));

        // invalid order information
        assertFalse(OrderInformation.isValidOrderInformation("")); // empty string
        assertFalse(OrderInformation.isValidOrderInformation(" ")); // spaces only

        // valid order information
        assertTrue(OrderInformation.isValidOrderInformation("Books"));
        assertTrue(OrderInformation.isValidOrderInformation("Facial Cleanser"));
        assertTrue(OrderInformation.isValidOrderInformation("Confectionery Boxes"));
    }
}
