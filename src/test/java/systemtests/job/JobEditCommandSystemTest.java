//@@author kush1509
package systemtests.job;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUMBER_OF_POSITIONS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SKILL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_POSITIONS_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_ALGORITHMS;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.SKILL_DESC_JAVASCRIPT;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_POSITIONS_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_ALGORITHMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_INTERN;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalJobs.DEVELOPER_INTERN;
import static seedu.address.testutil.TypicalJobs.INTERN;
import static seedu.address.testutil.TypicalJobs.KEYWORD_MATCHING_POSITION_ENGINEER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.job.JobEditCommand;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.job.exceptions.JobNotFoundException;
import seedu.address.model.skill.Skill;
import seedu.address.testutil.JobBuilder;
import seedu.address.testutil.JobUtil;
import systemtests.AddressBookSystemTest;

public class JobEditCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST;
        String command = " " + JobEditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + POSITION_DESC_INTERN + "  " + TEAM_DESC_INTERN + " " + LOCATION_DESC_INTERN + "  "
                + NUMBER_OF_POSITIONS_DESC_INTERN + " " + SKILL_DESC_ALGORITHMS + " ";
        Job editedJob = new JobBuilder().withPosition(VALID_POSITION_INTERN).withTeam(VALID_TEAM_INTERN)
                .withLocation(VALID_LOCATION_INTERN).withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_INTERN)
                .withSkills(VALID_SKILL_ALGORITHMS).build();
        assertCommandSuccess(command, index, editedJob);

        /* Case: undo editing the last job in the list -> last job restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last job in the list -> last job edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateJob(getModel().getFilteredJobList().get(INDEX_FIRST.getZeroBased()), editedJob);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a job with new values same as existing values -> edited */
        command = " " + JobEditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + POSITION_DESC_INTERN + "  "
                + TEAM_DESC_INTERN + " " + LOCATION_DESC_INTERN + "  " + NUMBER_OF_POSITIONS_DESC_INTERN
                + " " + SKILL_DESC_ALGORITHMS + " ";
        assertCommandSuccess(command, index, INTERN);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST;
        command = JobEditCommand.COMMAND_WORD + " " + index.getOneBased() + SKILL_DESC_FRIEND;
        Job jobToEdit = getModel().getFilteredJobList().get(index.getZeroBased());
        editedJob = new JobBuilder(jobToEdit).withSkills(VALID_SKILL_FRIEND).build();
        assertCommandSuccess(command, index, editedJob);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered job list, edit index within bounds of address book and job list -> edited */
        showJobsWithPosition(KEYWORD_MATCHING_POSITION_ENGINEER);
        index = INDEX_FIRST;
        assertTrue(index.getZeroBased() < getModel().getFilteredJobList().size());
        command = JobEditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + POSITION_DESC_INTERN;
        jobToEdit = getModel().getFilteredJobList().get(index.getZeroBased());
        editedJob = new JobBuilder(jobToEdit).withPosition(VALID_POSITION_INTERN).build();
        assertCommandSuccess(command, index, editedJob);

        /* Case: filtered job list, edit index within bounds of address book but out of bounds of job list
         * -> rejected
         */
        showJobsWithPosition(KEYWORD_MATCHING_POSITION_ENGINEER);
        int invalidIndex = getModel().getAddressBook().getJobList().size();
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " " + invalidIndex + POSITION_DESC_INTERN,
                Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " 0" + POSITION_DESC_INTERN,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, JobEditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " -1" + POSITION_DESC_INTERN,
              String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, JobEditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredJobList().size() + 1;
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " " + invalidIndex + POSITION_DESC_INTERN,
                Messages.MESSAGE_INVALID_JOB_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + POSITION_DESC_INTERN,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, JobEditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased(),
                JobEditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid position -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_POSITION_DESC,
                Position.MESSAGE_POSITION_CONSTRAINTS);

        /* Case: invalid team -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + INVALID_TEAM_DESC,
                Team.MESSAGE_TEAM_CONSTRAINTS);

        /* Case: invalid location -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                + INVALID_LOCATION_DESC, Location.MESSAGE_LOCATION_CONSTRAINTS);

        /* Case: invalid number of positions -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_NUMBER_OF_POSITIONS_DESC,
                NumberOfPositions.MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS);

        /* Case: invalid skill -> rejected */
        assertCommandFailure(JobEditCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased()
                        + INVALID_SKILL_DESC,
                Skill.MESSAGE_SKILL_CONSTRAINTS);

        /* Case: edit a job with new values same as another job's values -> rejected */
        executeCommand(JobUtil.getJobAddCommand(DEVELOPER_INTERN));
        assertTrue(getModel().getAddressBook().getJobList().contains(DEVELOPER_INTERN));
        index = INDEX_FIRST;
        assertFalse(getModel().getFilteredJobList().get(index.getZeroBased()).equals(DEVELOPER_INTERN));
        command = " " + JobEditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + POSITION_DESC_DEVELOPER_INTERN + "  " + TEAM_DESC_DEVELOPER_INTERN + " "
                + LOCATION_DESC_DEVELOPER_INTERN + "  " + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + " "
                + SKILL_DESC_JAVASCRIPT + " " + SKILL_DESC_ALGORITHMS;
        assertCommandFailure(command, JobEditCommand.MESSAGE_DUPLICATE_JOB);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Job, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see JobEditCommandSystemTest#assertCommandSuccess(String, Index, Job, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Job editedJob) {
        assertCommandSuccess(command, toEdit, editedJob, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code JobEditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the job at index {@code toEdit} being
     * updated to values specified {@code editedJob}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see JobEditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Job editedJob,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateJob(
                    expectedModel.getFilteredJobList().get(toEdit.getZeroBased()), editedJob);
            expectedModel.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        } catch (DuplicateJobException | JobNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedJob is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(JobEditCommand.MESSAGE_EDIT_JOB_SUCCESS, editedJob), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see JobEditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
