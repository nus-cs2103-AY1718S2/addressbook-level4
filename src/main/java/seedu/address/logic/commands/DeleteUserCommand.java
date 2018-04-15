//@@author zacci
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.user.exceptions.CannotDeleteSelfException;
import seedu.address.model.user.exceptions.MustHaveAtLeastOneSecurityLevelThreeUserException;
import seedu.address.model.user.exceptions.NotEnoughAuthorityToDeleteException;
import seedu.address.model.user.exceptions.UserDoesNotExistException;

/**
 * Deletes a user from the PrisonBook.
 */
public class DeleteUserCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteuser";
    public static final String COMMAND_ALIAS = "du";
    public static final int MIN_SECURITY_LEVEL = 2;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes specified user.\n"
            + "Parameters: user/USERNAME_TO_BE_DELETED\n"
            + "Example: " + COMMAND_WORD + " user/prisonguard";

    public static final String MESSAGE_DELETE_CELL_SUCCESS = "User has been successfully deleted";

    private String userToDelete;

    /**
     * Creates a deleteUserCommand object
     * @param userToDelete username to be deleted
     */
    public DeleteUserCommand(String userToDelete) {
        this.userToDelete = userToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(userToDelete);
        try {
            model.deleteUser(userToDelete);
        } catch (CannotDeleteSelfException cdse) {
            throw new CommandException(cdse.getMessage());
        } catch (MustHaveAtLeastOneSecurityLevelThreeUserException mhalosltue) {
            throw new CommandException(mhalosltue.getMessage());
        } catch (UserDoesNotExistException udnee) {
            throw new CommandException(udnee.getMessage());
        } catch (NotEnoughAuthorityToDeleteException neatde) {
            throw new CommandException(neatde.getMessage());
        }

        return new CommandResult(MESSAGE_DELETE_CELL_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() {
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteUserCommand // instanceof handles nulls
                && this.userToDelete.equals(((DeleteUserCommand) other).userToDelete)); // state check
    }
}
