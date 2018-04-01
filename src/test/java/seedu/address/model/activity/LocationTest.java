package seedu.address.model.activity;

import org.junit.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
//@@Author YuanQLLer
public class LocationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLocation = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Location(invalidLocation));
    }

    @Test
    public void isValidRemark() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid addresses
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only


        // valid addresses
        assertTrue(Location.isValidLocation("Blk 456, Den Road, #01-355"));
        assertTrue(Location.isValidLocation("-")); // one character
        // long address
        assertTrue(Location.isValidLocation("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA"));
    }

    @Test
    public void hashCodeTest() {
        assertEquals(new Location("Clementi").hashCode(),new Location("Clementi").hashCode());
        assertEquals(new Location("-").hashCode(),new Location("-").hashCode());
    }

    @Test
    public void isEqual() {
        assertTrue(new Location("Clementi").equals(new Location("Clementi")));
        assertFalse(new Location("-").equals(null));
        assertFalse(new Location("-").equals("-"));
        assertFalse(new Location("-").equals(new Location("+")));
    }
}
