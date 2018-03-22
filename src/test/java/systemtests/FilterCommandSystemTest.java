package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;

import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FilterCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void filter() {
        /* Case: find multiple persons in address book, command with leading spaces and trailing spaces
         * -> 2 persons found
         */
        String command = "   " + FilterCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER + "   ";
        Model expectedModel = getModel();


        /* Case: filter person where person list is not displaying the person we are finding -> 1 person found */
        command = FilterCommand.COMMAND_WORD + " 2018-03-29";
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book, 1 keywords -> 2 persons found */
        command = FilterCommand.COMMAND_WORD + " 2018-03-24";
        ModelHelper.setFilteredList(expectedModel, FIONA, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter multiple persons in address book 1 non-matching keyword
         * -> 0 persons found
         */
        command = FilterCommand.COMMAND_WORD + " 2017-03-23";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous filter command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous filter command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: filter person in address book, keyword is substring of date -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " 2018";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: filter person in address book, date is substring of keyword -> 0 persons found */
        command = FilterCommand.COMMAND_WORD + " 03-23";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
