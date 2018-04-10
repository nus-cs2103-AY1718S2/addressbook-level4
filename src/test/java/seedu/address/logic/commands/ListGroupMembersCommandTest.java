//@@author jas5469
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.group.MembersInGroupPredicate;

public class ListGroupMembersCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Group group1 = model.getFilteredGroupList().get(0);
        Group group2 = model.getFilteredGroupList().get(1);
        MembersInGroupPredicate firstPredicate =
                new MembersInGroupPredicate(group1);
        MembersInGroupPredicate secondPredicate =
                new MembersInGroupPredicate(group2);

        ListGroupMembersCommand findFirstCommand = new ListGroupMembersCommand(firstPredicate, group1);
        ListGroupMembersCommand findSecondCommand = new ListGroupMembersCommand(secondPredicate, group2);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ListGroupMembersCommand findFirstCommandCopy = new ListGroupMembersCommand(firstPredicate, group1);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
    @Test
    public void execute_noGroupFound() {
        String expectedMessage = String.format(ListGroupMembersCommand.MESSAGE_NO_SUCH_GROUP);
        Group groupToList = new Group(new Information("Group Z"));
        MembersInGroupPredicate predicate = new MembersInGroupPredicate(groupToList);
        ListGroupMembersCommand command = prepareCommand(predicate, groupToList);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_GroupFound_EmptyList_Success() {
        Group groupToList = new Group(new Information("Group A"));
        String expectedMessage = String.format("0 persons listed!");
        Model expectedModel = model;
        MembersInGroupPredicate predicate = new MembersInGroupPredicate(groupToList);
        ListGroupMembersCommand command = prepareCommand(predicate, groupToList);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Parses {@code userInput} into a {@code ListGroupMembersCommand}.
     */
    private ListGroupMembersCommand prepareCommand(MembersInGroupPredicate predicate, Group groupToList) {
        ListGroupMembersCommand command =
                new ListGroupMembersCommand(new MembersInGroupPredicate(groupToList), groupToList);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


}

