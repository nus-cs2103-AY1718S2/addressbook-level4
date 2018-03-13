package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

public class LoginCommand extends Command{
    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_LOGIN_SUCCESS = "Successfully logged in!";
    public static final String MESSAGE_LOGIN_FAIL = "Wrong username and password! Please try again.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows user to login to the system "
            + "given the correct username and password.\n"
            + "Parameters: USERNAME PASSWORD\n"
            + "Example: " + COMMAND_WORD + "alice password123";

    public static final String TEST_USERNAME = "alice";
    public static final String TEST_PASSWORD = "password123";

    private final String username;
    private final String password;

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException{
        throw new CommandException("Username: " + username + ", Password: " + password);
    }
}
