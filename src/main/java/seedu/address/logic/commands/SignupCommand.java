//@@author Jason1im
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.DuplicateUsernameException;

/**
 * Register a new account for the user.
 */
public class SignupCommand  extends Command {
    public static final String COMMAND_WORD = "signup";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_USERNAME + " "
            + PREFIX_PASSWORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Creates a new user account."
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "John "
            + PREFIX_PASSWORD + "353535 ";
    public static final String MESSAGE_SUCCESS = "You have signup successfully!";
    public static final String MESSAGE_FAILURE = "Signup has failed.\n";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username has already been used.";

    public final String username;
    public final String password;

    public SignupCommand(String inputUsername, String inputPassword) {
        requireNonNull(inputUsername);
        requireNonNull(inputPassword);
        this.username = inputUsername;
        this.password = inputPassword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.register(username, password);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DuplicateUsernameException due) {
            throw new CommandException(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE_USERNAME));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignupCommand // instanceof handles nulls
                && username.equals(((SignupCommand) other).username)
                && password.equals(((SignupCommand) other).password));
    }
}
