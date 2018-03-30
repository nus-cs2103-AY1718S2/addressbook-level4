package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.exceptions.CommandException;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_throwsCommandException() throws Exception {
        LoginCommand command = new LoginCommand(VALID_USERNAME_AMY, VALID_PASSWORD_AMY);

        thrown.expect(CommandException.class);
        thrown.expectMessage("Test");

        command.execute();
    }

    @Test
    public void equals() {
        LoginCommand standardCommand = new LoginCommand(VALID_USERNAME_AMY, VALID_PASSWORD_AMY);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same values -> returns true
        LoginCommand commandWithSameValues = new LoginCommand(VALID_USERNAME_AMY, VALID_PASSWORD_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different values -> returns false
        LoginCommand commandWithDiffUsername = new LoginCommand(VALID_USERNAME_BOB, VALID_PASSWORD_AMY);
        LoginCommand commandWithDiffPassword = new LoginCommand(VALID_USERNAME_AMY, VALID_PASSWORD_BOB);
        assertFalse(standardCommand.equals(commandWithDiffUsername));
        assertFalse(standardCommand.equals(commandWithDiffPassword));
    }
}
