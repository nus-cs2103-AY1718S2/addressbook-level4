//@@author zacci
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.UserAlreadyExistsException;

/**
 * Adds a new user to the PrisonBook
 */
public class AddUserCommand extends Command {
    public static final String COMMAND_WORD = "adduser";
    public static final String COMMAND_ALIAS = "au";
    public static final int MIN_SECURITY_LEVEL = 3;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new user to the PrisonBook.\n"
            + "Parameters: user/NEW_USERNAME pw/NEW_PASSWORD sl/SECURITY_LEVEL (integer from 0 to 3)...\n"
            + "Example: " + COMMAND_WORD + " user/newuser1 pw/password1 sl/2";

    public static final String MESSAGE_ADD_USER_SUCCESS = "New user %s added to PrisonBook";
    public static final String MESSAGE_ALREADY_EXISTING_USER = "%s is already a user in PrisonBook";
    private final String username;

    private User userToAdd;

    /**
     * @param username of the new user to be added to the PrisonBook
     * @param password of the new user to be added to the PrisonBook
     * @param securityLevel of the new user to be added to the PrisonBook
     */
    public AddUserCommand(String username, String password, int securityLevel) {
        requireNonNull(username);
        requireNonNull(password);
        this.username = username;
        userToAdd = new User(username, password, securityLevel);
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireNonNull(userToAdd);
        try {
            model.addUser(userToAdd);
        } catch (UserAlreadyExistsException uaee) {
            throw new CommandException(String.format(MESSAGE_ALREADY_EXISTING_USER, username));
        }
        return new CommandResult(String.format(MESSAGE_ADD_USER_SUCCESS, username));
    }

    public User getUserToAdd() {
        return userToAdd;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddUserCommand // instanceof handles nulls
                && Objects.equals(this.userToAdd, ((AddUserCommand) other).userToAdd));
    }
}
