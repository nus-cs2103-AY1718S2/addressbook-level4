package seedu.progresschecker.model.credentials;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.progresschecker.testutil.Assert;

//@@author adityaa1998
public class UsernameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null repo
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid repo
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("^")); // only non-alphanumeric characters
        assertFalse(Username.isValidUsername("ca jacxvccxk")); // alphabets only with spaces
        assertFalse(Username.isValidUsername("git hub1212#")); // alphanumeric characters with spaces


        // valid repo
        assertTrue(Username.isValidUsername("12345")); // numbers only
        assertTrue(Username.isValidUsername("github-repo-4")); // with capital letters
        assertTrue(Username.isValidUsername("git_hub")); // contains non-alphanumeric characters

    }
}
