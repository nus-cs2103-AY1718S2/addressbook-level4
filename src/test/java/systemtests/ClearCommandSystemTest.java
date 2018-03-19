package systemtests;

import static seedu.progresschecker.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.progresschecker.commons.core.index.Index;
import seedu.progresschecker.logic.commands.ClearCommand;
import seedu.progresschecker.logic.commands.RedoCommand;
import seedu.progresschecker.logic.commands.UndoCommand;
import seedu.progresschecker.model.Model;
import seedu.progresschecker.model.ModelManager;

public class ClearCommandSystemTest extends ProgressCheckerSystemTest {

    @Test
    public void clear() {
        final Model defaultModel = getModel();

        /* Case: clear non-empty ProgressChecker, command with leading spaces and trailing alphanumeric characters and
         * spaces -> cleared
         */
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardUnchanged();

        /* Case: undo clearing ProgressChecker -> original ProgressChecker restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command,  expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo clearing ProgressChecker -> cleared */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, new ModelManager());
        assertSelectedCardUnchanged();

        /* Case: selects first card in person list and clears ProgressChecker -> cleared and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original ProgressChecker
        selectPerson(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardDeselected();

        /* Case: filters the person list before clearing -> entire ProgressChecker cleared */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original ProgressChecker
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();

        /* Case: clear empty ProgressChecker -> cleared */
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> cleared */
        assertCommandSuccess("ClEaR");
        assertSelectedCardUnchanged();

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
     * These verifications are done by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, new ModelManager());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ClearCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
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
