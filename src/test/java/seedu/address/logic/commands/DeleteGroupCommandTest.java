package seedu.address.logic.commands;
//@@author SuxianAlicia
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalGroups.COLLEAGUES;
import static seedu.address.testutil.TypicalGroups.FRIENDS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Group;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteGroupCommand}.
 */
public class DeleteGroupCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void execute_validGroup_success() throws Exception {
        Group groupToDelete = FRIENDS;
        DeleteGroupCommand deleteGroupCommand = prepareCommand(FRIENDS);

        String expectedMessage = String.format(DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS, groupToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.deleteGroup(groupToDelete);

        assertCommandSuccess(deleteGroupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentGroup_throwsCommandException() throws Exception {
        Group groupToDelete = new Group("friend");
        DeleteGroupCommand deleteGroupCommand = prepareCommand(groupToDelete);

        assertCommandFailure(deleteGroupCommand, model, DeleteGroupCommand.MESSAGE_GROUP_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_validGroup_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Group groupToDelete = FRIENDS;
        DeleteGroupCommand deleteGroupCommand = prepareCommand(groupToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        // delete -> friends group deleted
        deleteGroupCommand.execute();
        undoRedoStack.push(deleteGroupCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same group deleted again
        expectedModel.deleteGroup(groupToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidGroup_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Group groupToDelete = new Group("friend");
        DeleteGroupCommand deleteGroupCommand = prepareCommand(groupToDelete);

        // execution failed -> deleteGroupCommand not pushed into undoRedoStack
        assertCommandFailure(deleteGroupCommand, model, DeleteGroupCommand.MESSAGE_GROUP_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteGroupCommand deleteGroupFirstCommand = prepareCommand(FRIENDS);
        DeleteGroupCommand deleteGroupSecondCommand = prepareCommand(COLLEAGUES);

        // same object -> returns true
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommand));

        // same values -> returns true
        DeleteGroupCommand deleteGroupFirstCommandCopy = prepareCommand(FRIENDS);
        assertTrue(deleteGroupFirstCommand.equals(deleteGroupFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteGroupFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteGroupFirstCommand.equals(null));

        // different preference -> returns false
        assertFalse(deleteGroupFirstCommand.equals(deleteGroupSecondCommand));
    }

    /**
     * Returns a {@code DeleteGroupCommand} with the parameter {@code group}.
     */
    private DeleteGroupCommand prepareCommand(Group group) {
        DeleteGroupCommand deleteGroupCommand = new DeleteGroupCommand(group);
        deleteGroupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteGroupCommand;
    }
}
