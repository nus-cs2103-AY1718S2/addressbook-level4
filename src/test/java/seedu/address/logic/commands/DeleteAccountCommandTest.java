package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.HARRY;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;
import static seedu.address.testutil.TypicalBooks.getTypicalCatalogue;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class DeleteAccountCommandTest {

    private Model model = new ModelManager(getTypicalCatalogue(), new UserPrefs());

    @Test
    public void execute_foundUsername_success() throws Exception {
        Model actualModel = new ModelManager();
        actualModel = getTypicalAccountList();
        Model expectedModel = new ModelManager();
        expectedModel = getTypicalAccountList();
        expectedModel.deleteAccount(HARRY);
        assertCommandSuccess(prepareCommand("harry123", actualModel), actualModel,
                String.format(DeleteAccountCommand.MESSAGE_DELETE_ACCOUNT_SUCCESS, HARRY), expectedModel);

    }

    @Test
    public void execute_usernameNotFound_failure()  throws Exception {
        Model model = new ModelManager();
        model = getTypicalAccountList();
        assertCommandFailure(prepareCommand("harry1234", model), model,
                "Account does not exist");
    }


    /**
     * Returns a {@code DeleteAccountCommand} with the parameter {@code username}.
     */
    private DeleteAccountCommand prepareCommand(String username, Model model) {
        DeleteAccountCommand deleteAccountCommand = new DeleteAccountCommand(username);
        deleteAccountCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteAccountCommand;
    }

}
