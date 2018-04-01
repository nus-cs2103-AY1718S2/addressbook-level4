// @@author kush1509
package seedu.address.model.job;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

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
    public void isValidLocation() {
        // null location
        Assert.assertThrows(NullPointerException.class, () -> Location.isValidLocation(null));

        // invalid locations
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only

        // valid locations
        assertTrue(Location.isValidLocation("Singapore"));
        assertTrue(Location.isValidLocation("-")); // one character
        assertTrue(Location.isValidLocation("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }

}
