package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
//import seedu.address.model.login.exceptions.AlreadyLoggedOutException;

import static java.util.Objects.requireNonNull;

public class LogoutCommand extends Command {


    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_LOGOUT_SUCCESS = "You have logged out.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        model.setLoginStatus(false);
        return new CommandResult(MESSAGE_LOGOUT_SUCCESS);
    }

}
