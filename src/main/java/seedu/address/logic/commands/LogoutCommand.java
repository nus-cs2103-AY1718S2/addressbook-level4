//@@author Jason1im
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.UserLogoutException;

/**
* Logs the user out of contactHeRo.
*/
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "You have logged out successfully!";
    public static final String MESSAGE_MULTIPLE_LOGOUT = "You have already logged out.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.logout();
            EventsCenter.getInstance().post(new LoginEvent(false));
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UserLogoutException ule) {
            throw new CommandException(MESSAGE_MULTIPLE_LOGOUT);
        }
    }
}
