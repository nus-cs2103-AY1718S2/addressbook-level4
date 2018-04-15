//@@author zacci
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalUsers.PRISONGUARD;
import static seedu.address.testutil.TypicalUsers.VALID_PASSWORD;
import static seedu.address.testutil.TypicalUsers.VALID_USERNAME;
import static seedu.address.testutil.TypicalUsers.getTypicalUserDatabase;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LoginCommandTest {

    private Model model = new ModelManager(getTypicalUserDatabase(), new UserPrefs());

    public LoginCommandTest() {}

    @Test
    public void execute_validUsernameValidPassword_success() {
        LoginCommand loginCommand = prepareCommand(PRISONGUARD.getUsername(), PRISONGUARD.getPassword());

        String expectedMessage = LoginCommand.MESSAGE_LOGIN_SUCCESS;
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.attemptLogin(PRISONGUARD.getUsername(), PRISONGUARD.getPassword());

        assertCommandSuccess(loginCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_userDoesNotExist_failure() {

        LoginCommand loginCommand = prepareCommand(VALID_USERNAME, VALID_PASSWORD);

        assertCommandFailure(loginCommand, model, LoginCommand.MESSAGE_LOGIN_FAILURE);
    }

    @Test
    public void execute_userAlreadyLoggedIn_failure() {

        ModelManager tempModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        tempModel.attemptLogin(PRISONGUARD.getUsername(), PRISONGUARD.getPassword());
        LoginCommand loginCommand = prepareCommand(VALID_USERNAME, VALID_PASSWORD, tempModel);

        assertCommandFailure(loginCommand, model, LoginCommand.MESSAGE_ALREADY_LOGGED_IN);
    }



    private LoginCommand prepareCommand(String username, String password) {
        LoginCommand loginCommand = new LoginCommand(username, password);
        loginCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return loginCommand;
    }

    private LoginCommand prepareCommand(String username, String password, Model tempModel) {
        LoginCommand loginCommand = new LoginCommand(username, password);
        loginCommand.setData(tempModel, new CommandHistory(), new UndoRedoStack());
        return loginCommand;
    }
}
