package seedu.progresschecker.model.credentials;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.progresschecker.testutil.Assert;

//@@author adityaa1998
public class RepositoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Repository(null));
    }

    @Test
    public void constructor_invalidRepository_throwsIllegalArgumentException() {
        String invalidRepo = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Repository(invalidRepo));
    }

    @Test
    public void isValidRepository() {
        // null repo
        Assert.assertThrows(NullPointerException.class, () -> Repository.isValidRepository(null));

        // invalid repo
        assertFalse(Repository.isValidRepository("")); // empty string
        assertFalse(Repository.isValidRepository(" ")); // spaces only
        assertFalse(Repository.isValidRepository("^")); // only non-alphanumeric characters
        assertFalse(Repository.isValidRepository("ca jacxvccxk")); // alphabets only with spaces
        assertFalse(Repository.isValidRepository("adityaa the 2nd")); // alphanumeric characters with spaces


        // valid repo
        assertTrue(Repository.isValidRepository("12345")); // numbers only
        assertTrue(Repository.isValidRepository("github/repo-4")); // with capital letters
        assertTrue(Repository.isValidRepository("git*")); // contains non-alphanumeric characters

    }
}
