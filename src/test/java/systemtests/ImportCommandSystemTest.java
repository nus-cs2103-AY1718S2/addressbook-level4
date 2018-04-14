package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ENCRYPTED_IMPORT_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILE_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_IMPORT_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.MIXED_CASE_IMPORT_COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.TEST_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IMPORT_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.WRONG_PASSWORD;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_DATA_CONVERSION_ERROR;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_NOT_FOUND;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_PASSWORD_WRONG;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.commons.util.SecurityUtil;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author Caijun7
public class ImportCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void importCommand() throws Exception {
        Model model = getModel();

        /* ----------------------------------- Perform valid import operations  ----------------------------------- */

        /* Case: import from valid filepath, command with no leading and trailing spaces -> retrieved */
        String command = ImportCommand.COMMAND_WORD + " " + VALID_IMPORT_FILEPATH;
        assertCommandSuccess(command);

        /* Case: undo adding Amy to the list -> Amy deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.importAddressBook(VALID_IMPORT_FILEPATH, SecurityUtil.hashPassword(""));
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: import from valid filepath and decrypted with test password, command with no leading and
         * trailing spaces -> retrieved
         */
        command = ImportCommand.COMMAND_WORD + " " + ENCRYPTED_IMPORT_FILEPATH + " " + TEST_PASSWORD;
        assertCommandSuccess(command);
        SecurityUtil.encrypt(ENCRYPTED_IMPORT_FILEPATH, TEST_PASSWORD);

        /* Case: import from valid filepath, command with leading spaces and trailing spaces -> retrieved*/
        command = "   " + ImportCommand.COMMAND_WORD + "   " + VALID_IMPORT_FILEPATH + "   ";
        assertCommandSuccess(command);


        /* Case: import to valid filepath encrypted with test password, command with leading spaces and trailing spaces
         * -> retrieved
         */
        command = "   " + ImportCommand.COMMAND_WORD + "   " + ENCRYPTED_IMPORT_FILEPATH + "   "
                + TEST_PASSWORD + "  ";
        assertCommandSuccess(command);
        SecurityUtil.encrypt(ENCRYPTED_IMPORT_FILEPATH, TEST_PASSWORD);

        /* ----------------------------------- Perform invalid import operations ----------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(ImportCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        /* Case: invalid number of parameters -> rejected */
        assertCommandFailure(ImportCommand.COMMAND_WORD + " " + VALID_IMPORT_FILEPATH + " " + TEST_PASSWORD + " "
                + VALID_IMPORT_FILEPATH, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        /* Case: invalid filepath -> rejected */
        assertCommandFailure(ImportCommand.COMMAND_WORD + " " + INVALID_IMPORT_FILEPATH,
                String.format(MESSAGE_FILE_NOT_FOUND));

        /* Case: wrong password -> rejected */
        assertCommandFailure(ImportCommand.COMMAND_WORD + " " + ENCRYPTED_IMPORT_FILEPATH + " " + WRONG_PASSWORD,
                String.format(MESSAGE_PASSWORD_WRONG));

        /* Case: invalid file format -> rejected */
        assertCommandFailure(ImportCommand.COMMAND_WORD + " " + INVALID_FILE_FORMAT,
                String.format(MESSAGE_DATA_CONVERSION_ERROR));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_IMPORT_COMMAND_WORD + " " + VALID_IMPORT_FILEPATH, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected person.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SUCCESS);
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
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
     *
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
