package systemtests;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.logic.commands.CommandTestUtil.ASSIGNEE_DESC_ANMIN;
import static seedu.progresschecker.logic.commands.CommandTestUtil.BODY_DESC_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_ASSIGNEE_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_BODY_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_LABEL_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.progresschecker.logic.commands.CommandTestUtil.LABEL_DEC_TASK;
import static seedu.progresschecker.logic.commands.CommandTestUtil.MILESTONE_DESC_ONE;
import static seedu.progresschecker.logic.commands.CommandTestUtil.TITLE_DESC_ONE;
import static seedu.progresschecker.testutil.TypicalIssue.ISSUE_ONE;
import static seedu.progresschecker.testutil.TypicalIssue.TEST_FIVE;
import static seedu.progresschecker.testutil.TypicalIssue.TEST_FOUR;
import static seedu.progresschecker.testutil.TypicalIssue.TEST_SEVEN;
import static seedu.progresschecker.testutil.TypicalIssue.TEST_SIX;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import seedu.progresschecker.commons.core.Messages;
import seedu.progresschecker.logic.commands.CreateIssueCommand;
import seedu.progresschecker.logic.commands.exceptions.CommandException;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.model.issues.Assignees;
import seedu.progresschecker.model.issues.Issue;
import seedu.progresschecker.model.issues.Labels;
import seedu.progresschecker.model.issues.Title;
import seedu.progresschecker.testutil.GitDetailsBuilder;
import seedu.progresschecker.testutil.IssueUtil;

//@@author adityaa1998
public class CreateIssueCommandSystemTest extends ProgressCheckerSystemTest {
    private final String gitlogin = "gl r/adityaa1998/samplerepo-pr-practice gu/anminkang pc/aditya2018";
    private final String gitlogout = "gitlogout";

    @Before
    public void setUpCreateIssue() throws Exception {

        Model model = getModel();
        GitDetails validDetails = new GitDetailsBuilder().build();
        model.loginGithub(validDetails);
    }

    @Test
    public void add() throws Exception {

        /* ------------------------------- Perform create issue operations ----------------------------------- */

        Issue toCreate = ISSUE_ONE;

        String command = "   " + CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandSuccess(command, toCreate);

        /* Case: create a issue, missing assignee -> created */
        assertCommandSuccess(TEST_FOUR);

        /* Case: create a issue, missing body -> created */
        assertCommandSuccess(TEST_FIVE);

        /* Case: create a issue, missing milestone -> created */
        assertCommandSuccess(TEST_SIX);

        /* Case: create a issue, missing labels -> created */
        assertCommandSuccess(TEST_SEVEN);

        /* ------------------------------- Perform invalid create issue operations ------------------------------- */

        /* Case: Github not authenticated -> rejected */
        command = CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandFailureWithoutAuthentication(command, CreateIssueCommand.MESSAGE_FAILURE);

        /* Case: missing title -> rejected */
        command = CreateIssueCommand.COMMAND_WORD + "  "
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateIssueCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "+issues " + IssueUtil.getCreateIssueCommand(toCreate);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid title -> rejected */
        command = "   " + CreateIssueCommand.COMMAND_WORD + "  " + INVALID_TITLE_DESC
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandFailure(command, Title.MESSAGE_TITLE_CONSTRAINTS);

        /* Case: invalid assignee -> rejected */
        command = "   " + CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
                + "  " + INVALID_ASSIGNEE_DESC + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandFailure(command, Assignees.MESSAGE_ASSIGNEES_CONSTRAINTS);

        /* Case: invalid milestone -> rejected */
        //command = CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
        //+ "  " + ASSIGNEE_DESC_ANMIN + " "
        //+ INVALID_MILESTONE_DESC + "   "
        //+ BODY_DESC_ONE + "   "
        //+ LABEL_DEC_TASK + "   ";

        /* Case: invalid labels -> rejected */
        command = CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + INVALID_BODY_DESC + "   "
                + INVALID_LABEL_DESC + "   ";
        assertCommandFailure(command, Labels.MESSAGE_LABEL_CONSTRAINTS);

    }

    /**
     * Executes the {@code CreateIssueCommand} that adds {@code toCreate} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code CreateIssueCommand} with the details of
     * {@code toCreate}.<br>
     * 4. {@code Model}, {@code Storage} and {@code issueListPanel} equal to the corresponding components in
     * the current model added with {@code toCreate}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Issue toCreate) throws Exception {
        assertCommandSuccess(IssueUtil.getCreateIssueCommand(toCreate), toCreate);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(issue)}. Executes {@code command}
     * instead.
     */
    private void assertCommandSuccess(String command, Issue toCreate) throws Exception {
        Model expectedModel = getModel();
        GitDetails validDetails = new GitDetailsBuilder().build();
        expectedModel.loginGithub(validDetails);
        try {
            expectedModel.createIssueOnGitHub(toCreate);
        } catch (IOException | CommandException e) {
            throw new IllegalArgumentException("Check authentication or parameters");
        }
        String expectedResultMessage = CreateIssueCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Issue)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code issueListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(gitlogin);
        executeCommand(command);
        assertApplicationDisplaysExpectedForIssue("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(gitlogin);
        executeCommand(command);
        assertApplicationDisplaysExpectedForIssue(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailureWithoutAuthentication(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(gitlogout);
        executeCommand(command);
        assertApplicationDisplaysExpectedForIssue(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
