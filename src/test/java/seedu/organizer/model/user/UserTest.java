package seedu.organizer.model.user;

import org.junit.Test;

import seedu.organizer.testutil.Assert;

//@@author dominickenn
public class UserTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new User(null, null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        String validPassword = "validPass";
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(invalidUsername, validPassword));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String validUsername = "validUsername";
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(validUsername, invalidPassword));
    }

    @Test
    public void isValidUsername() {
        Assert.assertThrows(NullPointerException.class, () -> User.isValidUsername(null));
    }

    @Test
    public void isValidPassword() {
        Assert.assertThrows(NullPointerException.class, () -> User.isValidPassword(null));
    }

}
