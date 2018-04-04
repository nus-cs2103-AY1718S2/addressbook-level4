package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_VENUE_DESC_F1;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_VENUE_DESC_NDP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_VENUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_NDP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_F1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_VENUE_NDP;
import static seedu.address.testutil.TypicalEvents.F1RACE;
import static seedu.address.testutil.TypicalEvents.GSS;
import static seedu.address.testutil.TypicalEvents.HARIRAYA;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.EventUtil;

public class AddEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* ------------------------ Perform add operations on the shown unfiltered list ----------------------------- */

        /* Case: add an event to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String today = localDate.format(formatter);
        Event toAdd = new EventBuilder().withDate(today).build();
        String command = "   " + AddEventCommand.COMMAND_WORD + "  " + EVENT_NAME_DESC_F1 + "  " + EVENT_VENUE_DESC_F1
                + " d/" + today + "   " + EVENT_START_TIME_DESC_F1 + "   " + EVENT_END_TIME_DESC_F1;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding F1 to the list -> F1 deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding F1 to the list -> F1 added again */
        command = RedoCommand.COMMAND_WORD;
        model.addEvent(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a event with all fields same as another event in the address book except name -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_NAME_NDP).withVenue(VALID_EVENT_VENUE_F1)
                .withDate(VALID_EVENT_DATE_F1).withStartTime(VALID_EVENT_START_TIME_F1)
                .withEndTime(VALID_EVENT_END_TIME_F1).build();
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_NDP + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_F1
                + EVENT_START_TIME_DESC_F1 + EVENT_END_TIME_DESC_F1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a event with all fields same as another event in the address book except venue -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_NAME_F1).withVenue(VALID_EVENT_VENUE_NDP)
                .withDate(VALID_EVENT_DATE_F1).withStartTime(VALID_EVENT_START_TIME_F1)
                .withEndTime(VALID_EVENT_END_TIME_F1).build();
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_NDP + EVENT_DATE_DESC_F1
                + EVENT_START_TIME_DESC_F1 + EVENT_END_TIME_DESC_F1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a event with all fields same as another event in the address book except date -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_NAME_F1).withVenue(VALID_EVENT_VENUE_F1)
                .withDate(VALID_EVENT_DATE_NDP).withStartTime(VALID_EVENT_START_TIME_F1)
                .withEndTime(VALID_EVENT_END_TIME_F1).build();
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_NDP
                + EVENT_START_TIME_DESC_F1 + EVENT_END_TIME_DESC_F1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a event with all fields same as another event in the address book except address -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_NAME_F1).withVenue(VALID_EVENT_VENUE_F1)
                .withDate(VALID_EVENT_DATE_F1).withStartTime(VALID_EVENT_START_TIME_NDP)
                .withEndTime(VALID_EVENT_END_TIME_F1).build();
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_F1
                + EVENT_START_TIME_DESC_NDP + EVENT_END_TIME_DESC_F1;
        assertCommandSuccess(command, toAdd);

        /* Case: add a event with all fields same as another event in the address book
        except Timetable link -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_NAME_F1).withVenue(VALID_EVENT_VENUE_F1)
                .withDate(VALID_EVENT_DATE_F1).withStartTime(VALID_EVENT_START_TIME_F1)
                .withEndTime(VALID_EVENT_END_TIME_NDP).build();
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_F1
                + EVENT_START_TIME_DESC_F1 + EVENT_END_TIME_DESC_NDP;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        assertCommandSuccess(GSS);

        /* Case: add a event, missing tags -> added */
        assertCommandSuccess(HARIRAYA);

        /* ----------------------------------- Perform invalid add operations --------------------------------------- */

        /* Case: add a duplicate event -> rejected */
        command = EventUtil.getAddEventCommand(HARIRAYA);
        assertCommandFailure(command, AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: add a duplicate event except with different tags -> rejected */
        // "friends" is an existing tag used in the default model, see TypicalEvents#ALICE
        // This test will fail if a new tag that is not in the model is used, see the bug documented in
        // AddressBook#addEvent(Event)
        command = EventUtil.getAddEventCommand(HARIRAYA);
        assertCommandFailure(command, AddEventCommand.MESSAGE_DUPLICATE_EVENT);

        /* Case: missing name -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_F1 + EVENT_START_TIME_DESC_F1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing venue -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_DATE_DESC_F1 + EVENT_START_TIME_DESC_F1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_F1 + EVENT_START_TIME_DESC_F1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing address -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_F1;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + EventUtil.getEventDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid name -> rejected */
        command = AddEventCommand.COMMAND_WORD + INVALID_EVENT_NAME_DESC + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_F1
                + EVENT_START_TIME_DESC_F1 + EVENT_END_TIME_DESC_F1;
        assertCommandFailure(command, Event.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid venue -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + INVALID_EVENT_VENUE_DESC + EVENT_DATE_DESC_F1
                + EVENT_START_TIME_DESC_F1 + EVENT_END_TIME_DESC_F1;
        assertCommandFailure(command, Event.MESSAGE_VENUE_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_F1 + INVALID_EVENT_DATE_DESC
                + EVENT_START_TIME_DESC_F1 + EVENT_END_TIME_DESC_F1;
        assertCommandFailure(command, Event.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid address -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_F1
                + INVALID_EVENT_START_TIME_DESC + EVENT_END_TIME_DESC_F1;
        assertCommandFailure(command, Event.MESSAGE_TIME_CONSTRAINTS);

        /* Case: invalid end time -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_DESC_F1 + EVENT_VENUE_DESC_F1 + EVENT_DATE_DESC_F1
                + EVENT_START_TIME_DESC_F1 + INVALID_EVENT_END_TIME_DESC;
        assertCommandFailure(command, Event.MESSAGE_TIME_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddEventCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command node displays an empty string.<br>
     * 2. Command node has the default style class.<br>
     * 3. Result display node displays the success message of executing {@code AddEventCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code EventListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Event toAdd) {
        assertCommandSuccess(EventUtil.getAddEventCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Event)}. Executes {@code command}
     * instead.
     *
     * @see AddEventCommandSystemTest#assertCommandSuccess(Event)
     */
    private void assertCommandSuccess(String command, Event toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addEvent(toAdd);
        } catch (DuplicateEventException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddEventCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Event)} except asserts that
     * the,<br>
     * 1. Result display node displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code EventListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see AddEventCommandSystemTest#assertCommandSuccess(String, Event)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command node displays {@code command}.<br>
     * 2. Command node has the error style class.<br>
     * 3. Result display node displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code EventListPanel} remain unchanged.<br>
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
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
