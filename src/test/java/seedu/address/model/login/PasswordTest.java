package seedu.address.model.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author kaisertanqr
public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidPassword));
    }

    @Test
    public void isValidUsername() {
        // null address
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid usernames
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only
        assertFalse(Password.isValidPassword("secret pass")); // contains spaces
        assertFalse(Password.isValidPassword("PassWord@@")); // non-alphanumeric character

        // valid usernames
        assertTrue(Password.isValidPassword("password123"));
    }
}
