package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

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

        // invalid major
        assertFalse(Major.isValidMajor("")); // empty string
        assertFalse(Major.isValidMajor(" ")); // spaces only
        assertFalse(Major.isValidMajor("^")); // only non-alphanumeric characters
        assertFalse(Major.isValidMajor("comp*")); // contains non-alphanumeric characters

        // valid major
        assertTrue(Major.isValidMajor("computer science")); // alphabets only
        assertTrue(Major.isValidMajor("12345")); // numbers only
        assertTrue(Major.isValidMajor("2nd major in Business")); // alphanumeric characters
        assertTrue(Major.isValidMajor("Computer Engineering")); // with capital letters
        assertTrue(Major.isValidMajor("Business Analytics and Information Security")); // long names
    }
}
