package seedu.address.model.activity;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void constructor_invalidRemark_throwsIllegalArgumentException() {
        String invalidRemark = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Remark(invalidRemark));
    }

    @Test
    public void isValidRemark() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));

        // invalid addresses
        assertTrue(Remark.isValidRemark(" Hello")); // empty string
        assertTrue(Remark.isValidRemark(" ")); // spaces only

        // valid addresses
        assertTrue(Remark.isValidRemark("Blk 456, Den Road, #01-355"));
        assertTrue(Remark.isValidRemark("-")); // one character
        assertTrue(Remark.isValidRemark("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
