package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalUsers.ALICE;
import static seedu.address.testutil.TypicalUsers.JOHNDOE;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.LoginManagerBuilder;

public class LoginManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        LoginManager loginManager = new LoginManagerBuilder().withUser(ALICE).withUser(JOHNDOE).build();
        LoginManager differentLoginManager = new LoginManager();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        LoginManager LoginManager = new LoginManager();
        LoginManager LoginManagerCopy = new LoginManager();
        assertTrue(LoginManager.equals(LoginManagerCopy));

        // same object -> returns true
        assertTrue(LoginManager.equals(LoginManager));

        // null -> returns false
        assertFalse(LoginManager.equals(null));

        // different types -> returns false
        assertFalse(LoginManager.equals(5));
    }
}
