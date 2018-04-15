package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UPLOAD_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.MIXED_CASE_UPLOAD_COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.TEST_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UPLOAD_FILEPATH;
import static seedu.address.logic.commands.UploadCommand.MESSAGE_FILE_UNABLE_TO_SAVE;
import static seedu.address.logic.commands.UploadCommand.MESSAGE_REQUEST_TIMEOUT;
import static seedu.address.logic.commands.UploadCommand.MESSAGE_SUCCESS;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.logic.commands.UploadCommand;
import seedu.address.model.Model;
import seedu.address.storage.GoogleDriveStorage;

//@@author Caijun7
public class UploadCommandSystemTest extends AddressBookSystemTest {

    @BeforeClass
    public static void setTestEnvironment() {
        GoogleDriveStorage.setTestEnvironment();
    }

    @Test
    public void upload() {
        /* ----------------------------------- Perform valid upload operations  ----------------------------------- */

        /* Case: upload to valid filepath, command with no leading and trailing spaces -> retrieved */
        String command = UploadCommand.COMMAND_WORD + " " + VALID_UPLOAD_FILEPATH;
        assertCommandSuccess(command);

        /* Case: upload to valid filepath encrypted with test password, command with no leading and trailing spaces
         * -> retrieved
         */
        command = UploadCommand.COMMAND_WORD + " " + VALID_UPLOAD_FILEPATH + " " + TEST_PASSWORD;
        assertCommandSuccess(command);

        /* Case: upload to valid filepath, command with leading spaces and trailing spaces -> retrieved*/
        command = "   " + UploadCommand.COMMAND_WORD + "   " + VALID_UPLOAD_FILEPATH + "   ";
        assertCommandSuccess(command);

        /* ----------------------------------- Perform invalid upload operations ----------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(UploadCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));

        /* Case: invalid number of parameters -> rejected */
        assertCommandFailure(UploadCommand.COMMAND_WORD + " " + VALID_UPLOAD_FILEPATH + " " + TEST_PASSWORD + " "
                + VALID_UPLOAD_FILEPATH, String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));

        /* Case: upload to valid filepath encrypted with test password, command with leading spaces and trailing spaces
         * -> rejected
         */
        assertCommandFailure("   " + UploadCommand.COMMAND_WORD + "   " + VALID_UPLOAD_FILEPATH + "   " + TEST_PASSWORD
                + " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));

        /* Case: invalid filepath -> rejected */
        assertCommandFailure(UploadCommand.COMMAND_WORD + " " + INVALID_UPLOAD_FILEPATH,
                String.format(MESSAGE_FILE_UNABLE_TO_SAVE));

        GoogleDriveStorage.resetTestEnvironment();
        /* Case: no user response -> rejected */
        assertCommandFailure(UploadCommand.COMMAND_WORD + " " + INVALID_UPLOAD_FILEPATH,
                String.format(MESSAGE_REQUEST_TIMEOUT));
        GoogleDriveStorage.setTestEnvironment();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_UPLOAD_COMMAND_WORD + " " + VALID_UPLOAD_FILEPATH, MESSAGE_UNKNOWN_COMMAND);
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

    @AfterClass
    public static void resetTestEnvironment() {
        GoogleDriveStorage.resetTestEnvironment();
    }
}
