package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.login.exceptions.UserNotFoundException;

/**
 * Change the password of an existing user in the user database.
 */
public class ChangeUserPasswordCommand extends Command {
    public static final String COMMAND_WORD = "change-user-password";

    public static final String MESSAGE_USAGE = "Changes the user password."
            + "with the parameters: u/USERNAME p/PASSWORD n/NEWPASSWORD"
            + "\nEXAMPLE: change-user-password u/slap p/123456 n/";

    public static final String MESSAGE_SUCCESS = "Password updated!";
    public static final String MESSAGE_UPDATE_FAILURE = "Password update failed. Username or password is incorrect.";
    public static final String MESSAGE_NOT_LOGGED_OUT = "You are not logged out.Please logout to execute this command.";

    private final Username username;
    private final Password password;
    private final Password newPassword;

    public ChangeUserPasswordCommand(Username username, Password password, Password newPassword) {
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            if (model.checkCredentials(this.username, this.password)) {
                User target = new User(username, password);
                User userWithNewPassword = new User(username, newPassword);
                model.updateUserPassword(target, userWithNewPassword);
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                return new CommandResult(MESSAGE_UPDATE_FAILURE);
            }
        } catch (AlreadyLoggedInException e) {
            throw new CommandException(MESSAGE_NOT_LOGGED_OUT);
        } catch (UserNotFoundException pnfe) {
            throw new CommandException(MESSAGE_UPDATE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeUserPasswordCommand // instanceof handles nulls
                && this.username.equals(((ChangeUserPasswordCommand) other).username)
                && this.password.equals(((ChangeUserPasswordCommand) other).password))
                && this.newPassword.equals(((ChangeUserPasswordCommand) other).newPassword); // state check
    }
}
