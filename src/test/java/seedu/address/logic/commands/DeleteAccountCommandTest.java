package seedu.address.logic.commands;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;

import org.junit.Test;

import com.sun.org.apache.xpath.internal.operations.Mod;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountListUAL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.DeleteAccountCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.account.Account;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.testutil.AccountBuilder;

public class DeleteAccountCommandTest {

    @Test
    public void execute_validUsername_success() throws Exception {
        Model model = new ModelManager();
//        model = getTypicalAccountList();
//        String validUsername = "harry123";
//        assertCommandSuccess(prepareCommand(validUsername, model), model,
//                DeleteAccountCommand.MESSAGE_DELETE_ACCOUNT_SUCCESS, model);
        UniqueAccountList uniqueAccountList = new UniqueAccountList();
        uniqueAccountList = getTypicalAccountListUAL();
        Account validAccount = new AccountBuilder().build();
        model.addAccount(validAccount);
        assertCommandSuccess(prepareCommand(validAccount.getCredential().getUsername().toString(), model), model,
                DeleteAccountCommand.MESSAGE_DELETE_ACCOUNT_SUCCESS, model);
    }

    @Test
    public void execute_invalidUserName5character() {
        Model model = new ModelManager();
        model = getTypicalAccountList();
        String unvalidUsername = "ab12";
        assertCommandFailure(prepareCommand(unvalidUsername, model), model, String.format(MESSAGE_INVALID_COMMAND_FORMAT
                , DeleteAccountCommand.MESSAGE_USAGE));

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