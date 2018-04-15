package seedu.address.logic;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.GoogleAuthenticationException;

//@@author KevinCJH

public class GoogleAuthenticationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GoogleAuthentication googleAuthentication = new GoogleAuthentication();

    /**
     * Checks if Login url generated is valid
     */
    @Test
    public void execute_authenticate_url() {
        assertTrue(googleAuthentication.getAuthenticationUrl().contains(
                "https://accounts.google.com/o/oauth2/auth?client_id"));
    }

    /**
     * Exception should be thrown as no token is generated.
     */
    @Test
    public void execute_invalidToken() throws Exception {
        thrown.expect(GoogleAuthenticationException.class);
        googleAuthentication.getToken();
    }

}
