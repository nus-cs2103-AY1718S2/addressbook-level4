package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.account.Account;
import seedu.address.testutil.AccountBuilder;

//@@author shadow2496
public class LoginCommandTest {

    @Test
    public void execute_login_success() {
        Account validAccount = new AccountBuilder().build();
        LoginCommand command = new LoginCommand(validAccount);

        String expectedMessage = String.format(LoginCommand.MESSAGE_SUCCESS, validAccount);

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void equals() {
        Account account = new AccountBuilder().withUsername(VALID_USERNAME_AMY)
                .withPassword(VALID_PASSWORD_AMY).build();
        Account accountWithDiffUsername = new AccountBuilder().withUsername(VALID_USERNAME_BOB)
                .withPassword(VALID_PASSWORD_AMY).build();
        Account accountWithDiffPassword = new AccountBuilder().withUsername(VALID_USERNAME_AMY)
                .withPassword(VALID_PASSWORD_BOB).build();
        LoginCommand standardCommand = new LoginCommand(account);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same values -> returns true
        LoginCommand commandWithSameValues = new LoginCommand(account);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different values -> returns false
        LoginCommand commandWithDiffUsername = new LoginCommand(accountWithDiffUsername);
        LoginCommand commandWithDiffPassword = new LoginCommand(accountWithDiffPassword);
        assertFalse(standardCommand.equals(commandWithDiffUsername));
        assertFalse(standardCommand.equals(commandWithDiffPassword));
    }
}
