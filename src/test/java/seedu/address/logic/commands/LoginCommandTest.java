//@@author cxingkai
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoginCommandTest {

    @Test
    public void equals() {
        LoginCommand loginCommand = new LoginCommand();
        LoginCommand loginCommandCopy = new LoginCommand();

        assertTrue(loginCommand.equals(loginCommandCopy));
    }
}
