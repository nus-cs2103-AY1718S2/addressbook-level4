package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.END_DATE_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.END_TIME_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.ENTRY_TITLE_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENTRY_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_ALTERNATE_DATE;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.START_DATE_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENTRY_TITLE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_ALTERNATE_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATE_MEET_SUPPLIER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_GET_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEET_SUPPLIER;
import static seedu.address.testutil.TypicalCalendarEntries.GET_BOOKS;
import static seedu.address.testutil.TypicalCalendarEntries.MEET_SUPPLIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.entry.CalendarEntry;
import seedu.address.model.entry.EndDate;
import seedu.address.model.entry.EndTime;
import seedu.address.model.entry.EntryTitle;
import seedu.address.model.entry.StartDate;
import seedu.address.model.entry.StartTime;
import seedu.address.model.entry.exceptions.DuplicateCalendarEntryException;
import seedu.address.testutil.CalendarEntryBuilder;
import seedu.address.testutil.CalendarEntryUtil;

public class AddEntryCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addEntry() throws Exception {
        Model model = getModel();

        /* --------------------- Perform addEntry operations on the shown unfiltered list -------------------------- */

        /* Case: add a calendar entry to a non-empty calendar manager,
         * command with leading spaces and trailing spaces -> added
         */
        CalendarEntry toAdd = MEET_SUPPLIER;
        String command = "   " + AddEntryCommand.COMMAND_WORD + "  " + ENTRY_TITLE_DESC_MEET_SUPPLIER + "  "
                + START_DATE_DESC_MEET_SUPPLIER + " " + END_DATE_DESC_MEET_SUPPLIER + "   "
                + START_TIME_DESC_MEET_SUPPLIER + "   " + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandSuccess(command, toAdd);


