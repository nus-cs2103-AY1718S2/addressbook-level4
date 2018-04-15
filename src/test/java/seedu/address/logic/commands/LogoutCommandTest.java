//@@author zacci
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalUsers.PRISONGUARD;
import static seedu.address.testutil.TypicalUsers.getTypicalUserDatabase;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LogoutCommandTest {

    private Model model = new ModelManager(getTypicalUserDatabase(), new UserPrefs());

    public LogoutCommandTest() {}

    @Test
    public void execute_validSessionOngoing_success() {

        ModelManager tempModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        tempModel.attemptLogin(PRISONGUARD.getUsername(), PRISONGUARD.getPassword());
        LogoutCommand logoutCommand = prepareCommand(tempModel);

        String expectedMessage = LogoutCommand.MESSAGE_SUCCESS;
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(logoutCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_userNotLoggedIn_failure() {

        LogoutCommand logoutCommand = prepareCommand();

        assertCommandFailure(logoutCommand, model, LogoutCommand.MESSAGE_USER_NOT_LOGGED_IN);
    }

    private LogoutCommand prepareCommand() {
        LogoutCommand logoutCommand = new LogoutCommand();
        logoutCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return logoutCommand;
    }

    private LogoutCommand prepareCommand(Model tempModel) {
        LogoutCommand logoutCommand = new LogoutCommand();
        logoutCommand.setData(tempModel, new CommandHistory(), new UndoRedoStack());
        return logoutCommand;
    }
}
