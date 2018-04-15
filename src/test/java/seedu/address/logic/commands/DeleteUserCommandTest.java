//@@author zacci
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalUsers.PRISONLEADER;
import static seedu.address.testutil.TypicalUsers.PRISONWARDEN;
import static seedu.address.testutil.TypicalUsers.VALID_USERNAME;
import static seedu.address.testutil.TypicalUsers.getTypicalUserDatabase;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.user.exceptions.CannotDeleteSelfException;
import seedu.address.model.user.exceptions.NotEnoughAuthorityToDeleteException;
import seedu.address.model.user.exceptions.UserDoesNotExistException;

public class DeleteUserCommandTest {

    private Model model = new ModelManager(getTypicalUserDatabase(), new UserPrefs());

    public DeleteUserCommandTest() {}

    @Test
    public void execute_deleteUserOfHigherSecurityLevel_failure() {

        ModelManager tempModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        tempModel.attemptLogin(PRISONLEADER.getUsername(), PRISONLEADER.getPassword());
        DeleteUserCommand deleteUserCommand = prepareCommand(PRISONWARDEN.getUsername(), tempModel);

        assertCommandFailure(deleteUserCommand, model, NotEnoughAuthorityToDeleteException.MESSAGE);
    }

    @Test
    public void execute_deleteSelf_failure() {

        ModelManager tempModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        tempModel.attemptLogin(PRISONLEADER.getUsername(), PRISONLEADER.getPassword());
        DeleteUserCommand deleteUserCommand = prepareCommand(PRISONLEADER.getUsername(), tempModel);

        assertCommandFailure(deleteUserCommand, model, CannotDeleteSelfException.MESSAGE);
    }

    @Test
    public void execute_deleteNonExistentUser_failure() {

        ModelManager tempModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        tempModel.attemptLogin(PRISONLEADER.getUsername(), PRISONLEADER.getPassword());
        DeleteUserCommand deleteUserCommand = prepareCommand(VALID_USERNAME, tempModel);

        assertCommandFailure(deleteUserCommand, model, UserDoesNotExistException.MESSAGE);
    }

    private DeleteUserCommand prepareCommand(String username) {
        DeleteUserCommand deleteUserCommand = new DeleteUserCommand(username);
        deleteUserCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteUserCommand;
    }

    private DeleteUserCommand prepareCommand(String username, Model tempModel) {
        DeleteUserCommand deleteUserCommand = new DeleteUserCommand(username);
        deleteUserCommand.setData(tempModel, new CommandHistory(), new UndoRedoStack());
        return deleteUserCommand;
    }
}
