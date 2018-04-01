// @@author kush1509
package systemtests.job;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NUMBER_OF_POSITIONS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSITION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TEAM_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_POSITIONS_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.POSITION_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_ALGORITHMS;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_EXCEL;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_JAVASCRIPT;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.TEAM_DESC_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_POSITIONS_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_DEVELOPER_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ALGORITHMS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVASCRIPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_INTERN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TEAM_DEVELOPER_INTERN;
import static seedu.address.testutil.TypicalJobs.DEVELOPER_INTERN;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.job.JobAddCommand;
import seedu.address.model.Model;
import seedu.address.model.job.Job;
import seedu.address.model.job.Location;
import seedu.address.model.job.NumberOfPositions;
import seedu.address.model.job.Position;
import seedu.address.model.job.Team;
import seedu.address.model.job.exceptions.DuplicateJobException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.JobBuilder;
import seedu.address.testutil.JobUtil;
import systemtests.AddressBookSystemTest;

public class JobAddCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add a job to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        Job toAdd = DEVELOPER_INTERN;
        String command = "   " + JobAddCommand.COMMAND_WORD + "  " + POSITION_DESC_DEVELOPER_INTERN + " "
                + TEAM_DESC_DEVELOPER_INTERN + " " + LOCATION_DESC_DEVELOPER_INTERN + " "
                + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN + " " + TAG_DESC_JAVASCRIPT + " " + TAG_DESC_ALGORITHMS;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Software Engineer to the list -> Software Engineer deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Software Engineer to the list -> Software Engineer added again */
        command = RedoCommand.COMMAND_WORD;
        model.addJob(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a job with all fields same as another job in the address book except position -> added */
        toAdd = new JobBuilder().withPosition(VALID_POSITION_INTERN).withTeam(VALID_TEAM_DEVELOPER_INTERN)
                .withLocation(VALID_LOCATION_DEVELOPER_INTERN)
                .withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN)
                .withTags(VALID_TAG_JAVASCRIPT, VALID_TAG_ALGORITHMS).build();
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN
                + TAG_DESC_JAVASCRIPT + TAG_DESC_ALGORITHMS;
        assertCommandSuccess(command, toAdd);

        /* Case: add a job with all fields same as another job in the address book except team -> added */
        toAdd = new JobBuilder().withPosition(VALID_POSITION_DEVELOPER_INTERN).withTeam(VALID_TEAM_INTERN)
                .withLocation(VALID_LOCATION_DEVELOPER_INTERN)
                .withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN)
                .withTags(VALID_TAG_JAVASCRIPT, VALID_TAG_ALGORITHMS).build();
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN
                + TAG_DESC_JAVASCRIPT + TAG_DESC_ALGORITHMS;
        assertCommandSuccess(command, toAdd);

        /* Case: add a job with all fields same as another job in the address book except location -> added */
        toAdd = new JobBuilder().withPosition(VALID_POSITION_DEVELOPER_INTERN).withTeam(VALID_TEAM_DEVELOPER_INTERN)
                .withLocation(VALID_LOCATION_INTERN).withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_DEVELOPER_INTERN)
                .withTags(VALID_TAG_JAVASCRIPT, VALID_TAG_ALGORITHMS).build();
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_INTERN + NUMBER_OF_POSITIONS_DESC_DEVELOPER_INTERN
                + TAG_DESC_JAVASCRIPT + TAG_DESC_ALGORITHMS;
        assertCommandSuccess(command, toAdd);

        /* Case: add a job with all fields same as another job in the address book except numberOfPositions -> added */
        toAdd = new JobBuilder().withPosition(VALID_POSITION_DEVELOPER_INTERN).withTeam(VALID_TEAM_DEVELOPER_INTERN)
                .withLocation(VALID_LOCATION_DEVELOPER_INTERN).withNumberOfPositions(VALID_NUMBER_OF_POSITIONS_INTERN)
                .withTags(VALID_TAG_JAVASCRIPT, VALID_TAG_ALGORITHMS).build();
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_DEVELOPER_INTERN + TEAM_DESC_DEVELOPER_INTERN
                + LOCATION_DESC_DEVELOPER_INTERN + NUMBER_OF_POSITIONS_DESC_INTERN + TAG_DESC_JAVASCRIPT
                + TAG_DESC_ALGORITHMS;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate job -> rejected */
        command = JobUtil.getJobAddCommand(DEVELOPER_INTERN);
        assertCommandFailure(command, JobAddCommand.MESSAGE_DUPLICATE_JOB);

        /* Case: missing position -> rejected */
        command = JobAddCommand.COMMAND_WORD + TEAM_DESC_INTERN
                + LOCATION_DESC_INTERN + NUMBER_OF_POSITIONS_DESC_INTERN + TAG_DESC_EXCEL;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobAddCommand.MESSAGE_USAGE));

        /* Case: missing team -> rejected */
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + LOCATION_DESC_INTERN
                + NUMBER_OF_POSITIONS_DESC_INTERN + TAG_DESC_EXCEL;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobAddCommand.MESSAGE_USAGE));

        /* Case: missing location -> rejected */
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + TEAM_DESC_INTERN
                + NUMBER_OF_POSITIONS_DESC_INTERN + TAG_DESC_EXCEL;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobAddCommand.MESSAGE_USAGE));

        /* Case: missing numberOfPositions -> rejected */
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + TEAM_DESC_INTERN + LOCATION_DESC_INTERN
                + TAG_DESC_EXCEL;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobAddCommand.MESSAGE_USAGE));

        /* Case: missing tags -> rejected */
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + TEAM_DESC_INTERN
                + LOCATION_DESC_INTERN + NUMBER_OF_POSITIONS_DESC_INTERN;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, JobAddCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + JobUtil.getJobDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);
        
        /* Case: invalid position -> rejected */
        command = JobAddCommand.COMMAND_WORD + INVALID_POSITION_DESC + TEAM_DESC_INTERN
                + LOCATION_DESC_INTERN + NUMBER_OF_POSITIONS_DESC_INTERN + TAG_DESC_EXCEL;
        assertCommandFailure(command, Position.MESSAGE_POSITION_CONSTRAINTS);

        /* Case: invalid team -> rejected */
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + INVALID_TEAM_DESC
                + LOCATION_DESC_INTERN + NUMBER_OF_POSITIONS_DESC_INTERN + TAG_DESC_EXCEL;
        assertCommandFailure(command, Team.MESSAGE_TEAM_CONSTRAINTS);

        /* Case: invalid location -> rejected */
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + TEAM_DESC_INTERN
                + INVALID_LOCATION_DESC + NUMBER_OF_POSITIONS_DESC_INTERN + TAG_DESC_EXCEL;
        assertCommandFailure(command, Location.MESSAGE_LOCATION_CONSTRAINTS);

        /* Case: invalid numberOfPositions -> rejected */
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + TEAM_DESC_INTERN
                + LOCATION_DESC_INTERN + INVALID_NUMBER_OF_POSITIONS_DESC + TAG_DESC_EXCEL;
        assertCommandFailure(command, NumberOfPositions.MESSAGE_NUMBER_OF_POSITIONS_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = JobAddCommand.COMMAND_WORD + POSITION_DESC_INTERN + TEAM_DESC_INTERN
                + LOCATION_DESC_INTERN + NUMBER_OF_POSITIONS_DESC_INTERN + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Executes the {@code JobAddCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code JobAddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code JobListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Job toAdd) {
        assertCommandSuccess(JobUtil.getJobAddCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Job)}. Executes {@code command}
     * instead.
     * @see JobAddCommandSystemTest#assertCommandSuccess(Job)
     */
    private void assertCommandSuccess(String command, Job toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addJob(toAdd);
        } catch (DuplicateJobException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(JobAddCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Job)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code JobListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see JobAddCommandSystemTest#assertCommandSuccess(String, Job)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedJobCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code JobListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedJobCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
