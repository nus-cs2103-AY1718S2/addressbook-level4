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

import seedu.address.logic.login.LoginManager;
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

        LoginCommand commandSuccess = new LoginCommand();
        assertCommandSuccess(commandSuccess, model, "", expectedModel);
    }

    @Test
    public void equals() {
        LoginCommand loginCommand = new LoginCommand();
        LoginCommand loginCommandCopy = new LoginCommand();

        assertTrue(loginCommand.equals(loginCommandCopy));
    }
}
