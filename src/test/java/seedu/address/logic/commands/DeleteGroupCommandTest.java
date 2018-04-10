//@@author jas5469
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalGroups.GROUP_A;
import static seedu.address.testutil.TypicalGroups.GROUP_B;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validNameUnfilteredList_success() throws Exception {
        Group groupToDelete = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = prepareCommand(groupToDelete.getInformation());
        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void executeUndoRedo_validNameUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Group groupToDelete = model.getFilteredGroupList().get(INDEX_FIRST_GROUP.getZeroBased());
        DeleteGroupCommand deleteGroupCommand = prepareCommand(groupToDelete.getInformation());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first to-do deleted
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts addressbook back to previous state and filtered to-do list to show all to-dos
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first to-do deleted again
        expectedModel.deleteGroup(groupToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() throws Exception {

        Information information = new Information("GROUP G");
        DeleteGroupCommand deleteGroupCommand = prepareCommand(information);

        assertCommandFailure(deleteGroupCommand, model, Messages.MESSAGE_INVALID_GROUP_NAME);
    }

    @Test
    public void executeUndoRedo_invalidNameUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Information information = new Information("GROUP G");
        DeleteGroupCommand deleteGroupCommand = prepareCommand(information);

        // execution failed -> deleteGroupCommand not pushed into undoRedoStack
        assertCommandFailure(deleteGroupCommand, model, Messages.MESSAGE_INVALID_GROUP_NAME);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteGroupCommand deleteGroupFirstCommand = prepareCommand(GROUP_A.getInformation());
        DeleteGroupCommand deleteGroupSecondCommand = prepareCommand(GROUP_B.getInformation());

        // same object -> returns true
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommand));

        // same values -> returns true
        DeleteGroupCommand deleteGroupFirstCommandCopy = prepareCommand(GROUP_A.getInformation());
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteGroupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteGroupFirstCommand.equals(null));

        // different group -> returns false
        assertFalse(deleteGroupFirstCommand.equals(deleteGroupSecondCommand));
    }

    /**
     * Returns a {@code DeleteGroupCommand} with the parameter {@code index}.
     */
    private DeleteGroupCommand prepareCommand(Information information) {
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(information);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteGroupCommand;
    }
}
