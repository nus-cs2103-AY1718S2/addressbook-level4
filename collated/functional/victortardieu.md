# victortardieu
###### \java\seedu\address\logic\commands\ClearAccountCommand.java
``` java
/**
 * Checks if user is a librarian. If yes, it clears the list of accounts and logs out the current account.
 */
public class ClearAccountCommand extends UndoableCommand {

    /**
     *
     */
    public static final String COMMAND_WORD = "cleara";
    public static final String MESSAGE_SUCCESS = "AccountList has been cleared, and you are logged out!";
    public static final PrivilegeLevel PRIVILEGE_LEVEL = Model.PRIVILEGE_LEVEL_LIBRARIAN;

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        UniqueAccountList blankList = new UniqueAccountList();
        try {
            blankList.add(Account.createDefaultAdminAccount());
        } catch (DuplicateAccountException e) {
            e.printStackTrace();
        }
        model.resetAccount(blankList);
        model.logout();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public PrivilegeLevel getPrivilegeLevel() {

        return PRIVILEGE_LEVEL;
    }
}
```
