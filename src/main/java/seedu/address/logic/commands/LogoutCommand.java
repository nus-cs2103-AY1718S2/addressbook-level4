package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.OAuthManager;
import seedu.address.logic.commands.exceptions.CommandException;

//@@ author kaisertanqr
/**
 * Logs user out from application
 */
public class LogoutCommand extends Command {


    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_LOGOUT_SUCCESS = "You have logged out.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        model.setLoginStatus(false);
        //@@author ifalluphill
        OAuthManager.clearCachedCalendarData();
        //@@ author kaisertanqr
        return new CommandResult(MESSAGE_LOGOUT_SUCCESS);
    }

}
