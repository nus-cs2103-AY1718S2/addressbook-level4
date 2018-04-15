package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author Pearlissa
public class LoginManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LoginManager loginManager = new LoginManager();

    @Test
    public void equals() {

        // same values -> returns true
        LoginManager loginManager = new LoginManager();

        // same object -> returns true
        assertTrue(loginManager.equals(loginManager));

        // null -> returns false
        assertFalse(loginManager.equals(null));

        // different types -> returns false
        assertFalse(loginManager.equals(5));
    }
}
