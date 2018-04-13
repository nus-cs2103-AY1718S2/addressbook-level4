//@@author cxingkai
package seedu.address.logic.commands;

import seedu.address.logic.login.LoginManager;

/**
 * Logs user out of the system
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String SUCCESS_MESSAGE = "Successfully logged out of IMDB.";

    @Override
    public CommandResult execute() {
        LoginManager.logout();
        return new CommandResult(SUCCESS_MESSAGE);
    }

    @Override
    public boolean equals (Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogoutCommand); // instanceof handles nulls
    }
}
