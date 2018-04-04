// @@author kush1509
package systemtests.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.job.JobDeleteCommand.MESSAGE_DELETE_JOB_SUCCESS;
import static seedu.address.testutil.TestUtil.getJob;
import static seedu.address.testutil.TestUtil.getLastJobIndex;
import static seedu.address.testutil.TestUtil.getMidJobIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.job.JobDeleteCommand;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.exceptions.JobNotFoundException;
import systemtests.AddressBookSystemTest;

public class JobDeleteCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_JOB_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, JobDeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first Job in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + JobDeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST.getOneBased() + "       ";
        Job deletedJob = removeJob(expectedModel, INDEX_FIRST);
        String expectedResultMessage = String.format(MESSAGE_DELETE_JOB_SUCCESS, deletedJob);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last Job in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastJobIndex = getLastJobIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastJobIndex);

        /* Case: undo deleting the last Job in the list -> last Job restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last Job in the list -> last Job deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeJob(modelBeforeDeletingLast, lastJobIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle Job in the list -> deleted */
        Index middleJobIndex = getMidJobIndex(getModel());
        assertCommandSuccess(middleJobIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        //        /* Case: filtered Job list, delete index within bounds of address book and Job list -> deleted */
        //        showJobsWithName(KEYWORD_MATCHING_MEIER);
        //        Index index = INDEX_FIRST;
        //        assertTrue(index.getZeroBased() < getModel().getFilteredJobList().size());
        //        assertCommandSuccess(index);
        //
        //        /* Case: filtered Job list, delete index within bounds of address book but out of bounds of Job list
        //         * -> rejected
        //         */
        //        showJobsWithName(KEYWORD_MATCHING_MEIER);
        //        int invalidIndex = getModel().getAddressBook().getJobList().size();
        //        command = JobDeleteCommand.COMMAND_WORD + " " + invalidIndex;
        //        assertCommandFailure(command, MESSAGE_INVALID_JOB_DISPLAYED_INDEX);
        //
        // /* --------------------- Performing delete operation while a Job card is selected ------------------------ */
        //
        //        /* Case: delete the selected Job -> Job list panel selects the Job before the deleted Job */
        //        showAllJobs();
        //        expectedModel = getModel();
        //        Index selectedIndex = getLastPersonIndex(expectedModel);
        //        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        //        selectJob(selectedIndex);
        //        command = JobDeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        //        deletedJob = removeJob(expectedModel, selectedIndex);
        //        expectedResultMessage = String.format(MESSAGE_DELETE_JOB_SUCCESS, deletedJob);
        //        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = JobDeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_JOB_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = JobDeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_JOB_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getJobList().size() + 1);
        command = JobDeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_JOB_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(JobDeleteCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_JOB_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(JobDeleteCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_JOB_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Job} at the specified {@code index} in {@code model}'s address book.
     * @return the removed Job
     */
    private Job removeJob(Model model, Index index) {
        Job targetJob = getJob(model, index);
        try {
            model.deleteJob(targetJob);
        } catch (JobNotFoundException pnfe) {
            throw new AssertionError("targetJob is retrieved from model.");
        }
        return targetJob;
    }

    /**
     * JobDeletes the Job at {@code toJobDelete} by creating a default {@code JobDeleteCommand}
     * using {@code toJobDelete} and performs the same verification as
     * {@code assertCommandSuccess(String, Model, String)}.
     * @see JobDeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toJobDelete) {
        Model expectedModel = getModel();
        Job deletedJob = removeJob(expectedModel, toJobDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_JOB_SUCCESS, deletedJob);

        assertCommandSuccess(
                JobDeleteCommand.COMMAND_WORD + " " + toJobDelete.getOneBased(), expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    // @@author kush1509-reused
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see JobDeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
