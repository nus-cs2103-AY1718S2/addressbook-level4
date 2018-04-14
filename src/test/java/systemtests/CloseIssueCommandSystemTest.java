package systemtests;

import static seedu.progresschecker.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.progresschecker.testutil.TypicalIndexes.INDEX_ISSUE_ONE;

import org.junit.Test;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.logic.commands.CloseIssueCommand;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.credentials.GitDetails;
import seedu.progresschecker.testutil.GitDetailsBuilder;

public class CloseIssueCommandSystemTest extends ProgressCheckerSystemTest {

    private final String gitlogin = "gl r/adityaa1998/samplerepo-pr-practice gu/anminkang pc/aditya2018";

    @Test
    public void closeIssue() throws Exception {
        /* ----------------- Performing closeIssue operation on open issues on github -------------------- */
        Model expectedModel = getModel();
        GitDetails validDetails = new GitDetailsBuilder().build();
        expectedModel.loginGithub(validDetails);
        String command = "     " + CloseIssueCommand.COMMAND_WORD + "      "
                + INDEX_ISSUE_ONE.getOneBased() + "       ";
        String expectedResultMessage = String.format(CloseIssueCommand.MESSAGE_SUCCESS,
                String.valueOf(INDEX_ISSUE_ONE.getOneBased()));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: github not authenticated -> rejected*/
        command = CloseIssueCommand.COMMAND_WORD + " 34";
        assertCommandFailureWithoutAuthentication(command, CloseIssueCommand.MESSAGE_AUTHENTICATION_FAILURE);

        /* Case: invalid index (0) -> rejected */
        command = CloseIssueCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseIssueCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        command = CloseIssueCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseIssueCommand.MESSAGE_USAGE));

        /* Case: invalid index -> rejected */
        command = CloseIssueCommand.COMMAND_WORD + " " + 99999;
        assertCommandFailure(command, CloseIssueCommand.MESSAGE_FAILURE);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(CloseIssueCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseIssueCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(CloseIssueCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CloseIssueCommand.MESSAGE_USAGE));


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
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see ProgressCheckerSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
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

        executeCommand("gitlogout");
        executeCommand(command);
        assertApplicationDisplaysExpectedForIssue(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
