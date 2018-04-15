//@@author chantiongley
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.account.Account;
import seedu.address.model.account.PrivilegeLevel;
import seedu.address.model.account.Username;
import seedu.address.model.account.exceptions.AccountNotFoundException;

/**
 * Deletes a account using it's last displayed index from the account list
 */
public class DeleteAccountCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteAccount";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the account identified by the name of the user stored in account list.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " John";
    public static final String MESSAGE_DELETE_ACCOUNT_SUCCESS = "Deleted Account: %1$s";

    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    private String username;
    private Account accountToDelete;

    public DeleteAccountCommand(String username) {
        requireNonNull(username);
        this.username = username;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.deleteAccount(accountToDelete);
        } catch (AccountNotFoundException anfe) {
            throw new CommandException("Account does not exist");
        }

        return new CommandResult(String.format(MESSAGE_DELETE_ACCOUNT_SUCCESS, accountToDelete));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        accountToDelete = model.getAccountList().searchByUsername(new Username(username));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteAccountCommand // instanceof handles nulls
            && accountToDelete.equals(((DeleteAccountCommand) other).accountToDelete));
    }

    @Override
    public PrivilegeLevel getPrivilegeLevel() {
        return PRIVILEGE_LEVEL;
    }
}

