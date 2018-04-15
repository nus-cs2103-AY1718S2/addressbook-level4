//@@author deeheenguyen
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showInterviewAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INTERVIEW;
import static seedu.address.testutil.TypicalInterviews.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.interview.Interview;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteInterviewCommand}.
 */
public class DeleteInterviewCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Interview interviewToDelete = model.getFilteredInterviewList().get(INDEX_FIRST_INTERVIEW.getZeroBased());
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);

        String expectedMessage = String.format(DeleteInterviewCommand.MESSAGE_DELETE_INTERVIEW_SUCCESS,
                interviewToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteInterview(interviewToDelete);

        assertCommandSuccess(deleteInterviewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInterviewList().size() + 1);
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(outOfBoundIndex);

        assertCommandFailure(deleteInterviewCommand, model, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showInterviewAtIndex(model, INDEX_FIRST_INTERVIEW);

        Interview interviewToDelete = model.getFilteredInterviewList().get(INDEX_FIRST_INTERVIEW.getZeroBased());
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);

        String expectedMessage = String.format(DeleteInterviewCommand.MESSAGE_DELETE_INTERVIEW_SUCCESS,
                interviewToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteInterview(interviewToDelete);
        showNoInterview(expectedModel);

        assertCommandSuccess(deleteInterviewCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showInterviewAtIndex(model, INDEX_FIRST_INTERVIEW);

        Index outOfBoundIndex = INDEX_SECOND_INTERVIEW;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getInterviewList().size());

        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(outOfBoundIndex);

        assertCommandFailure(deleteInterviewCommand, model, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Interview interviewToDelete = model.getFilteredInterviewList().get(INDEX_FIRST_INTERVIEW.getZeroBased());
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteInterviewCommand.execute();
        undoRedoStack.push(deleteInterviewCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deleteInterview(interviewToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredInterviewList().size() + 1);
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteInterviewCommand, model, Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Interview} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted interview in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the interview object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameInterviewDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteInterviewCommand deleteInterviewCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showInterviewAtIndex(model, INDEX_SECOND_INTERVIEW);
        Interview interviewToDelete = model.getFilteredInterviewList().get(INDEX_FIRST_INTERVIEW.getZeroBased());
        // delete -> deletes second Interview in unfiltered interview list / first interview in filtered interview list
        deleteInterviewCommand.execute();
        undoRedoStack.push(deleteInterviewCommand);

        // undo -> reverts addressbook back to previous state and filtered interview list to show all interviews
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteInterview(interviewToDelete);
        assertNotEquals(interviewToDelete, expectedModel.getFilteredInterviewList()
                .get(INDEX_FIRST_INTERVIEW.getZeroBased()));
        // redo -> deletes same second interview in unfiltered interview list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteInterviewCommand deleteFirstCommand = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);
        DeleteInterviewCommand deleteSecondCommand = prepareInterviewCommand(INDEX_SECOND_INTERVIEW);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteInterviewCommand deleteFirstCommandCopy = prepareInterviewCommand(INDEX_FIRST_INTERVIEW);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different interview -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteInterviewCommand} with the parameter {@code index}.
     */
    private DeleteInterviewCommand prepareInterviewCommand(Index index) {
        DeleteInterviewCommand deleteInterviewCommand = new DeleteInterviewCommand(index);
        deleteInterviewCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteInterviewCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoInterview(Model model) {
        model.updateFilteredInterviewList(p -> false);

        assertTrue(model.getFilteredInterviewList().isEmpty());
    }
}
