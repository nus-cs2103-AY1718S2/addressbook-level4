package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENTRY_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_SUPPLIER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CALENDAR_ENTRIES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ENTRY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEntryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;
import seedu.address.model.entry.exceptions.CalendarEntryNotFoundException;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;
import seedu.address.testutil.CalendarEntryBuilder;

public class EditEntryCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void editEntry() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_THIRD_ENTRY;
        String command = " " + EditEntryCommand.COMMAND_WORD + "  " + index.getOneBased() + "    "
                + ENTRY_TITLE_DESC_MEET_SUPPLIER + " " + START_DATE_DESC_MEET_SUPPLIER + "  "
                + END_DATE_DESC_MEET_SUPPLIER + "    " + START_TIME_DESC_MEET_SUPPLIER + " "
                + END_TIME_DESC_MEET_SUPPLIER;

        CalendarEntry editedEntry = new CalendarEntryBuilder().withEntryTitle(VALID_ENTRY_TITLE_MEET_SUPPLIER)
                .withStartDate(VALID_START_DATE_MEET_SUPPLIER).withEndDate(VALID_END_DATE_MEET_SUPPLIER)
                .withStartTime(VALID_START_TIME_MEET_SUPPLIER).withEndTime(VALID_END_TIME_MEET_SUPPLIER).build();

        assertCommandSuccess(command, index, editedEntry);

        /* Case: undo editing the calendar entry in the list -> calendar entry restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last calendar entry in the list -> last calendar entry edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateCalendarEntry(
                getModel().getFilteredCalendarEntryList().get(INDEX_THIRD_ENTRY.getZeroBased()), editedEntry);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " 0" + ENTRY_TITLE_DESC_MEET_SUPPLIER,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEntryCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " -1" + ENTRY_TITLE_DESC_MEET_SUPPLIER,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEntryCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredOrderList().size() + 1;
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " " + invalidIndex
                        + ENTRY_TITLE_DESC_MEET_SUPPLIER,
                Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditEntryCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased(),
                EditEntryCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid entry title -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_ENTRY_TITLE_DESC,
                EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);

        /* Case: invalid start date -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_START_DATE_DESC,
                StartDate.MESSAGE_START_DATE_CONSTRAINTS);

        /* Case: invalid end date -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_END_DATE_DESC,
                EndDate.MESSAGE_END_DATE_CONSTRAINTS);

        /* Case: invalid start time -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_START_TIME_CONSTRAINTS);

        /* Case: invalid end time -> rejected */
        assertCommandFailure(EditEntryCommand.COMMAND_WORD + " " + INDEX_FIRST_ENTRY.getOneBased()
                        + INVALID_END_TIME_DESC,
                EndTime.MESSAGE_END_TIME_CONSTRAINTS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditOrderCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the order at index {@code toEdit} being
     * updated to values specified {@code editedOrder}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditEntryCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Index toEdit, CalendarEntry editedEntry) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateCalendarEntry(
                    expectedModel.getFilteredCalendarEntryList().get(toEdit.getZeroBased()), editedEntry);
            expectedModel.updateFilteredCalendarEntryList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
        } catch (DuplicateCalendarEntryException | CalendarEntryNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedEntry is either a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditEntryCommand.MESSAGE_EDIT_ENTRY_SUCCESS, editedEntry));
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredCalendarEntryList(PREDICATE_SHOW_ALL_CALENDAR_ENTRIES);
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
     * 4. Asserts that the selected person card and status bar remain unchanged.<br>
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
