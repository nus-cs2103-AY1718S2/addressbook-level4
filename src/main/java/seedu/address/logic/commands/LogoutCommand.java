package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exceptions.UserLogoutException;

/**
 * Logs the user out of contactHeRo.
 */
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "You have logout successfully!";
    public static final String MESSAGE_MULTIPLE_LOGOUT = "You have already logout.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.logout();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UserLogoutException ule) {
            throw new CommandException(MESSAGE_MULTIPLE_LOGOUT);
        }
    }
}
