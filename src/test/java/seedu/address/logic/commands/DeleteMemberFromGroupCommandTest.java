//@@author jas5469
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;


/**
 * Contains tests  and unit tests for
 * {@code DeleteMembersFromGroupCommand}.
 */
public class DeleteMemberFromGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_deletePerson_success() throws Exception {
        Group groupToDelete = model.getFilteredGroupList().get(2);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index index = Index.fromZeroBased(0);

        String expectedMessage = String.format(DeleteMemberFromGroupCommand.MESSAGE_DELETE_PERSON_FROM_GROUP_SUCCESS,
                personToDelete.getName().toString(), groupToDelete);
        DeleteMemberFromGroupCommand deleteMemberFromGroupCommand = prepareCommand(index, groupToDelete);
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.getFilteredGroupList().get(2).removePerson(personToDelete);

        assertCommandSuccess(deleteMemberFromGroupCommand, model, expectedMessage, model);
    }

    /**
     * Returns a {@code DeleteMemberFromGroupCommand} with the parameter {@code index}.
     */
    private DeleteMemberFromGroupCommand prepareCommand(Index index, Group groupToDelete) {
        DeleteMemberFromGroupCommand deleteMemberFromGroupCommand = new DeleteMemberFromGroupCommand(index,
                groupToDelete);
        deleteMemberFromGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteMemberFromGroupCommand;
    }
}
