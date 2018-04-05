package seedu.progresschecker.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.progresschecker.testutil.Assert;

//@@author EdwardKSG
public class GithubUsernameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new GithubUsername(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new GithubUsername(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> GithubUsername.isValidUsername(null));

        // invalid username
        assertFalse(GithubUsername.isValidUsername("")); // empty string
        assertFalse(GithubUsername.isValidUsername(" ")); // spaces only
        assertFalse(GithubUsername.isValidUsername("^")); // only non-alphanumeric characters
        assertFalse(GithubUsername.isValidUsername("peter*")); // contains non-alphanumeric characters

        // valid username
        assertTrue(GithubUsername.isValidUsername("peter jack")); // alphabets only
        assertTrue(GithubUsername.isValidUsername("12345")); // numbers only
        assertTrue(GithubUsername.isValidUsername("peter the 2nd")); // alphanumeric characters
        assertTrue(GithubUsername.isValidUsername("Capital Tan")); // with capital letters
        assertTrue(GithubUsername.isValidUsername("David Roger Jackson Ray Jr 2nd")); // long usernames
    }
}
