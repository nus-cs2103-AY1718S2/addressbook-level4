package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author shookshire
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

        // invalid location
        assertFalse(Location.isValidLocation("")); // empty string
        assertFalse(Location.isValidLocation(" ")); // spaces only
        assertFalse(Location.isValidLocation("sodv")); // invalid location
        assertFalse(Location.isValidLocation("north asdf")); // one invalid location
        assertFalse(Location.isValidLocation("fdsaob efowfds idb south")); // multiple invalid location
        assertFalse(Location.isValidLocation("north south north")); // repeated location

        // valid location
        assertTrue(Location.isValidLocation("north"));
        assertTrue(Location.isValidLocation("south"));
        assertTrue(Location.isValidLocation("west"));
        assertTrue(Location.isValidLocation("east"));
        assertTrue(Location.isValidLocation("central"));
        assertTrue(Location.isValidLocation("central north south")); // multiple valid location
    }
}
