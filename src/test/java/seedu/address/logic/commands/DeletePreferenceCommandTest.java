package seedu.address.logic.commands;
//@@author SuxianAlicia
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;
import static seedu.address.testutil.TypicalPreferences.SHOES;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Preference;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeletePreferenceCommand}.
 */
public class DeletePreferenceCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new CalendarManager(), new UserPrefs());

    @Test
    public void execute_validPreference_success() throws Exception {
        Preference prefToDelete = SHOES;
        DeletePreferenceCommand deletePrefCommand = prepareCommand(SHOES);

        String expectedMessage = String.format(DeletePreferenceCommand.MESSAGE_DELETE_PREFERENCE_SUCCESS, prefToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.deletePreference(prefToDelete);

        assertCommandSuccess(deletePrefCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonexistentPreference_throwsCommandException() throws Exception {
        Preference prefToDelete = new Preference("shoe");
        DeletePreferenceCommand deletePrefCommand = prepareCommand(prefToDelete);

        assertCommandFailure(deletePrefCommand, model, DeletePreferenceCommand.MESSAGE_PREFERENCE_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_validPreference_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Preference prefToDelete = SHOES;
        DeletePreferenceCommand deletePrefCommand = prepareCommand(prefToDelete);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        // delete -> shoes preference deleted
        deletePrefCommand.execute();
        undoRedoStack.push(deletePrefCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same preference deleted again
        expectedModel.deletePreference(prefToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidPreference_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Preference prefToDelete = new Preference("shoe");
        DeletePreferenceCommand deletePrefCommand = prepareCommand(prefToDelete);

        // execution failed -> deletePrefCommand not pushed into undoRedoStack
        assertCommandFailure(deletePrefCommand, model, DeletePreferenceCommand.MESSAGE_PREFERENCE_NOT_FOUND);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeletePreferenceCommand deletePrefFirstCommand = prepareCommand(SHOES);
        DeletePreferenceCommand deletePrefSecondCommand = prepareCommand(COMPUTERS);

        // same object -> returns true
        assertTrue(deletePrefFirstCommand.equals(deletePrefFirstCommand));

        // same values -> returns true
        DeletePreferenceCommand deletePrefFirstCommandCopy = prepareCommand(SHOES);
        assertTrue(deletePrefFirstCommand.equals(deletePrefFirstCommandCopy));

        // different types -> returns false
        assertFalse(deletePrefFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deletePrefFirstCommand.equals(null));

        // different preference -> returns false
        assertFalse(deletePrefFirstCommand.equals(deletePrefSecondCommand));
    }

    /**
     * Returns a {@code DeletePreferenceCommand} with the parameter {@code preference}.
     */
    private DeletePreferenceCommand prepareCommand(Preference preference) {
        DeletePreferenceCommand deletePrefCommand = new DeletePreferenceCommand(preference);
        deletePrefCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deletePrefCommand;
    }
}
