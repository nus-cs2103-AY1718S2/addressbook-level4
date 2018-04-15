package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_FE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOBTITLE_BE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BE;
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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.job.Job;
import seedu.address.testutil.EditJobDescriptorBuilder;
import seedu.address.testutil.JobBuilder;

//@@author ChengSashankh
public class EditJobCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Job editedJob = new JobBuilder().build();
        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder(editedJob).build();
        EditJobCommand editJobCommand = prepareCommand(INDEX_FIRST_JOB, descriptor);

        String expectedMessage = String.format(EditJobCommand.MESSAGE_EDIT_JOB_SUCCESS, editedJob);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateJob(model.getFilteredJobList().get(0), editedJob);

        assertCommandSuccess(editJobCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastJob = Index.fromOneBased(model.getFilteredJobList().size());
        Job lastJob = model.getFilteredJobList().get(indexLastJob.getZeroBased());

        JobBuilder jobInList = new JobBuilder(lastJob);
        Job editedJob = jobInList.withJobTitle(VALID_JOBTITLE_BE).withLocation(VALID_LOCATION_BE)
                .withTags(VALID_TAG_BE).build();

        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder().withJobTitle(VALID_JOBTITLE_BE)
                .withLocation(VALID_LOCATION_BE).withTags(VALID_TAG_BE).build();
        EditJobCommand editJobCommand = prepareCommand(indexLastJob, descriptor);

        String expectedMessage = String.format(EditJobCommand.MESSAGE_EDIT_JOB_SUCCESS, editedJob);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateJob(lastJob, editedJob);

        assertCommandSuccess(editJobCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditJobCommand editJobCommand = prepareCommand(INDEX_FIRST_JOB, new EditJobCommand.EditJobDescriptor());
        Job editedJob = model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased());

        String expectedMessage = String.format(EditJobCommand.MESSAGE_EDIT_JOB_SUCCESS, editedJob);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editJobCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showJobAtIndex(model, INDEX_FIRST_JOB);

        Job jobInFilteredList = model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased());
        Job editedJob = new JobBuilder(jobInFilteredList).withJobTitle(VALID_JOBTITLE_BE).build();
        EditJobCommand editJobCommand = prepareCommand(INDEX_FIRST_JOB,
                new EditJobDescriptorBuilder().withJobTitle(VALID_JOBTITLE_BE).build());

        String expectedMessage = String.format(EditJobCommand.MESSAGE_EDIT_JOB_SUCCESS, editedJob);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateJob(model.getFilteredJobList().get(0), editedJob);

        assertCommandSuccess(editJobCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateJobUnfilteredList_failure() {
        Job firstJob = model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased());
        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder(firstJob).build();
        EditJobCommand editJobCommand = prepareCommand(INDEX_SECOND_JOB, descriptor);

        assertCommandFailure(editJobCommand, model, EditJobCommand.MESSAGE_DUPLICATE_JOB);
    }

    @Test
    public void execute_duplicateJobFilteredList_failure() {
        showJobAtIndex(model, INDEX_FIRST_JOB);

        // edit job in filtered list into a duplicate in address book
        Job jobInList = model.getAddressBook().getJobList().get(INDEX_SECOND_JOB.getZeroBased());
        EditJobCommand editJobCommand = prepareCommand(INDEX_FIRST_JOB,
                new EditJobDescriptorBuilder(jobInList).build());

        assertCommandFailure(editJobCommand, model, EditJobCommand.MESSAGE_DUPLICATE_JOB);
    }

    @Test
    public void execute_invalidJobIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredJobList().size() + 1);
        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder()
                .withJobTitle(VALID_JOBTITLE_BE).build();
        EditJobCommand editJobCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editJobCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidJobIndexFilteredList_failure() {
        showJobAtIndex(model, INDEX_FIRST_JOB);
        Index outOfBoundIndex = INDEX_SECOND_JOB;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getJobList().size());

        EditJobCommand editJobCommand = prepareCommand(outOfBoundIndex,
                new EditJobDescriptorBuilder().withJobTitle(VALID_JOBTITLE_BE).build());

        assertCommandFailure(editJobCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Job editedJob = new JobBuilder().build();
        Job jobToEdit = model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased());
        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder(editedJob).build();
        EditJobCommand editJobCommand = prepareCommand(INDEX_FIRST_JOB, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        // edit -> first job edited
        editJobCommand.execute();
        undoRedoStack.push(editJobCommand);

        // undo -> reverts addressbook back to previous state and filtered job list to show all job
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first job edited again
        expectedModel.updateJob(jobToEdit, editedJob);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredJobList().size() + 1);
        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder()
                .withJobTitle(VALID_JOBTITLE_BE).build();
        EditJobCommand editJobCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editJobCommand not pushed into undoRedoStack
        assertCommandFailure(editJobCommand, model, Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Person} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameJobEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Job editedJob = new JobBuilder().build();
        EditJobCommand.EditJobDescriptor descriptor = new EditJobDescriptorBuilder(editedJob).build();
        EditJobCommand editJobCommand = prepareCommand(INDEX_FIRST_JOB, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showJobAtIndex(model, INDEX_SECOND_JOB);
        Job jobToEdit = model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased());
        // edit -> edits second job in unfiltered job list / first job in filtered job list
        editJobCommand.execute();
        undoRedoStack.push(editJobCommand);

        // undo -> reverts addressbook back to previous state and filtered job list to show all jobs
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateJob(jobToEdit, editedJob);
        assertNotEquals(model.getFilteredJobList().get(INDEX_FIRST_JOB.getZeroBased()), jobToEdit);
        // redo -> edits same second person in unfiltered person list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditJobCommand standardCommand = prepareCommand(INDEX_FIRST_JOB, DESC_FE);

        // same values -> returns true
        EditJobCommand.EditJobDescriptor copyDescriptor = new EditJobCommand.EditJobDescriptor(DESC_FE);
        EditJobCommand commandWithSameValues = prepareCommand(INDEX_FIRST_JOB, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditJobCommand(INDEX_SECOND_JOB, DESC_FE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditJobCommand(INDEX_FIRST_JOB, DESC_BE)));
    }

    /**
     * Returns an {@code EditJobCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditJobCommand prepareCommand(Index index, EditJobCommand.EditJobDescriptor descriptor) {
        EditJobCommand editJobCommand = new EditJobCommand(index, descriptor);
        editJobCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editJobCommand;
    }

}
//@@author
