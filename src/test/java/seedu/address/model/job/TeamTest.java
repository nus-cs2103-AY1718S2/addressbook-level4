// @@author kush1509
package seedu.address.model.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TeamTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Team(null));
    }

    @Test
    public void constructor_invalidTeam_throwsIllegalArgumentException() {
        String invalidTeam = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Team(invalidTeam));
    }

    @Test
    public void isValidTeam() {
        // null team
        Assert.assertThrows(NullPointerException.class, () -> Team.isValidTeam(null));

        // invalid team
        assertFalse(Team.isValidTeam("")); // empty string
        assertFalse(Team.isValidTeam(" ")); // spaces only
        assertFalse(Team.isValidTeam("^")); // only non-alphanumeric characters
        assertFalse(Team.isValidTeam("management*")); // contains non-alphanumeric characters

        // valid team
        assertTrue(Team.isValidTeam("frontend")); // alphabets only
        assertTrue(Team.isValidTeam("12345")); // numbers only
        assertTrue(Team.isValidTeam("2nd development")); // alphanumeric characters
        assertTrue(Team.isValidTeam("Cloud Services")); // with capital letters
        assertTrue(Team.isValidTeam("Frontend Web Development")); // long teams
    }
}
