// @@author anh2111
package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POPULATION;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SaveReportCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

public class SaveReportCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void savereport() {
        Tag samplePop = new Tag("SEIntern");
        Tag samplePopComputing = new Tag("computing");
        /* ------------------------ Perform savereport operations -------------------------- */

        /* Case: command with leading spaces and trailing spaces
         * -> report displayed
         */
        String command = "   " + SaveReportCommand.COMMAND_WORD + "  " + PREFIX_POPULATION + samplePop.tagName + "   ";
        assertCommandSuccess(command, samplePop
        );

        /* Case: command with more than one population
         * -> report displayed of the last population
         */
        command = "   " + SaveReportCommand.COMMAND_WORD + "  " + PREFIX_POPULATION + samplePop.tagName
                + " " + PREFIX_POPULATION + samplePopComputing.tagName;
        assertCommandSuccess(command, samplePopComputing);

        /* Case: undo previous command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid operations ------------------------------------ */

        /* Case: empty population -> rejected */
        assertCommandFailure(SaveReportCommand.COMMAND_WORD + " " + PREFIX_POPULATION,
                MESSAGE_TAG_CONSTRAINTS);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SaveReport pop/Anh", MESSAGE_UNKNOWN_COMMAND);
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
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Tag population) {
        Model expectedModel = getModel();
        expectedModel.updateReport(population);
        expectedModel.addReport(expectedModel.getReport());
        String expectedResultMessage = SaveReportCommand.MESSAGE_SUCCESS + population.tagName;

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
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
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertTrue(isReportPanelOpenning());
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
// @@author
