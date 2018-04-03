package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;


/**
 * Finds and groups all persons in Fastis whose name contains any of the argument keywords to a specific group.
 * Keyword matching is case sensitive.
 */
public class AddMembersToGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addGroupMember";
    public static final String COMMAND_ALIAS = "aGM";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds person whose names contain any of "
            + "the specified keywords (case-sensitive) and adds them to group list them.\n"
            + "Parameters: g/GroupName(Must exist) n/KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_GROUP + "CS1010"
            + PREFIX_NAME + "alice";

    public static final String MESSAGE_NO_SUCH_GROUP = "No such group exist.";
    public static final String MESSAGE_ADD_PERSON_TO_GROUP_SUCCESS = "%1$s added to group %2$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No such person in Fastis";
    public static final String MESSAGE_DUPLICATE_PERSON = "Person already in Group";
    public static final String MESSAGE_GROUP_NOT_FOUND = "No such Group in Fastis";
    public static final String MESSAGE_DUPLICATE_GROUP = "Group already in Group";

    private Person personToAdd;
    private Group groupToAdd;
    private Group groupAdded;

    public AddMembersToGroupCommand (Person personToAdd , Group groupToAdd) {
        this.personToAdd = personToAdd;
        this.groupToAdd = groupToAdd;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<Group> groupList = model.getFilteredGroupList();
        List<Person> personList = model.getFilteredPersonList();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getName().equals(personToAdd.getName()) ) {
                personToAdd=personList.get(i);
            }
        }
        if (!groupList.contains(groupToAdd)) {
            throw new CommandException(MESSAGE_NO_SUCH_GROUP);
        }
        if (!personList.contains(personToAdd)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        else {
            for (Group group : groupList) {
                if  (groupToAdd.getInformation().equals(group.getInformation())){
                    groupToAdd = group;
                    try {
                        groupAdded = group;
                        groupAdded.getPersonList().add(personToAdd);
                        model.updateGroup(groupToAdd,groupAdded);
                    } catch (DuplicatePersonException e){
                        throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                    } catch (DuplicateGroupException e) {
                        throw new CommandException(MESSAGE_DUPLICATE_GROUP);
                    } catch (GroupNotFoundException e) {
                        throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
                    }
                }
            }
            return new CommandResult(String.format(MESSAGE_ADD_PERSON_TO_GROUP_SUCCESS, personToAdd.getName(),
                    groupToAdd.getInformation().toString()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMembersToGroupCommand // instanceof handles nulls
                && personToAdd.equals(((AddMembersToGroupCommand) other).personToAdd));
    }



}
