package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TimetableUnionCommand.MESSAGE_SELECT_PERSON_SUCCESS;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.TimetableUnionCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author AzuraAiR
/**
 * A system test class for the TimetableUnion table view, which contains interaction with other UI components.
 */
public class TimetableUnionCommandSystemTest extends AddressBookSystemTest {

    private static final String ODD_IDENTIFIER = "Odd";
    private static final String EVEN_IDENTIFIER = "Even";
    private static final String[] TYPICAL_PERSONS_NAME_STUB =
        {"Alice Pauline", "Benson Meier", "Carl Kurz", "Daniel Meier", "Elle Meyer", "Fiona Kunz", "George Best"};

    @Test
    public void timetableUnion() {
        /* ----------------------------------- Perform valid vacant operations  ----------------------------------- */

        /* Case: union odd timetables of person of index 1 and 2 -> success*/
        String command = TimetableUnionCommand.COMMAND_WORD + " Odd" + " 1 2";
        assertCommandSuccess(command, ODD_IDENTIFIER, buildPersonResultStub(2));

        /* Case: union odd timetables of person of index 1-7 -> success*/
        command = TimetableUnionCommand.COMMAND_WORD + " Odd" + " 1 2 3 4 5 6 7";
        assertCommandSuccess(command, ODD_IDENTIFIER, buildPersonResultStub(7));

        /* Case: union even timetables of person of index 1 and 2 -> success*/
        command = TimetableUnionCommand.COMMAND_WORD + " Even" + " 1 2";
        assertCommandSuccess(command, EVEN_IDENTIFIER, buildPersonResultStub(2));

        /* Case: union odd timetables of person of index 1-7 -> success*/
        command = TimetableUnionCommand.COMMAND_WORD + " Even" + " 1 2 3 4 5 6 7";
        assertCommandSuccess(command, EVEN_IDENTIFIER, buildPersonResultStub(7));

        /* Case: undo previous command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid vacant operations ----------------------------------- */

        /* Case: Invalid index -> failure*/
        command = TimetableUnionCommand.COMMAND_WORD + " Odd" + " 1 8";
        assertCommandFailure(command, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: Invalid additional parameter -> failure*/
        command = TimetableUnionCommand.COMMAND_WORD + " Every" + " 1 2";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, "")
                + TimetableUnionCommand.MESSAGE_USAGE);

        /* Case: Too manu spaces -> failure*/
        command = TimetableUnionCommand.COMMAND_WORD + " Odd" + " 1 2     3";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, "")
                + TimetableUnionCommand.MESSAGE_USAGE);
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
    private void assertCommandSuccess(String command, String oddEven, String persons) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SELECT_PERSON_SUCCESS, oddEven, persons);
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

    /**
     * Builds the expected result message of the persons printed
     * @param index number of persons
     * @return the expected result message
     */
    private String buildPersonResultStub(int index) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < index; i++) {
            sb.append(TYPICAL_PERSONS_NAME_STUB[i]);
            if (i != index - 1) {
                sb.append(", ");
            } else {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
