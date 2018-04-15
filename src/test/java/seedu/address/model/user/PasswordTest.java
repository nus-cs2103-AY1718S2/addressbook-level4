package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Pearlissa
public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidPass = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidPass));
    }

    @Test
    public void isValidPassword() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid password
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("^")); // only non-alphanumeric characters
        assertFalse(Password.isValidPassword("john*")); // contains non-alphanumeric characters
        assertFalse(Password.isValidPassword("john doe")); // contains whitespace
        assertFalse(Password.isValidPassword("abcdefg")); // contains less than 7 characters
        assertFalse(Password.isValidPassword("abcde12345abcde12345abcde12345a")); // contains more than 30 characters
        assertFalse(Password.isValidPassword("john_doe")); // contains underscore

        // valid password
        assertTrue(Password.isValidPassword("johndoe1")); // alphabets only
        assertTrue(Password.isValidPassword("12345678")); // numbers only
        assertTrue(Password.isValidPassword("JohnDoe1")); // with capital letters
        assertTrue(Password.isValidPassword("abcdefgh")); // at least 8 characters
        assertTrue(Password.isValidPassword("abcde12345abcde12345abcde12345")); // at most 30 characters
    }
}
