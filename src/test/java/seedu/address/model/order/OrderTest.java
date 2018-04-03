//@@author amad-person
package seedu.address.model.order;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class OrderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Order(null, null, null, null));
    }
}
