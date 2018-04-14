package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LoginManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LoginManager loginManager = new LoginManager();

    @Test
    public void equals() {

        // same values -> returns true
        LoginManager LoginManager = new LoginManager();

        // same object -> returns true
        assertTrue(LoginManager.equals(LoginManager));

        // null -> returns false
        assertFalse(LoginManager.equals(null));

        // different types -> returns false
        assertFalse(LoginManager.equals(5));
    }
}
