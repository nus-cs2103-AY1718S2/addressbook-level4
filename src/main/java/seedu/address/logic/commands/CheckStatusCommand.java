//@@author zacci
package seedu.address.logic.commands;

/**
 * Displays the status of the current session
 */
public class CheckStatusCommand extends Command {

    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USER_NOT_LOGGED_IN = "You are not currently logged in";

    /**
     * Checks the status of current session
     * @return details of the status
     */
    public CommandResult execute() {
        if (!model.checkIsLoggedIn()) {
            return new CommandResult(MESSAGE_USER_NOT_LOGGED_IN);
        }
        String details = (model.getSessionDetails());
        return new CommandResult(details);
    }

}
