//@@author jas5469
package seedu.address.logic.commands;

import java.util.List;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.group.exceptions.GroupNotFoundException;

/**
 * Deletes a group identified using it's last displayed index from the address book.
 */
public class DeleteGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteGroup";
    public static final String COMMAND_ALIAS = "dG";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the group identified by the group Name used.\n"
            + "Parameters: GroupName(Must exist) KEYWORD ...\n"
            + "CS1010";

    public static final String MESSAGE_DELETE_GROUP_SUCCESS = "Deleted group: %1$s";
    public static final String MESSAGE_NO_SUCH_GROUP = "Group not found";

    private final Information groupName;

    private Group groupToDelete;

    public DeleteGroupCommand(Information groupName) {
        this.groupName = groupName;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        groupToDelete = new Group(groupName);
        List<Group> groupList = model.getFilteredGroupList();
        if (!groupList.contains(groupToDelete)) {
            throw new CommandException(MESSAGE_NO_SUCH_GROUP);
        } else {
            for (Group group : groupList) {
                if (groupName.equals(group.getInformation())) {
                    groupToDelete = group;
                }
            }
            try {
                model.deleteGroup(groupToDelete);
            } catch (GroupNotFoundException gnfe) {
                throw new CommandException(String.format(MESSAGE_NO_SUCH_GROUP,
                        groupName.toString()));
            }

            return new CommandResult(String.format(MESSAGE_DELETE_GROUP_SUCCESS,
                    groupToDelete.getInformation().toString()));

        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteGroupCommand // instanceof handles nulls
                && this.groupName.equals(((DeleteGroupCommand) other).groupName) // state check
                && Objects.equals(this.groupToDelete, ((DeleteGroupCommand) other).groupToDelete));
    }
}
