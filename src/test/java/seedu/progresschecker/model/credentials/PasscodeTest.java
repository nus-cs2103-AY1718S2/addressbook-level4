package seedu.progresschecker.model.credentials;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.progresschecker.testutil.Assert;

//@@author adityaa1998
public class PasscodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Passcode(null));
    }

    @Test
    public void constructor_invalidPasscode_throwsIllegalArgumentException() {
        String invalidPasscode = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Passcode(invalidPasscode));
    }

    @Test
    public void isValidPasscode() {
        // null passcode
        Assert.assertThrows(NullPointerException.class, () -> Passcode.isValidPasscode(null));

        // invalid passcode
        assertFalse(Passcode.isValidPasscode("")); // empty string
        assertFalse(Passcode.isValidPasscode(" ")); // spaces only
        assertFalse(Passcode.isValidPasscode("^")); // only non-alphanumeric characters
        assertFalse(Passcode.isValidPasscode("ads12")); // only lowercase and numbers with less than 7 characters
        assertFalse(Passcode.isValidPasscode("cajacxvccxk")); // alphabets only
        assertFalse(Passcode.isValidPasscode("12345")); // numbers only
        assertFalse(Passcode.isValidPasscode("ADDD1232")); // capital letter and numbers only
        assertFalse(Passcode.isValidPasscode("git*")); // contains characters with less than 7 characters


        // valid passcode
        assertTrue(Passcode.isValidPasscode("adityaathe2nd")); // alphanumeric characters with numerals
        assertTrue(Passcode.isValidPasscode("giTHub/repo-4")); // with capital letters
        assertTrue(Passcode.isValidPasscode("github passcode1")); // with letters and numerals

    }
}
