package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalCalendarEntries.getTypicalCalendarManagerWithEntries;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.CalendarEntry;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code  DeleteEntryCommand}.
 */
//@@author SuxianAlicia
public class DeleteEntryCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithOrders(),
            getTypicalCalendarManagerWithEntries(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        CalendarEntry entryToDelete = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteEntryCommand deleteEntryCommand = prepareCommand(INDEX_FIRST_ENTRY);

        String expectedMessage = String.format(DeleteEntryCommand.MESSAGE_DELETE_ENTRY_SUCCESS, entryToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
                model.getCalendarManager(), new UserPrefs());
        expectedModel.deleteCalendarEntry(entryToDelete);

        assertCommandSuccess(deleteEntryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteEntryCommand deleteEntryCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        CalendarEntry entryToDelete = model.getFilteredCalendarEntryList().get(INDEX_FIRST_ENTRY.getZeroBased());
        DeleteEntryCommand deleteEntryCommand = prepareCommand(INDEX_FIRST_ENTRY);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        // delete -> first order deleted
        deleteEntryCommand.execute();
        undoRedoStack.push(deleteEntryCommand);

        // undo -> reverts address book back to previous state and order list to show all calendar entries
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first order deleted again
        expectedModel.deleteCalendarEntry(entryToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCalendarEntryList().size() + 1);
        DeleteEntryCommand deleteEntryCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteEntryCommand not pushed into undoRedoStack
        assertCommandFailure(deleteEntryCommand, model, Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteEntryCommand deleteFirstEntryCommand = prepareCommand(INDEX_FIRST_ENTRY);
        DeleteEntryCommand deleteSecondEntryCommand = prepareCommand(INDEX_SECOND_ENTRY);

        // same object -> returns true
        assertTrue(deleteFirstEntryCommand.equals(deleteFirstEntryCommand));

        // same values -> returns true
        DeleteEntryCommand deleteFirstEntryCommandCopy = prepareCommand(INDEX_FIRST_ENTRY);
        assertTrue(deleteFirstEntryCommand.equals(deleteFirstEntryCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstEntryCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstEntryCommand.equals(deleteFirstEntryCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstEntryCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstEntryCommand.equals(null));

        // different order -> returns false
        assertFalse(deleteFirstEntryCommand.equals(deleteSecondEntryCommand));
    }

    /**
     * Returns a {@code DeleteEntryCommand} with the parameter {@code index}.
     */
    private DeleteEntryCommand prepareCommand(Index index) {
        DeleteEntryCommand deleteEntryCommand = new DeleteEntryCommand(index);
        deleteEntryCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteEntryCommand;
    }
}
