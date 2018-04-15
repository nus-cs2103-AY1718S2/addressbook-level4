package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EXPORT_FILEPATH;
import static seedu.address.logic.commands.CommandTestUtil.MIXED_CASE_EXPORT_COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.TEST_PASSWORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPORT_FILEPATH;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_UNABLE_TO_SAVE;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.model.Model;

//@@author Caijun7
public class ExportCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void export() {
        /* ----------------------------------- Perform valid export operations  ----------------------------------- */

        /* Case: export to valid filepath, command with no leading and trailing spaces -> retrieved */
        String command = ExportCommand.COMMAND_WORD + " " + VALID_EXPORT_FILEPATH;
        assertCommandSuccess(command);

        /* Case: export to valid filepath encrypted with test password, command with no leading and trailing spaces
         * -> retrieved
         */
        command = ExportCommand.COMMAND_WORD + " " + VALID_EXPORT_FILEPATH + " " + TEST_PASSWORD;
        assertCommandSuccess(command);

        /* Case: export to valid filepath, command with leading spaces and trailing spaces -> retrieved*/
        command = "   " + ExportCommand.COMMAND_WORD + "   " + VALID_EXPORT_FILEPATH + "   ";
        assertCommandSuccess(command);

        /* ----------------------------------- Perform invalid export operations ----------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(ExportCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

        /* Case: invalid number of parameters -> rejected */
        assertCommandFailure(ExportCommand.COMMAND_WORD + " " + VALID_EXPORT_FILEPATH + " " + TEST_PASSWORD + " "
                + VALID_EXPORT_FILEPATH, String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

        /* Case: export to valid filepath encrypted with test password, command with leading spaces and trailing spaces
         * -> rejected
         */
        assertCommandFailure("   " + ExportCommand.COMMAND_WORD + "   " + VALID_EXPORT_FILEPATH + "   " + TEST_PASSWORD
                + " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

        /* Case: invalid filepath -> rejected */
        assertCommandFailure(ExportCommand.COMMAND_WORD + " " + INVALID_EXPORT_FILEPATH,
                String.format(MESSAGE_FILE_UNABLE_TO_SAVE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_EXPORT_COMMAND_WORD + " " + VALID_EXPORT_FILEPATH, MESSAGE_UNKNOWN_COMMAND);
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
}
