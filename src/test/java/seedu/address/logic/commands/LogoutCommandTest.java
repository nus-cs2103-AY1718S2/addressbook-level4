//@@author cxingkai
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.login.LoginManager;

public class LogoutCommandTest {

    @Test
    public void execute_alreadyLoggedIn_successfulLogout() {
        LoginManager.authenticate("alice", "password123");

        LogoutCommand logoutCommand = new LogoutCommand();
        logoutCommand.execute();

        assertEquals(LoginManager.getUserState(), LoginManager.NO_USER_STATE);
    }

    @Test
    public void equals() {
        LogoutCommand logoutCommand = new LogoutCommand();
        LogoutCommand logoutCommandCopy = new LogoutCommand();

        assertTrue(logoutCommand.equals(logoutCommandCopy));
    }

}
