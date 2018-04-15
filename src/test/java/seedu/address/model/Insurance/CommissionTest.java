package seedu.address.model.Insurance;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Sebry9
public class CommissionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Insurance(null));
    }

    @Test
    public void constructor_invalidCommission_throwsIllegalArgumentException() {
        String invalidCommission = "Health[-100]";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Insurance(invalidCommission));
    }
}
//@@author
