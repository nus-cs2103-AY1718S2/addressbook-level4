package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;
//@@author chuakunhong
public class NricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null nric number
        Assert.assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid nric numbers
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("91")); // less than 3 numbers
        assertFalse(Nric.isValidNric("ic")); // non-numeric
        assertFalse(Nric.isValidNric("9011p041")); // alphabets within digits
        assertFalse(Nric.isValidNric("S9312 153A")); // spaces within digits

        // valid nric number
        assertTrue(Nric.isValidNric("S9312154Z"));
    }
}
//@@author
