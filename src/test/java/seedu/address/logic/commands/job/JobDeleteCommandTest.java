// @@author kush1509
package seedu.address.logic.commands.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showJobAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalJobs.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.job.Job;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code JobDeleteCommand}.
 */
public class JobDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Job jobToDelete = model.getFilteredJobList().get(INDEX_FIRST.getZeroBased());
        JobDeleteCommand jobDeleteCommand = prepareCommand(INDEX_FIRST);

        String expectedMessage = String.format(JobDeleteCommand.MESSAGE_DELETE_JOB_SUCCESS, jobToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteJob(jobToDelete);

        assertCommandSuccess(jobDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredJobList().size() + 1);
        JobDeleteCommand jobDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(jobDeleteCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showJobAtIndex(model, INDEX_FIRST);

        Job jobToDelete = model.getFilteredJobList().get(INDEX_FIRST.getZeroBased());
        JobDeleteCommand jobDeleteCommand = prepareCommand(INDEX_FIRST);

        String expectedMessage = String.format(JobDeleteCommand.MESSAGE_DELETE_JOB_SUCCESS, jobToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteJob(jobToDelete);
        showNoJob(expectedModel);

        assertCommandSuccess(jobDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showJobAtIndex(model, INDEX_FIRST);

        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getJobList().size());

        JobDeleteCommand jobDeleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(jobDeleteCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Job jobToDelete = model.getFilteredJobList().get(INDEX_FIRST.getZeroBased());
        JobDeleteCommand jobDeleteCommand = prepareCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first job deleted
        jobDeleteCommand.execute();
        undoRedoStack.push(jobDeleteCommand);

        // undo -> reverts addressbook back to previous state and filtered job list to show all jobs
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first job deleted again
        expectedModel.deleteJob(jobToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredJobList().size() + 1);
        JobDeleteCommand jobDeleteCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(jobDeleteCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Job} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted job in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the job object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameJobDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        JobDeleteCommand jobDeleteCommand = prepareCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showJobAtIndex(model, INDEX_SECOND);
        Job jobToDelete = model.getFilteredJobList().get(INDEX_FIRST.getZeroBased());
        // delete -> deletes second job in unfiltered job list / first job in filtered job list
        jobDeleteCommand.execute();
        undoRedoStack.push(jobDeleteCommand);

        // undo -> reverts addressbook back to previous state and filtered job list to show all jobs
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteJob(jobToDelete);
        assertNotEquals(jobToDelete, model.getFilteredJobList().get(INDEX_FIRST.getZeroBased()));
        // redo -> deletes same second job in unfiltered job list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        JobDeleteCommand jobDeleteFirstCommand = prepareCommand(INDEX_FIRST);
        JobDeleteCommand jobDeleteSecondCommand = prepareCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(jobDeleteFirstCommand.equals(jobDeleteFirstCommand));

        // same values -> returns true
        JobDeleteCommand jobDeleteFirstCommandCopy = prepareCommand(INDEX_FIRST);
        assertTrue(jobDeleteFirstCommand.equals(jobDeleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        jobDeleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(jobDeleteFirstCommand.equals(jobDeleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(jobDeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(jobDeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(jobDeleteFirstCommand.equals(jobDeleteSecondCommand));
    }

    /**
     * Returns a {@code JobDeleteCommand} with the parameter {@code index}.
     */
    private JobDeleteCommand prepareCommand(Index index) {
        JobDeleteCommand jobDeleteCommand = new JobDeleteCommand(index);
        jobDeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return jobDeleteCommand;
    }

    /**
     * Updates {@code model}'s filtered job list to show no one.
     */
    private void showNoJob(Model model) {
        model.updateFilteredJobList(p -> false);

        assertTrue(model.getFilteredJobList().isEmpty());
    }

}
