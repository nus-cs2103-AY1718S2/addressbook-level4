//@@author jas5469
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Finds by index and delete person in a group that exist in  Fastis .
 * Keyword matching is case sensitive.
 */
public class DeleteMemberFromGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteGroupMember";
    public static final String COMMAND_ALIAS = "dGM";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds person via index on the most recent list "
            + "and delete from group that contain the specified keywords (case-sensitive).\n"
            + "Parameters: INDEX (must be a positive integer) g/GroupName(Must exist) "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_GROUP + "CS1010";

    public static final String MESSAGE_NO_SUCH_GROUP = "No such group exist.";
    public static final String MESSAGE_DELETE_PERSON_FROM_GROUP_SUCCESS = "%1$s deleted from group %2$s";
    public static final String MESSAGE_GROUP_NOT_FOUND = "No such Group in Fastis";
    public static final String MESSAGE_DUPLICATE_GROUP = "Group already in Group";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No such Person in Group";

    private Index index;
    private Person personToDelete;
    private Group groupToDelete;
    private Group groupInvolved;
    private List<Group> groupList;

    public DeleteMemberFromGroupCommand(Index index, Group groupToDelete) {
        requireNonNull(index);
        this.index = index;
        this.groupToDelete = groupToDelete;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        for (Group group : groupList) {
            if (groupToDelete.getInformation().equals(group.getInformation())) {
                try {
                    groupInvolved = new Group(group.getInformation(), group.getPersonList());
                    groupInvolved.removePerson(personToDelete);
                    model.updateGroup(group, groupInvolved);
                } catch (DuplicateGroupException dge) {
                    throw new CommandException(MESSAGE_DUPLICATE_GROUP);
                } catch (GroupNotFoundException gnfe) {
                    throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
                } catch (PersonNotFoundException pnfe) {
                    throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
                }
            }
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_FROM_GROUP_SUCCESS, personToDelete.getName(),
                groupToDelete.getInformation().toString()));
    }

    @Override
    protected void preprocessUndoableCommand() throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        groupList = model.getFilteredGroupList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        boolean contains = new Boolean(Boolean.FALSE);
        for (Group g : groupList) {
            if (g.getInformation().equals(groupToDelete.getInformation())) {
                contains = true;
            }
        }
        if (contains == false) {
            throw new CommandException(MESSAGE_NO_SUCH_GROUP);
        } else {
            personToDelete = lastShownList.get(index.getZeroBased());
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands
                .DeleteMemberFromGroupCommand // instanceof handles nulls
                && groupToDelete.equals(((seedu.address.logic.commands
                .DeleteMemberFromGroupCommand) other).groupToDelete));
    }


}
