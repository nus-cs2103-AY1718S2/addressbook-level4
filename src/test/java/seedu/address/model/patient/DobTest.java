//@@author ktingit
package seedu.address.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class DobTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateOfBirth(null));
    }

    @Test
    public void constructor_invalidDateOfBirth_throwsIllegalArgumentException() {
        String invalidDob = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new DateOfBirth(invalidDob));
    }

    @Test
    public void isValidDob() {
        // null dob
        Assert.assertThrows(NullPointerException.class, () -> DateOfBirth.isValidDob(null));

        // invalid dob
        assertFalse(DateOfBirth.isValidDob("")); // empty string
        assertFalse(DateOfBirth.isValidDob(" ")); // spaces only
        assertFalse(DateOfBirth.isValidDob("ab/cd/efgh")); // non-digits
        assertFalse(DateOfBirth.isValidDob("99/99/9999")); // invalid range
        assertFalse(DateOfBirth.isValidDob("12/31/1990")); // non ddmmyyyy format
        assertFalse(DateOfBirth.isValidDob("1990/12/12")); // non ddmmyyyy format

        // valid dob
        assertTrue(DateOfBirth.isValidDob("11/11/1991")); // standard format
    }
}
