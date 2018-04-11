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
        UniqueAccountList uniqueAccountList = new UniqueAccountList();
        assertCommandSuccess(prepareCommand(uniqueAccountList), uniqueAccountList, ClearAccountCommand.MESSAGE_SUCCESS,
                uniqueAccountList);
    }

    @Test
    public void execute_nonEmptyAccountList_success() throws DuplicateAccountException {
        UniqueAccountList uniqueAccountList = new UniqueAccountList();
        uniqueAccountList = getTypicalAccountList();
        assertCommandSuccess(prepareCommand(uniqueAccountList), uniqueAccountList, ClearAccountCommand.MESSAGE_SUCCESS, uniqueAccountList);
    }

    private ClearAccountCommand prepareCommand (UniqueAccountList uniqueAccountList) throws DuplicateAccountException {
        ClearAccountCommand command = new ClearAccountCommand();
        command.setData(uniqueAccountList, new CommandHistory(), new UndoRedoStack());
        uniqueAccountList.add(Account.createDefaultAdminAccount());
        return command;
    }
}