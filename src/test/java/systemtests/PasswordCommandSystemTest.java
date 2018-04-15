package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.MIXED_CASE_PASSWORD_COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.address.logic.commands.PasswordCommand.INVALID_PASSWORD;
import static seedu.address.logic.commands.PasswordCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.PasswordCommand.MESSAGE_USAGE;

import org.junit.Test;

import seedu.address.commons.util.SecurityUtil;
import seedu.address.logic.commands.PasswordCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author yeggasd
/**
 * A system test class for the Password Command, which contains interaction with other UI components.
 */
public class PasswordCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void password() {
        /* ----------------------------------- Perform valid password operations  ----------------------------------- */

        /* Case: set password with no leading or trailing password -> password set */
        String command = PasswordCommand.COMMAND_WORD + " " + VALID_PASSWORD;
        assertCommandSuccess(command, VALID_PASSWORD);

        /* Case: set password with no leading or trailing password -> password set */
        command = "   " + PasswordCommand.COMMAND_WORD + "   " + VALID_PASSWORD + "   ";
        assertCommandSuccess(command, VALID_PASSWORD);

        /* Case: two parameters ->  password set as the whole string */
        command = "   " + PasswordCommand.COMMAND_WORD + "  " + VALID_PASSWORD + "  " + VALID_PASSWORD;
        assertCommandSuccess(command, VALID_PASSWORD + "  " + VALID_PASSWORD);

        /* Case: undo previous command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid password operations ---------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(PasswordCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, INVALID_PASSWORD, MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_PASSWORD_COMMAND_WORD + " " + VALID_PASSWORD, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code PasswordCommand}.<br>
     * 4. {@code PersonListPanel} remain unchanged.<br>
     * 5. {@code Model} and {@code Storage} is updated with password and encrypted accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String password) {
        Model expectedModel = getModel();
        byte[] hashedPassword = SecurityUtil.hashPassword(password);
        expectedModel.updatePassword(hashedPassword);
        String expectedResultMessage = String.format(MESSAGE_SUCCESS);
        executeCommand(command);
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
