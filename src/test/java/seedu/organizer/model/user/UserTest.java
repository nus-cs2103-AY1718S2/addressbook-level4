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
    public void isValidUsername_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> User.isValidUsername(null));
    }

    @Test
    public void isValidPassword_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> User.isValidPassword(null));
    }

    @Test
    public void usernameMatches_null_throwsNullPointerException() {
        User user = new User("match1", "match1");
        Assert.assertThrows(NullPointerException.class, () -> User.usernameMatches(user, null));
        Assert.assertThrows(NullPointerException.class, () -> User.usernameMatches(null, user));
        Assert.assertThrows(NullPointerException.class, () -> User.usernameMatches(null, null));
    }

    @Test
    public void passwordMatches_null_throwsNullPointerException() {
        User user = new User("match1", "match1");
        Assert.assertThrows(NullPointerException.class, () -> User.passwordMatches(user, null));
        Assert.assertThrows(NullPointerException.class, () -> User.passwordMatches(null, user));
        Assert.assertThrows(NullPointerException.class, () -> User.passwordMatches(null, null));
    }
}
