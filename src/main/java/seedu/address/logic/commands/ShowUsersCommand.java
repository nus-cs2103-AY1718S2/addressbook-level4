//@@zacci
package seedu.address.logic.commands;

/**
 * Shows all cells and number of prisoners in each of them to the user.
 */
public class ShowUsersCommand extends Command {

    public static final String COMMAND_WORD = "users";
    public static final int MIN_SECURITY_LEVEL = 3;

    public static final String MESSAGE_SUCCESS = "%s\nShown users.";


    @Override
    public CommandResult execute() {
        String users = model.getAddressBook().getUserList().toString();
        return new CommandResult(String.format(MESSAGE_SUCCESS, users));
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

}
