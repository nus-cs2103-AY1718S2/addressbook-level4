//@@author Jason1im
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.BadDataException;

/**
 * Change the username of the user account.
 */
public class UpdateUsernameCommand extends Command {

    public static final String COMMAND_WORD = "updateusername";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
               + PREFIX_USERNAME;
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Updates the username.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "NEW_USERNAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "John";
    public static final String MESSAGE_SUCCESS = "You have successfully changed your username to %1$s";

    private final String newUsername;

    public UpdateUsernameCommand( String newUsername) {
        requireNonNull( newUsername);
        this.newUsername = newUsername;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.updateUsername(newUsername);
            return new CommandResult(String.format(MESSAGE_SUCCESS, newUsername));
        }  catch (BadDataException bde) {
            throw new CommandException(bde.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.UpdateUsernameCommand) // instanceof handles nulls
                && this.newUsername.equals(((seedu.address.logic.commands.UpdateUsernameCommand) other).newUsername);
    }

}
