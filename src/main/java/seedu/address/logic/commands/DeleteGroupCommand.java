package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.exceptions.GroupNotFoundException;

/**
 * Deletes a group specified by user from address book.
 */
public class DeleteGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "groupdelete";
    public static final String COMMAND_ALIAS = "gd";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the specified group from all persons in address book.\n"
            + "Parameters: GROUP_NAME (must be alphanumeric)\n"
            + "Example: " + COMMAND_WORD + " friends";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted GROUP: %1$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "Group does not exist in address book.";


    private Group groupToDelete;

    public DeleteGroupCommand(Group targetGroup) {
        this.groupToDelete = targetGroup;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(groupToDelete);
        try {
            model.deleteGroup(groupToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete));
        } catch (GroupNotFoundException e) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && Objects.equals(this.groupToDelete, ((DeleteGroupCommand) other).groupToDelete)); // state check
    }
}
