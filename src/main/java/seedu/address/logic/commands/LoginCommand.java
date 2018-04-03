package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.external.exceptions.CredentialsException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;

/**
 * Displays the user's schedule.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": login to your Google account";

    public static final String MESSAGE_SUCCESS = "Google account logged in";

    public LoginCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.loginGoogleAccount();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (CredentialsException ce) {
            throw new CommandException(ce.getMessage());
        }
    }
}
