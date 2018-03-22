package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_VIDEOGAMES;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindPreferenceCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Group;

public class FindPreferenceCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void findPreference() {
        /* Case: find multiple persons in address book by their preferences,
        command with leading spaces and trailing spaces -> 2 persons found */
        String command = "   " + FindPreferenceCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_VIDEOGAMES + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, GEORGE);
        // Both Benson and George have preferences "videoGames"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find preference command where person list is displaying the persons we are finding
         * -> 2 persons found
         */
        command = FindPreferenceCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_VIDEOGAMES;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find persons via their preferences where person list is not displaying the person we are finding
        -> 3 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " computers";
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE, FIONA);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via preferences, 2 keywords -> 3 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " videoGames shoes";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via preferences, 2 keywords in reversed order
        -> 3 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " shoes videoGames";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via preferences, 2 keywords with 1 repeat -> 2 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " videoGames shoes videoGames";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book via preferences, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindPreferenceCommand.COMMAND_WORD + " videoGames shoes NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons via preferences in address book after deleting 1 of them -> 2 persons found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getAddressBook().getPersonList().contains(ALICE));
        command = FindPreferenceCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_VIDEOGAMES;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via preferences,
        keyword is same as preference name but of different case -> 2 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " VIdeOGameS";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via preferences, keyword is substring of preference name
        -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " videogame";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book via preferences,
        preference name is substring of keyword -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " videogamess";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person via preferences not in address book -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " facialWash";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book with FindPreferenceCommand -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " " + ELLE.getName().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " " + ELLE.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " " + ELLE.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = FindPreferenceCommand.COMMAND_WORD + " " + ELLE.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find groups of person in address book -> 0 persons found */
        List<Group> groups = new ArrayList<>(ELLE.getGroupTags());
        command = FindPreferenceCommand.COMMAND_WORD + " " + groups.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find via preferences while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assertFalse(getPersonListPanel().getHandleToSelectedCard().getName().equals(ELLE.getName().fullName));
        command = FindPreferenceCommand.COMMAND_WORD + " videoGames";
        ModelHelper.setFilteredList(expectedModel, BENSON, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book via group tags -> 0 persons found */
        deleteAllPersons();
        command = FindPreferenceCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_VIDEOGAMES;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdPREferenCE necklaces";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
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
