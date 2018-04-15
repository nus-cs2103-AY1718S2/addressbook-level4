package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.BadDataException;
import seedu.address.model.exception.InvalidPasswordException;

/**
 * Change the password of the user account.
 */
public class UpdatePasswordCommand extends Command {
    public static final String COMMAND_WORD = "updatepassword";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_PASSWORD + " "
            + PREFIX_NEW_PASSWORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Updates the user account password. \n"
            + "Parameters: "
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_NEW_PASSWORD + "NEW_PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "Doe123 "
            + PREFIX_NEW_PASSWORD + "doe456";
    public static final String MESSAGE_SUCCESS = "You have successfully updated your password!";

    private final String oldPassword;
    private final String newPassword;

    public UpdatePasswordCommand(String oldPassword, String newPassword) {
        requireAllNonNull(oldPassword, newPassword);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.updatePassword(oldPassword, newPassword);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (InvalidPasswordException ipe) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD);
        } catch (BadDataException bde) {
            throw new CommandException(bde.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdatePasswordCommand) // instanceof handles nulls
                && this.oldPassword.equals(((UpdatePasswordCommand) other).oldPassword)
                && this.newPassword.equals(((UpdatePasswordCommand) other).newPassword);
    }
}
