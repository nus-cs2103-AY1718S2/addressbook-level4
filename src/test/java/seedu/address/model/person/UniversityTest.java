package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author kexiaowen
public class UniversityTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new University(null));
    }

    @Test
    public void constructor_invalidUniversity_throwsIllegalArgumentException() {
        String invalidUniversity = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new University(invalidUniversity));
    }

    @Test
    public void isValidUniversity() {
        // null university
        Assert.assertThrows(NullPointerException.class, () -> University.isValidUniversity(null));

        // invalid university
        assertFalse(University.isValidUniversity("")); // empty string
        assertFalse(University.isValidUniversity(" ")); // spaces only
        assertFalse(University.isValidUniversity("^")); // only non-alphanumeric characters
        assertFalse(University.isValidUniversity("N*S")); // contains non-alphanumeric characters

        // valid name
        assertTrue(University.isValidUniversity("ntu")); // alphabets only
        assertTrue(University.isValidUniversity("12345")); // numbers only
        assertTrue(University.isValidUniversity("nus the 1st")); // alphanumeric characters
        assertTrue(University.isValidUniversity("National University of Singapore")); // with capital letters
        assertTrue(University.isValidUniversity("University of California Santa Barbara")); // long names
    }
}
