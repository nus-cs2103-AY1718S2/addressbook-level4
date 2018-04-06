package seedu.address.model.team;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

//@@author jordancjq
public class TeamNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new TeamName(null));
    }

    @Test
    public void constructor_invalidTeamName_throwsIllegalArgumentException() {
        String invalidTeamName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new TeamName(invalidTeamName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> TeamName.isValidName(null));

        // invalid name
        assertFalse(TeamName.isValidName("")); // empty string
        assertFalse(TeamName.isValidName(" ")); // spaces only
        assertFalse(TeamName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(TeamName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(TeamName.isValidName("new jersey")); // alphabets only
        assertTrue(TeamName.isValidName("5566")); // numbers only
        assertTrue(TeamName.isValidName("5th cycle")); // alphanumeric characters
        assertTrue(TeamName.isValidName("Georgia Clint")); // with capital letters
        assertTrue(TeamName.isValidName("The Longest Football Team Name You Know")); // long names
    }
}
