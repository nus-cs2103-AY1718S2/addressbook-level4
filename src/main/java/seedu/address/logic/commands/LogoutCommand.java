package seedu.address.logic.commands;

import seedu.address.external.exceptions.CredentialsException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Displays the user's schedule.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": logs out of your Google account";

    public static final String MESSAGE_SUCCESS = "Google account logged out";

    public LogoutCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.logoutGoogleAccount();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (CredentialsException ce) {
            throw new CommandException(ce.getMessage());
        }
    }
}
