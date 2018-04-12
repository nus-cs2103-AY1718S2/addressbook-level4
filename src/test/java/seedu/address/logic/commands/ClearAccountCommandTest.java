package seedu.address.logic.commands;

import static org.junit.Assert.*;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAccounts.getTypicalAccountList;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.model.account.UniqueAccountList;
import seedu.address.model.account.exceptions.DuplicateAccountException;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

public class ClearAccountCommandTest {

    @Test
    public void execute_empty_accountList() throws DuplicateAccountException {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearAccountCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAccountList_success() throws DuplicateAccountException {
        Model model = new ModelManager();
        model = getTypicalAccountList();
        assertCommandSuccess(prepareCommand(model), model, ClearAccountCommand.MESSAGE_SUCCESS,
                model);
    }

    private ClearAccountCommand prepareCommand(Model model) throws DuplicateAccountException {
        ClearAccountCommand command = new ClearAccountCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
