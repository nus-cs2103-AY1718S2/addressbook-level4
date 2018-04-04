package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.login.exceptions.UserNotFoundException;

/**
 * Deletes an existing user from the user database.
 */
public class DeleteUserCommand extends Command {

    public static final String COMMAND_WORD = "delete-user";

    public static final String MESSAGE_USAGE = "Delete the user with the parameters: u/USERNAME p/PASSWORD"
            + "\nEXAMPLE: delete-user u/user p/123456"
            + "\nNote: You must be logged out before executing this command and username and password must be valid";

    public static final String MESSAGE_SUCCESS = "User successfully deleted: %1$s";
    public static final String MESSAGE_DELETE_FAILURE = "Delete failed. " + "Username or password is incorrect.";
    public static final String MESSAGE_NOT_LOGGED_OUT = "You are not logged out. Please logout to execute this command";

    private final Username username;
    private final Password password;

    public DeleteUserCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            if (model.checkCredentials(this.username, this.password) && !model.hasLoggedIn()) {
                User toDelete = new User(username, password);
                model.deleteUser(toDelete);
                return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete.getUsername().toString()));
            } else {
                return new CommandResult(MESSAGE_DELETE_FAILURE);
            }
        } catch (AlreadyLoggedInException e) {
            throw new CommandException(MESSAGE_NOT_LOGGED_OUT);
        } catch (UserNotFoundException pnfe) {
            throw new CommandException(MESSAGE_DELETE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteUserCommand // instanceof handles nulls
                && this.username.equals(((DeleteUserCommand) other).username)
                && this.password.equals(((DeleteUserCommand) other).password)); // state check
    }
}