        /* Case: undo adding Meeting with Supplier to the list -> Meeting with Supplier deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Meeting with Supplier to the list -> Meeting with Supplier added again */
        command = RedoCommand.COMMAND_WORD;
        model.addCalendarEntry(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a calendar entry with all fields same as another entry in the calendar manager except entry title
        -> added */
        toAdd = new CalendarEntryBuilder().withEntryTitle(VALID_ENTRY_TITLE_GET_BOOKS)
                .withStartDate(VALID_START_DATE_MEET_SUPPLIER).withEndDate(VALID_END_DATE_MEET_SUPPLIER)
                .withStartTime(VALID_START_TIME_MEET_SUPPLIER).withEndTime(VALID_END_TIME_MEET_SUPPLIER).build();
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_GET_BOOKS + START_DATE_DESC_MEET_SUPPLIER
                + END_DATE_DESC_MEET_SUPPLIER + START_TIME_DESC_MEET_SUPPLIER
                + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandSuccess(command, toAdd);

        /* Case: add a calendar entry with all fields same as another entry in the calendar manager except start date
        -> added */
        toAdd = new CalendarEntryBuilder().withEntryTitle(VALID_ENTRY_TITLE_MEET_SUPPLIER)
                .withStartDate(VALID_START_DATE_ALTERNATE_DATE).withEndDate(VALID_END_DATE_MEET_SUPPLIER)
                .withStartTime(VALID_START_TIME_MEET_SUPPLIER).withEndTime(VALID_END_TIME_MEET_SUPPLIER).build();
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_ALTERNATE_DATE
                + END_DATE_DESC_MEET_SUPPLIER + START_TIME_DESC_MEET_SUPPLIER
                + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandSuccess(command, toAdd);

        /* Case: add a calendar entry with all fields same as another entry in the calendar manager except end date
        -> added */
        toAdd = new CalendarEntryBuilder().withEntryTitle(VALID_ENTRY_TITLE_MEET_SUPPLIER)
                .withStartDate(VALID_START_DATE_MEET_SUPPLIER).withEndDate(VALID_END_DATE_GET_BOOKS)
                .withStartTime(VALID_START_TIME_MEET_SUPPLIER).withEndTime(VALID_END_TIME_MEET_SUPPLIER).build();
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_MEET_SUPPLIER
                + END_DATE_DESC_GET_BOOKS + START_TIME_DESC_MEET_SUPPLIER
                + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandSuccess(command, toAdd);

        /* Case: add a calendar entry with all fields same as another entry in the calendar manager except start time
        -> added */
        toAdd = new CalendarEntryBuilder().withEntryTitle(VALID_ENTRY_TITLE_MEET_SUPPLIER)
                .withStartDate(VALID_START_DATE_MEET_SUPPLIER).withEndDate(VALID_END_DATE_MEET_SUPPLIER)
                .withStartTime(VALID_START_TIME_GET_BOOKS).withEndTime(VALID_END_TIME_MEET_SUPPLIER).build();
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_MEET_SUPPLIER
                + END_DATE_DESC_MEET_SUPPLIER + START_TIME_DESC_GET_BOOKS
                + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandSuccess(command, toAdd);

        /* Case: add a calendar entry with all fields same as another entry in the calendar manager except end time
        -> added */
        toAdd = new CalendarEntryBuilder().withEntryTitle(VALID_ENTRY_TITLE_MEET_SUPPLIER)
                .withStartDate(VALID_START_DATE_MEET_SUPPLIER).withEndDate(VALID_END_DATE_MEET_SUPPLIER)
                .withStartTime(VALID_START_TIME_MEET_SUPPLIER).withEndTime(VALID_END_TIME_GET_BOOKS).build();
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_MEET_SUPPLIER
                + END_DATE_DESC_MEET_SUPPLIER + START_TIME_DESC_MEET_SUPPLIER
                + END_TIME_DESC_GET_BOOKS;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty calendar manager -> added */
        deleteAllCalendarEntries();
        assertCommandSuccess(MEET_SUPPLIER);

        /* Case: add a calendar entry, command with parameters in random order -> added */
        toAdd = GET_BOOKS;
        command = AddEntryCommand.COMMAND_WORD + END_TIME_DESC_GET_BOOKS + START_DATE_DESC_GET_BOOKS
                + ENTRY_TITLE_DESC_GET_BOOKS + START_TIME_DESC_GET_BOOKS + END_DATE_DESC_GET_BOOKS;
        assertCommandSuccess(command, toAdd);


        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate calendar entry -> rejected */
        command = CalendarEntryUtil.getAddEntryCommand(GET_BOOKS);
        assertCommandFailure(command, AddEntryCommand.MESSAGE_DUPLICATE_ENTRY);

        /* Case: missing entry title -> rejected */
        command = AddEntryCommand.COMMAND_WORD + START_DATE_DESC_MEET_SUPPLIER + END_DATE_DESC_MEET_SUPPLIER
                + START_TIME_DESC_MEET_SUPPLIER + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE));

        /* Case: missing end date -> rejected */
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_MEET_SUPPLIER
                + START_TIME_DESC_MEET_SUPPLIER + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE));

        /* Case: missing end time -> rejected */
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_MEET_SUPPLIER
                + END_DATE_DESC_MEET_SUPPLIER + START_TIME_DESC_MEET_SUPPLIER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "entryadds " + CalendarEntryUtil.getCalendarEntryDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid entry title -> rejected */
        command = AddEntryCommand.COMMAND_WORD + INVALID_ENTRY_TITLE_DESC + START_DATE_DESC_MEET_SUPPLIER
                + END_DATE_DESC_MEET_SUPPLIER + START_TIME_DESC_MEET_SUPPLIER + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandFailure(command, EntryTitle.MESSAGE_ENTRY_TITLE_CONSTRAINTS);

        /* Case: invalid start date -> rejected */
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + INVALID_START_DATE_DESC
                + END_DATE_DESC_MEET_SUPPLIER + START_TIME_DESC_MEET_SUPPLIER + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandFailure(command, StartDate.MESSAGE_START_DATE_CONSTRAINTS);

        /* Case: invalid end date -> rejected */
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_MEET_SUPPLIER
                + INVALID_END_DATE_DESC + START_TIME_DESC_MEET_SUPPLIER + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandFailure(command, EndDate.MESSAGE_END_DATE_CONSTRAINTS);

        /* Case: invalid start time -> rejected */
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_MEET_SUPPLIER
                + END_DATE_DESC_MEET_SUPPLIER + INVALID_START_TIME_DESC + END_TIME_DESC_MEET_SUPPLIER;
        assertCommandFailure(command, StartTime.MESSAGE_START_TIME_CONSTRAINTS);

        /* Case: invalid end time -> rejected */
        command = AddEntryCommand.COMMAND_WORD + ENTRY_TITLE_DESC_MEET_SUPPLIER + START_DATE_DESC_MEET_SUPPLIER
                + END_DATE_DESC_MEET_SUPPLIER + START_TIME_DESC_MEET_SUPPLIER + INVALID_END_TIME_DESC;
        assertCommandFailure(command, EndTime.MESSAGE_END_TIME_CONSTRAINTS);
    }


    /**
     * Executes the {@code AddEntryCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. {@code CalendarEntryListPanel} is equal to entry list in the current model added with {@code toAdd}.<br>
     * 6. Selected card remains unchanged.<br>
     * 7. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(CalendarEntry toAdd) {
        assertCommandSuccess(CalendarEntryUtil.getAddEntryCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(CalendarEntry)}. Executes {@code command}
     * instead.
     * @see AddEntryCommandSystemTest#assertCommandSuccess(CalendarEntry)
     */
    private void assertCommandSuccess(String command, CalendarEntry toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addCalendarEntry(toAdd);
        } catch (DuplicateCalendarEntryException dcee) {
            throw new IllegalArgumentException("CalendarEntry already exists in the model.");
        }
        String expectedResultMessage = String.format(AddEntryCommand.MESSAGE_ADD_ENTRY_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, CalendarEntry)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * 3. {@code CalendarEntryListPanel} is equal to the corresponding entry list in {@code expectedModel}.<br>
     * @see AddEntryCommandSystemTest#assertCommandSuccess(String, CalendarEntry)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCalendarEntryListDisplaysExpected(expectedModel);
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
     * 5. {@code CalendarEntryListPanel} remain unchanged.<br>
     * 6. Selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
