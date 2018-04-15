package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.MIXED_CASE_REMOVEPASSWORD_COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_HASH;
import static seedu.address.logic.commands.RemovePasswordCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemovePasswordCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author yeggasd
/**
 * A system test class for the RemovePassword Command, which contains interaction with other UI components.
 */
public class RemovePasswordCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void removePassword() {
        /* ----------------------------------- Perform valid password operations  ----------------------------------- */

        /* Case: set password and remove with no leading or trailing space in command -> no password change */
        String passwordCommand = PasswordCommand.COMMAND_WORD + " " + VALID_PASSWORD;
        String removeCommand = RemovePasswordCommand.COMMAND_WORD;
        assertCommandSuccess(passwordCommand, removeCommand);

        /* Case: set password and remove with trailing space in command -> no password change */
        passwordCommand = PasswordCommand.COMMAND_WORD + " " + VALID_PASSWORD;
        removeCommand = RemovePasswordCommand.COMMAND_WORD + " ";
        assertCommandSuccess(passwordCommand, removeCommand);

        /* Case: set password twice and remove -> no password change */
        passwordCommand = PasswordCommand.COMMAND_WORD + " " + VALID_PASSWORD;
        removeCommand = RemovePasswordCommand.COMMAND_WORD;
        getModel().updatePassword(VALID_PASSWORD_HASH);
        executeCommand(passwordCommand);
        assertCommandSuccess(passwordCommand, removeCommand);

        /* Case: undo previous command -> rejected */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid password operations ---------------------------------- */

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_REMOVEPASSWORD_COMMAND_WORD + " " + VALID_PASSWORD, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@RemovePasswordCommand}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 4. {@code PersonListPanel} remain unchanged.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String passwordCommand, String removeCommand) {
        Model expectedModel = getModel();
        expectedModel.updatePassword(VALID_PASSWORD_HASH);
        expectedModel.updatePassword(null);
        String expectedResultMessage = String.format(MESSAGE_SUCCESS);
        executeCommand(passwordCommand);
        executeCommand(removeCommand);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
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
