package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TestUtil.getCalendarEntry;
import static seedu.address.testutil.TestUtil.getLastEntryIndex;
import static seedu.address.testutil.TestUtil.getMidEntryIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteEntryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;

public class DeleteEntryCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_ENTRY_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteEntryCommand.MESSAGE_USAGE);

    @Test
    public void deleteEntry() throws Exception {

        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first calendar entry in the list, command with leading spaces and trailing spaces
        -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteEntryCommand.COMMAND_WORD + "     " + INDEX_FIRST_ENTRY.getOneBased() + "   ";
        CalendarEntry deletedEntry = removeCalendarEntry(expectedModel, INDEX_FIRST_ENTRY);
        String expectedResultMessage = String.format(DeleteEntryCommand.MESSAGE_DELETE_ENTRY_SUCCESS, deletedEntry);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last calendar entry in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastEntryIndex = getLastEntryIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastEntryIndex);

        /* Case: undo deleting the last calendar entry in the list -> last calendar entry restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last calendar entry in the list -> last calendar entry deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeCalendarEntry(modelBeforeDeletingLast, lastEntryIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle calendar entry in the list -> deleted */
        Index middleEntryIndex = getMidEntryIndex(getModel());
        assertCommandSuccess(middleEntryIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteEntryCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_ENTRY_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteEntryCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_ENTRY_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteEntryCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteEntryCommand.COMMAND_WORD + " abc",
                MESSAGE_INVALID_DELETE_ENTRY_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteEntryCommand.COMMAND_WORD + " 1 abc",
                MESSAGE_INVALID_DELETE_ENTRY_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("entRYDelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code CalendarEntry} at the specified {@code index} in {@code model}'s calendar manager.
     * @return the removed calendar entry
     */
    private CalendarEntry removeCalendarEntry(Model model, Index index) {
        CalendarEntry targetEntry = getCalendarEntry(model, index);
        try {
            model.deleteCalendarEntry(targetEntry);
        } catch (CalendarEntryNotFoundException pnfe) {
            throw new AssertionError("not possible as targetEntry is retrieved from model.");
        }
        return targetEntry;
    }

    /**
     * Deletes the calendar entry at {@code toDelete} by creating a default {@code DeleteEntryCommand}
     * using {@code toDelete} and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteEntryCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        CalendarEntry deletedEntry = removeCalendarEntry(expectedModel, toDelete);
        String expectedResultMessage = String.format(DeleteEntryCommand.MESSAGE_DELETE_ENTRY_SUCCESS, deletedEntry);

        assertCommandSuccess(DeleteEntryCommand.COMMAND_WORD + " " + toDelete.getOneBased(), expectedModel,
                expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCalendarEntryListDisplaysExpected(expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCalendarEntryListDisplaysExpected(expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
