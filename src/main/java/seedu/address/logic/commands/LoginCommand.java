package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;

//@@author kaisertanqr
/**
 * Authenticates login credentials.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = "Login with the parameters: u/USERNAME p/PASSWORD"
            + "\nEXAMPLE: login u/user p/123456";

    public static final String MESSAGE_LOGIN_SUCCESS = "Login successful!";
    public static final String MESSAGE_LOGIN_FAILURE = "Username or password is incorrect. Please login again.";
    public static final String MESSAGE_LOGIN_ALREADY = "You have already logged in.";

    private final Username username;
    private final Password password;

    public LoginCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            if(model.checkLoginCredentials(this.username, this.password)) {
                return new CommandResult(MESSAGE_LOGIN_SUCCESS);
            } else {
                return new CommandResult(MESSAGE_LOGIN_FAILURE);
            }
        } catch (AlreadyLoggedInException e) {
            throw new CommandException(MESSAGE_LOGIN_ALREADY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && this.username.equals(((LoginCommand) other).username)
                && this.password.equals(((LoginCommand) other).password)); // state check
    }
}
