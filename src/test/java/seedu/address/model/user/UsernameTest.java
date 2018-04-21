package seedu.address.model.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author Pearlissa
public class UsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidUser = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Username(invalidUser));
    }

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid username
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("^")); // only non-alphanumeric characters
        assertFalse(Username.isValidUsername("john*")); // contains non-alphanumeric characters
        assertFalse(Username.isValidUsername("john doe")); // contains whitespace
        assertFalse(Username.isValidUsername("ab")); // contains less than 3 characters
        assertFalse(Username.isValidUsername("abcdefghijklmnop")); // contains more than 15 characters
        assertFalse(Username.isValidUsername("john_doe")); // contains underscore

        // valid username
        assertTrue(Username.isValidUsername("johndoe")); // alphabets only
        assertTrue(Username.isValidUsername("12345")); // numbers only
        assertTrue(Username.isValidUsername("JohnDoe")); // with capital letters
        assertTrue(Username.isValidUsername("abc")); // at least 3 characters
        assertTrue(Username.isValidUsername("abcde12345abcde")); // at most 3 characters
    }
}
