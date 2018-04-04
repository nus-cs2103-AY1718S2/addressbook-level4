//@@author nicholasangcx
package seedu.recipe.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccessTokenCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String INVALID_AUTHORIZATION_CODE = "wrong_format";

    @Test
    public void equals() {
        String firstTest = "firstCode";
        String secondTest = "secondCode";

        AccessTokenCommand accessTokenFirstCommand = new AccessTokenCommand(firstTest);
        AccessTokenCommand accessTokenSecondCommand = new AccessTokenCommand(secondTest);

        // same object -> returns true
        assertTrue(accessTokenFirstCommand.equals(accessTokenFirstCommand));

        // same values -> returns true
        AccessTokenCommand accessTokenFirstCommandCopy = new AccessTokenCommand(firstTest);
        assertTrue(accessTokenFirstCommandCopy.equals(accessTokenFirstCommand));

        // different types -> returns false
        assertFalse(accessTokenFirstCommand.equals(1));
        assertFalse(accessTokenFirstCommand.equals(new HelpCommand()));
        assertFalse(accessTokenFirstCommand.equals("anything"));

        // null -> returns false
        assertFalse(accessTokenFirstCommand.equals(null));

        // different recipe -> returns false
        assertFalse(accessTokenFirstCommand.equals(accessTokenSecondCommand));
    }

    @Test
    public void execute_invalidAuthorizationCode() {
        thrown.expect(AssertionError.class);
        AccessTokenCommand command = new AccessTokenCommand(INVALID_AUTHORIZATION_CODE);
        command.execute();
    }
}
//@@author
