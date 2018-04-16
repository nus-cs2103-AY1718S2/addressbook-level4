package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Address.isValid(null));

        // invalid addresses
        assertFalse(Address.isValid("")); // empty string
        assertFalse(Address.isValid(" ")); // spaces only

        // valid addresses
        assertTrue(Address.isValid("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValid("-")); // one character
        assertTrue(Address.isValid("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
