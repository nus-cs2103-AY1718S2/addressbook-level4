package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.login.exceptions.AuthenticationFailedException;

//@@author kaisertanqr
/**
 * Authenticates login credentials.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String LOGIN_SUCCESS = "Login successful!";
    public static final String LOGIN_FAILURE = "Username or password is incorrect. Please login again.";
    public static final String LOGIN_ALREADY = "You have already logged in.";

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
            model.checkLoginCredentials(this.username, this.password);
            return new CommandResult(LOGIN_SUCCESS);
        } catch (AlreadyLoggedInException e) {
            throw new CommandException(LOGIN_ALREADY);
        } catch (AuthenticationFailedException e) {
            throw new CommandException(LOGIN_FAILURE);
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
