/*
// Commented out as it takes too long on travis, please uncomment before running local tests
package systemtests;
import static seedu.organizer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.organizer.testutil.TypicalTasks.KEYWORD_MATCHING_SPRING;
import org.junit.Test;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.ClearCommand;
import seedu.organizer.logic.commands.RedoCommand;
import seedu.organizer.logic.commands.UndoCommand;
import seedu.organizer.model.Model;
public class ClearCommandSystemTest extends OrganizerSystemTest {
    @Test
    public void clear() {
        final Model defaultModel = getModel();
        */
/* Case: clear non-empty organizer book, command with leading spaces and trailing alphanumeric characters and
 * spaces -> cleared
 *//*
        assertCommandSuccess("   " + ClearCommand.COMMAND_WORD + " ab12   ");
        assertSelectedCardUnchanged();
        */
/* Case: undo clearing organizer book -> original organizer book restored *//*
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();
        */
/* Case: redo clearing organizer book -> cleared *//*
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage);
        assertSelectedCardUnchanged();
        */
/* Case: selects first card in task list and clears organizer book -> cleared and no card selected *//*
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original organizer book
        selectTask(Index.fromOneBased(1));
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardDeselected();
        */
/* Case: filters the task list before clearing -> entire organizer book cleared *//*
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original organizer book
        showTasksWithName(KEYWORD_MATCHING_SPRING);
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();
        */
/* Case: clear empty organizer book -> cleared *//*
        assertCommandSuccess(ClearCommand.COMMAND_WORD);
        assertSelectedCardUnchanged();
        */
/* Case: mixed case command word -> rejected *//*
        assertCommandFailure("ClEaR", MESSAGE_UNKNOWN_COMMAND);
    }
    */
/**
 * Executes {@code command} and verifies that the command box displays an empty string, the result display
 * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
 * These verifications are done by
 * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
 * Also verifies that the command box has the default style class and the status bar's sync status changes.
 *
 * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
 *//*
    private void assertCommandSuccess(String command) {
        Model expectedModel = getModel();
        expectedModel.deleteCurrentUserTasks();
        assertCommandSuccess(command, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
    //@@author dominickenn
    */
/**
 * Executes {@code command} and verifies that the command box displays an empty string, the result display
 * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
 * These verifications are done by
 * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
 * Also verifies that the command box has the default style class and the status bar's sync status changes.
 * Also verifies that the {@code expectedResultMessage} is displayed
 * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
 *//*
    public void assertCommandSuccess(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        expectedModel.deleteCurrentUserTasks();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
    }
    //@@author
    */
/**
 * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
 * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
 *
 * @see ClearCommandSystemTest#assertCommandSuccess(String)
 *//*
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }
    */
/**
 * Executes {@code command} and verifies that the command box displays {@code command}, the result display
 * box displays {@code expectedResultMessage} and the model related components equal to the current model.
 * These verifications are done by
 * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
 * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
 * error style.
 *
 * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
 *//*
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
*/
