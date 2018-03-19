package seedu.progresschecker.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.progresschecker.testutil.Assert;

public class MajorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Major(null));
    }

    @Test
    public void constructor_invalidMajor_throwsIllegalArgumentException() {
        String invalidMajor = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Major(invalidMajor));
    }

    @Test
    public void isValidMajor() {
        // null major
        Assert.assertThrows(NullPointerException.class, () -> Major.isValidMajor(null));

        // invalid majors
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only

        // valid majors
        assertTrue(Major.isValidMajor("Blk 456, Den Road, #01-355"));
        assertTrue(Major.isValidMajor("-")); // one character
        assertTrue(Major.isValidMajor("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long major
    }
}
