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
    public void isValidAddress() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        assertFalse(Address.isValidAddress(" ")); //empty space is now valid address

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Address.isValidAddress("-")); // one character
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

    //@@aussiroth
    @Test
    public void checkAddressEquality() {
        //test address against non-address type
        assertFalse(new Address("Computing Drive").equals(null));
        assertFalse(new Address("Computing Drive").equals(new Name("Computing Drive")));

        //test correctly returns equal if address string is the same
        assertTrue(new Address("Blk 456, Den Road, #01-355").equals(new Address("Blk 456, Den Road, #01-355")));
    }

    @Test
    public void checkAddressHashCode() {
        Address address = new Address("Blk 456, Den Road, #01-355");
        assertTrue(address.hashCode() == address.value.hashCode());
        address = new Address("-");
        assertTrue(address.hashCode() == address.value.hashCode());
        address = new Address("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA");
        assertTrue(address.hashCode() == address.value.hashCode());
    }
}
