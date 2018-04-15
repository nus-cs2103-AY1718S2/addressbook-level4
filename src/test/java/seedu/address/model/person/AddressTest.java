package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Address(null));
    }

    //in current implementation, empty address argument is not invalid, it is needed for constructing optional fields
    // as empty
    /*
    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }
    */

    @Test
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    //@@author jonleeyz
    @Test
    public void testHashcode_symmetric() {
        Address addressA = new Address();
        Address addressB = new Address();
        Address addressC = new Address(" ");
        Address addressD = new Address(" ");

        assertEquals(addressA.hashCode(), addressB.hashCode());
        assertEquals(addressC.hashCode(), addressD.hashCode());
        assertNotEquals(addressA.hashCode(), addressC.hashCode());
        assertNotEquals(addressA.hashCode(), addressD.hashCode());
        assertNotEquals(addressB.hashCode(), addressC.hashCode());
        assertNotEquals(addressB.hashCode(), addressD.hashCode());
    }
    //@@author
}
