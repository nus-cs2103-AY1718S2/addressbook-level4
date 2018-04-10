package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUILDING_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUILDING_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BUILDING_3;
import static seedu.address.logic.commands.CommandTestUtil.MIXED_CASE_VACANT_COMMAND_WORD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BUILDING_1;
import static seedu.address.logic.commands.VacantCommand.MESSAGE_INVALID_BUILDING;
import static seedu.address.logic.commands.VacantCommand.MESSAGE_SUCCESS;
import static seedu.address.model.building.Building.MESSAGE_BUILDING_CONSTRAINTS;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.VacantCommand;
import seedu.address.model.Model;

/**
 * A system test class for the Venue Table table view, which contains interaction with other UI components.
 */
public class VacantCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void vacant() {
        /* ----------------------------------- Perform valid vacant operations  ----------------------------------- */

        /* Case: find building COM2, command with no leading and trailing spaces -> retrieved */
        String command = VacantCommand.COMMAND_WORD + " " + VALID_BUILDING_1;
        assertCommandSuccess(command);

        /* Case: find building COM2, command with leading spaces and trailing spaces
         * -> retrieved
         */
        command = "   " + VacantCommand.COMMAND_WORD + "   " + VALID_BUILDING_1 + "   ";
        assertCommandSuccess(command);

        /* Case: undo previous command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid vacant operations ----------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(VacantCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));

        /* Case: invalid number of parameters -> rejected */
        assertCommandFailure(VacantCommand.COMMAND_WORD + " " + INVALID_BUILDING_2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, VacantCommand.MESSAGE_USAGE));

        /* Case: invalid building regex -> rejected */
        assertCommandFailure(VacantCommand.COMMAND_WORD + " " + INVALID_BUILDING_1,
                String.format(MESSAGE_BUILDING_CONSTRAINTS));

        /* Case: invalid building name -> rejected */
        assertCommandFailure(VacantCommand.COMMAND_WORD + " " + INVALID_BUILDING_3,
                String.format(MESSAGE_INVALID_BUILDING));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_VACANT_COMMAND_WORD + " " + VALID_BUILDING_1, MESSAGE_UNKNOWN_COMMAND);
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
