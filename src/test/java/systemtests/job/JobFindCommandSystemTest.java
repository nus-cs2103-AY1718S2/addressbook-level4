package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_JOBS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalJobs.KEYWORD_MATCHING_ENGINEER;
import static seedu.address.testutil.TypicalJobs.MARKETING_INTERN;
import static seedu.address.testutil.TypicalJobs.PRODUCT_MANAGER;
import static seedu.address.testutil.TypicalJobs.SOFTWARE_ENGINEER;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.job.JobDeleteCommand;
import seedu.address.logic.commands.job.JobFindCommand;
import seedu.address.model.Model;
import seedu.address.model.skill.Skill;

public class JobFindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void find() {
        /* Case: find multiple jobs in address book, command with leading spaces and trailing spaces
         * -> 2 jobs found
         */
        String command = "   " + JobFindCommand.COMMAND_WORD + " p/" + KEYWORD_MATCHING_ENGINEER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, SOFTWARE_ENGINEER); // position contains Engineer"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where job list is displaying the jobs we are finding
         * -> 2 jobs found
         */
        command = JobFindCommand.COMMAND_WORD + " p/" + KEYWORD_MATCHING_ENGINEER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job where job list is not displaying the job we are finding -> 1 job found */
        command = JobFindCommand.COMMAND_WORD + " p/Intern";
        ModelHelper.setFilteredJobList(expectedModel, MARKETING_INTERN);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 keywords -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " p/Intern Manager";
        ModelHelper.setFilteredJobList(expectedModel, MARKETING_INTERN, PRODUCT_MANAGER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

//        /* Case: find multiple jobs in address book, 2 keywords in reversed order -> 2 jobs found */
//        command = JobFindCommand.COMMAND_WORD + " p/Daniel Benson";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple jobs in address book, 2 keywords with 1 repeat -> 2 jobs found */
//        command = JobFindCommand.COMMAND_WORD + " p/Daniel Benson Daniel";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find multiple jobs in address book, 2 matching keywords and 1 non-matching keyword
//         * -> 2 jobs found
//         */
//        command = JobFindCommand.COMMAND_WORD + " p/Daniel Benson NonMatchingKeyWord";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: undo previous find command -> rejected */
//        command = UndoCommand.COMMAND_WORD;
//        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: redo previous find command -> rejected */
//        command = RedoCommand.COMMAND_WORD;
//        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
//        assertCommandFailure(command, expectedResultMessage);
//
//        /* Case: find same jobs in address book after deleting 1 of them -> 1 job found */
//        executeCommand(JobDeleteCommand.COMMAND_WORD + " 1");
//        assertFalse(getModel().getAddressBook().getJobList().contains(BENSON));
//        command = JobFindCommand.COMMAND_WORD + " p/" + KEYWORD_MATCHING_ENGINEER;
//        expectedModel = getModel();
//        ModelHelper.setFilteredJobList(expectedModel, DANIEL);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find job in address book, keyword is same as name but of different case -> 1 job found */
//        command = JobFindCommand.COMMAND_WORD + " p/MeIeR";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find job in address book, keyword is substring of name -> 0 jobs found */
//        command = JobFindCommand.COMMAND_WORD + " p/Mei";
//        ModelHelper.setFilteredJobList(expectedModel);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find job in address book, name is substring of keyword -> 0 jobs found */
//        command = JobFindCommand.COMMAND_WORD + " p/Meiers";
//        ModelHelper.setFilteredJobList(expectedModel);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find job not in address book -> 0 jobs found */
//        command = JobFindCommand.COMMAND_WORD + " p/Mark";
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find phone number of job in address book -> 0 jobs found */
//        command = JobFindCommand.COMMAND_WORD + " p/" + DANIEL.getPhone().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find address of job in address book -> 0 jobs found */
//        command = JobFindCommand.COMMAND_WORD + " p/" + DANIEL.getAddress().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find email of job in address book -> 0 jobs found */
//        command = JobFindCommand.COMMAND_WORD + " p/" + DANIEL.getEmail().value;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find skills of job in address book -> 0 jobs found */
//        List<Skill> skills = new ArrayList<>(DANIEL.getSkills());
//        command = JobFindCommand.COMMAND_WORD + " p/" + skills.get(0).skillName;
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: find while a job is selected -> selected card deselected */
//        showAllJobs();
//        selectJob(Index.fromOneBased(1));
//        assertFalse(getJobListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName));
//        command = JobFindCommand.COMMAND_WORD + " p/Daniel";
//        ModelHelper.setFilteredJobList(expectedModel, DANIEL);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardDeselected();
//
//        /* Case: find job in empty address book -> 0 jobs found */
//        deleteAllJobs();
//        command = JobFindCommand.COMMAND_WORD + " p/" + KEYWORD_MATCHING_ENGINEER;
//        expectedModel = getModel();
//        ModelHelper.setFilteredJobList(expectedModel, DANIEL);
//        assertCommandSuccess(command, expectedModel);
//        assertSelectedCardUnchanged();
//
//        /* Case: mixed case command word -> rejected */
//        command = "FiNd p/Meier";
//        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_JOBS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_JOBS_LISTED_OVERVIEW, expectedModel.getFilteredJobList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
