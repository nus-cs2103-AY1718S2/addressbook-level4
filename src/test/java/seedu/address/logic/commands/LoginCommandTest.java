package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.LoginCommand.FAKE_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LOGIN_FAIL;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LOGIN_SUCCESS;
import static seedu.address.logic.commands.LoginCommand.TEST_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.TEST_USERNAME;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.LoginManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LoginCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_success_displaysSuccessMessage() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getImdb(), new UserPrefs());
        LoginManager.logout();

        LoginCommand commandSuccess = new LoginCommand(TEST_USERNAME, TEST_PASSWORD);
        assertCommandSuccess(commandSuccess, model, MESSAGE_LOGIN_SUCCESS + TEST_USERNAME, expectedModel);
    }

    @Test
    public void execute_fail_displaysFailMessage() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_LOGIN_FAIL);

        LoginCommand expectFail = new LoginCommand(TEST_USERNAME, FAKE_PASSWORD);
        expectFail.execute();
    }

    @Test
    public void equals() {
        LoginCommand aliceCorrectLogin = new LoginCommand("alice", "password123");
        LoginCommand aliceWrongLogin = new LoginCommand("alice", "password456");
        LoginCommand bobLogin = new LoginCommand("bob", "password123");

        // same object -> returns true
        assertTrue(aliceCorrectLogin.equals(aliceCorrectLogin));

        // same values -> returns true
        LoginCommand aliceCorrectLoginCopy = new LoginCommand("alice", "password123");
        assertTrue(aliceCorrectLogin.equals(aliceCorrectLoginCopy));

        // different types -> returns false
        assertFalse(aliceCorrectLogin.equals(1));

        // null -> returns false
        assertFalse(aliceCorrectLogin.equals(null));

        // different values -> returns false
        assertFalse(aliceCorrectLogin.equals(aliceWrongLogin));
        assertFalse(aliceCorrectLogin.equals(bobLogin));
    }
}
