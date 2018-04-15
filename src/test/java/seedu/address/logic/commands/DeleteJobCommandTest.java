package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showJobAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_JOB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_JOB;
import static seedu.address.testutil.TypicalJobs.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.job.Job;

//@@author ChengSashankh
public class DeleteJobCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Job jobToDelete = model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased());
        DeleteJobCommand deleteJobCommand = prepareCommand(INDEX_FIRST_JOB);

        String expectedMessage = String.format(DeleteJobCommand.MESSAGE_DELETE_JOB_SUCCESS, jobToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteJob(jobToDelete);

        assertCommandSuccess(deleteJobCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredJobList().size() + 1);
        DeleteJobCommand deleteJobCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteJobCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showJobAtIndex(model, INDEX_FIRST_JOB);

        Job jobToDelete = model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased());
        DeleteJobCommand deleteJobCommand = prepareCommand(INDEX_FIRST_JOB);

        String expectedMessage = String.format(DeleteJobCommand.MESSAGE_DELETE_JOB_SUCCESS, jobToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteJob(jobToDelete);
        showNoJob(expectedModel);

        assertCommandSuccess(deleteJobCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showJobAtIndex(model, INDEX_FIRST_JOB);

        Index outOfBoundIndex = INDEX_SECOND_JOB;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getJobList().size());

        DeleteJobCommand deleteJobCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteJobCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Job jobToDelete = model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased());
        DeleteJobCommand deleteJobCommand = prepareCommand(INDEX_FIRST_JOB);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first job deleted
        deleteJobCommand.execute();
        undoRedoStack.push(deleteJobCommand);

        // undo -> reverts addressbook back to previous state and filtered job list to show all jobs
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deleteJob(jobToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredJobList().size() + 1);
        DeleteJobCommand deleteJobCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteJobCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteJobCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_JOB);
        DeleteJobCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_JOB);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteJobCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_JOB);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteJobCommand prepareCommand(Index index) {
        DeleteJobCommand deleteJobCommand = new DeleteJobCommand(index);
        deleteJobCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteJobCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoJob(Model model) {
        model.updateFilteredJobList(p -> false);

        assertTrue(model.getFilteredJobList().isEmpty());
    }
}
//@@author
