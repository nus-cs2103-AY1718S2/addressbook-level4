// @@author kush1509
package systemtests.job;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_JOBS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalJobs.DEVOPS_ENGINEER;
import static seedu.address.testutil.TypicalJobs.KEYWORD_MATCHING_LOCATION_SINGAPORE;
import static seedu.address.testutil.TypicalJobs.KEYWORD_MATCHING_POSITION_ENGINEER;
import static seedu.address.testutil.TypicalJobs.KEYWORD_MATCHING_SKILL_JAVA;
import static seedu.address.testutil.TypicalJobs.MARKETING_INTERN;
import static seedu.address.testutil.TypicalJobs.PRODUCT_MANAGER;
import static seedu.address.testutil.TypicalJobs.SOFTWARE_ENGINEER;

import org.junit.Test;

import seedu.address.logic.commands.job.JobDeleteCommand;
import seedu.address.logic.commands.job.JobFindCommand;
import seedu.address.model.Model;
import systemtests.AddressBookSystemTest;
import systemtests.ModelHelper;

public class JobFindCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findByPosition() {
        /* Case: find multiple jobs in address book, command with leading spaces and trailing spaces
         * -> 2 jobs found
         */
        String command = "   " + JobFindCommand.COMMAND_WORD + " p/" + KEYWORD_MATCHING_POSITION_ENGINEER + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, SOFTWARE_ENGINEER, DEVOPS_ENGINEER); // position contains Engineer
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where job list is displaying the jobs we are finding
         * -> 2 jobs found
         */
        command = JobFindCommand.COMMAND_WORD + " p/" + KEYWORD_MATCHING_POSITION_ENGINEER;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find same jobs in address book after deleting 1 of them -> 1 job found */
        executeCommand(JobDeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getJobList().contains(SOFTWARE_ENGINEER));
        command = JobFindCommand.COMMAND_WORD + " p/" + KEYWORD_MATCHING_POSITION_ENGINEER;
        expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, DEVOPS_ENGINEER);
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

        /* Case: find multiple jobs in address book, 2 keywords in reversed order -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " p/Manager Intern";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 keywords with 1 repeat -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " p/Manager Intern Manager";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 jobs found
         */
        command = JobFindCommand.COMMAND_WORD + " p/Manager Intern NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, keyword is same as position but of different case -> 1 job found */
        command = JobFindCommand.COMMAND_WORD + " p/EnGINeeR";
        ModelHelper.setFilteredJobList(expectedModel, DEVOPS_ENGINEER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, keyword is substring of position -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " p/Engin";
        ModelHelper.setFilteredJobList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, position is substring of keyword -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " p/Engineers";
        ModelHelper.setFilteredJobList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job not in address book -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " p/Analyst";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in empty address book -> 0 jobs found */
        deleteAllPersonsAndJobs();
        command = JobFindCommand.COMMAND_WORD + " p/" + KEYWORD_MATCHING_POSITION_ENGINEER;
        expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, SOFTWARE_ENGINEER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdjOB p/eNGIneeR";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void findByLocation() {
        /* Case: find multiple jobs in address book, command with leading spaces and trailing spaces
         * -> 2 jobs found
         */
        String command = "   " + JobFindCommand.COMMAND_WORD + " l/" + KEYWORD_MATCHING_LOCATION_SINGAPORE + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, SOFTWARE_ENGINEER, DEVOPS_ENGINEER); // position contains Engineer
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where job list is displaying the jobs we are finding
         * -> 2 jobs found
         */
        command = JobFindCommand.COMMAND_WORD + " l/" + KEYWORD_MATCHING_LOCATION_SINGAPORE;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find same jobs in address book after deleting 1 of them -> 1 job found */
        executeCommand(JobDeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getJobList().contains(SOFTWARE_ENGINEER));
        command = JobFindCommand.COMMAND_WORD + " l/" + KEYWORD_MATCHING_LOCATION_SINGAPORE;
        expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, DEVOPS_ENGINEER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job where job list is not displaying the job we are finding -> 1 job found */
        command = JobFindCommand.COMMAND_WORD + " l/India";
        ModelHelper.setFilteredJobList(expectedModel, PRODUCT_MANAGER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 keywords -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " l/Malaysia India";
        ModelHelper.setFilteredJobList(expectedModel, MARKETING_INTERN, PRODUCT_MANAGER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 keywords in reversed order -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " l/India Malaysia";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 keywords with 1 repeat -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " l/India Malaysia India";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 jobs found
         */
        command = JobFindCommand.COMMAND_WORD + " l/India Malaysia NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, keyword is same as location but of different case -> 1 job found */
        command = JobFindCommand.COMMAND_WORD + " l/SinGAPoRe";
        ModelHelper.setFilteredJobList(expectedModel, DEVOPS_ENGINEER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, keyword is substring of location -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " l/Singa";
        ModelHelper.setFilteredJobList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, location is substring of keyword -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " l/Singaporecity";
        ModelHelper.setFilteredJobList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job not in address book -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " l/Vietnam";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in empty address book -> 0 jobs found */
        deleteAllPersonsAndJobs();
        command = JobFindCommand.COMMAND_WORD + " l/" + KEYWORD_MATCHING_LOCATION_SINGAPORE;
        expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, SOFTWARE_ENGINEER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdjOB l/SingaporE";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void findBySkill() {
        /* Case: find multiple jobs in address book, command with leading spaces and trailing spaces
         * -> 2 jobs found
         */
        String command = "   " + JobFindCommand.COMMAND_WORD + " s/" + KEYWORD_MATCHING_SKILL_JAVA + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, SOFTWARE_ENGINEER); // position contains Engineer"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where job list is displaying the jobs we are finding
         * -> 2 jobs found
         */
        command = JobFindCommand.COMMAND_WORD + " s/" + KEYWORD_MATCHING_SKILL_JAVA;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find same jobs in address book after deleting 1 of them -> 1 job found */
        executeCommand(JobDeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getJobList().contains(SOFTWARE_ENGINEER));
        command = JobFindCommand.COMMAND_WORD + " s/" + KEYWORD_MATCHING_SKILL_JAVA;
        expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job where job list is not displaying the job we are finding -> 1 job found */
        command = JobFindCommand.COMMAND_WORD + " s/Testing";
        ModelHelper.setFilteredJobList(expectedModel, PRODUCT_MANAGER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 keywords -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " s/Excel Testing";
        ModelHelper.setFilteredJobList(expectedModel, MARKETING_INTERN, PRODUCT_MANAGER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 keywords in reversed order -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " s/Testing Excel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 keywords with 1 repeat -> 2 jobs found */
        command = JobFindCommand.COMMAND_WORD + " s/Excel Testing Excel";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple jobs in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 jobs found
         */
        command = JobFindCommand.COMMAND_WORD + " s/Excel Testing NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, keyword is same as location but of different case -> 1 job found */
        command = JobFindCommand.COMMAND_WORD + " s/ExCEl";
        ModelHelper.setFilteredJobList(expectedModel, MARKETING_INTERN);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, keyword is substring of location -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " s/Exc";
        ModelHelper.setFilteredJobList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in address book, location is substring of keyword -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " s/Excels";
        ModelHelper.setFilteredJobList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job not in address book -> 0 jobs found */
        command = JobFindCommand.COMMAND_WORD + " s/C++";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find job in empty address book -> 0 jobs found */
        deleteAllPersonsAndJobs();
        command = JobFindCommand.COMMAND_WORD + " s/" + KEYWORD_MATCHING_SKILL_JAVA;
        expectedModel = getModel();
        ModelHelper.setFilteredJobList(expectedModel, SOFTWARE_ENGINEER);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdjOB s/JaVA";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
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
