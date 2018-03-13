package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class LoginCommand extends Command{
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows user to login to the system "
            + "given the correct username and password.\n"
            + "Parameters: USERNAME PASSWORD\n"
            + "Example: " + COMMAND_WORD + "alice password123";

    public static final String MESSAGE_LOGIN_SUCCESS = "Successfully logged in!";
    public static final String MESSAGE_LOGIN_FAIL = "Wrong username and password! Please try again.";

    @Override
    public CommandResult execute() throws CommandException{
        throw new CommandException("Not yet implemented");
    }
}
