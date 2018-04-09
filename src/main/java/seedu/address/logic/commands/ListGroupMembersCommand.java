//@@author jas5469
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.MembersInGroupPredicate;
import seedu.address.model.person.UniquePersonList;

/**
 * Lists all persons in the address book to the user under the same group.
 */
public class ListGroupMembersCommand extends Command {


    public static final String COMMAND_WORD = "listGroupMembers";
    public static final String COMMAND_ALIAS = "lGM";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose Group contain any of "
        + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD \n"
        + "Example: " + COMMAND_WORD + " CS1010";
        
    public static final String MESSAGE_SUCCESS = "Listed all persons under group %1$s";
    public static final String MESSAGE_NO_SUCH_GROUP = "No such group exist.";


    private final MembersInGroupPredicate predicate;
    private Group groupToList;
    private Group groupToBeListed;

    public ListGroupMembersCommand(MembersInGroupPredicate predicate,Group groupToList) {
        this.predicate = predicate;
        this.groupToList = groupToList;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Group> groupList = model.getFilteredGroupList();
        if(!groupList.contains(groupToList)) {
            throw new CommandException(MESSAGE_NO_SUCH_GROUP);
        }
        else {
            for(Group group : groupList) {
                if (groupToList.getInformation().equals(group.getInformation())) {
                    groupToBeListed = group;
                }
            }
            MembersInGroupPredicate predicateCurr = new MembersInGroupPredicate(groupToBeListed);
            model.updateFilteredPersonList(predicateCurr);
            return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));

        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
        || (other instanceof ListGroupMembersCommand // instanceof handles nulls
        && this.predicate.equals(((ListGroupMembersCommand) other).predicate)); // state check
    }
}

