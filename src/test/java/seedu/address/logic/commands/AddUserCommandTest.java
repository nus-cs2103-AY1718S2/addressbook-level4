//@@author zacci
package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddUserCommand.MESSAGE_ALREADY_EXISTING_USER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalUsers.PRISONGUARD;
import static seedu.address.testutil.TypicalUsers.VALID_MINIMUM_SECURITY_LEVEL;
import static seedu.address.testutil.TypicalUsers.VALID_PASSWORD;
import static seedu.address.testutil.TypicalUsers.VALID_USERNAME;
import static seedu.address.testutil.TypicalUsers.getTypicalUserDatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.User;

public class AddUserCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalUserDatabase(), new UserPrefs());

    public AddUserCommandTest() {}

    @Test
    public void execute_validUsernameValidPasswordValidSecurityLevel_success() throws Exception {
        AddUserCommand addUserCommand = prepareCommand(VALID_USERNAME, VALID_PASSWORD,
                VALID_MINIMUM_SECURITY_LEVEL);

        String expectedMessage = String.format(AddUserCommand.MESSAGE_ADD_USER_SUCCESS, VALID_USERNAME);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        User userToAdd = new User(VALID_USERNAME, VALID_PASSWORD, VALID_MINIMUM_SECURITY_LEVEL);
        expectedModel.addUser(userToAdd);

        assertCommandSuccess(addUserCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_userAlreadyExists_failure() {
        AddUserCommand addUserCommand = prepareCommand(PRISONGUARD.getUsername(), PRISONGUARD.getPassword(),
                PRISONGUARD.getSecurityLevel());

        assertCommandFailure(addUserCommand, model,
                String.format(MESSAGE_ALREADY_EXISTING_USER, PRISONGUARD.getUsername()));

    }

    private AddUserCommand prepareCommand(String username, String password, int securityLevel) {
        AddUserCommand addUserCommand = new AddUserCommand(username, password, securityLevel);
        addUserCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addUserCommand;
    }
}
