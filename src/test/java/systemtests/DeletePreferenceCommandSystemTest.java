//@@author SuxianAlicia
package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeletePreferenceCommand.MESSAGE_DELETE_PREFERENCE_SUCCESS;
import static seedu.address.logic.commands.DeletePreferenceCommand.MESSAGE_PREFERENCE_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPreferences.COMPUTERS;
import static seedu.address.testutil.TypicalPreferences.NECKLACES;
import static seedu.address.testutil.TypicalPreferences.VIDEO_GAMES;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletePreferenceCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Preference;
import seedu.address.model.tag.exceptions.PreferenceNotFoundException;

public class DeletePreferenceCommandSystemTest extends AddressBookSystemTest {
    private static final String MESSAGE_INVALID_DELETE_PREFERENCE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeletePreferenceCommand.MESSAGE_USAGE);

    @Test
    public void deletePreference() {
        /* ------------ Performing delete preference operation while an unfiltered list is being shown -------------- */

        /* Case: delete the preference "videoGames" in the list, command with leading spaces and trailing spaces ->
        deleted */
        Model expectedModel = getModel();
        Model modelBeforeDeletingLast = getModel();
        String command = "     " + DeletePreferenceCommand.COMMAND_WORD + "      " + VIDEO_GAMES.tagName + "       ";
        Preference deletedPreference = VIDEO_GAMES;
        removePreference(expectedModel, VIDEO_GAMES);
        String expectedResultMessage = String.format(MESSAGE_DELETE_PREFERENCE_SUCCESS, deletedPreference);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: undo deleting the preference "videoGames" in the list -> preference "videoGames" restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the preference "videoGames" in the list -> "videoGames" deleted again */
        command = RedoCommand.COMMAND_WORD;
        removePreference(modelBeforeDeletingLast, VIDEO_GAMES);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        assertEquals(expectedModel, modelBeforeDeletingLast);
        /* ------------ Performing delete preference operation while a filtered list is being shown ---------------- */

        /* Case: filtered person list, delete existing preference but not in filtered person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        expectedModel = getModel();
        deletedPreference = NECKLACES;
        removePreference(expectedModel, NECKLACES);
        command = DeletePreferenceCommand.COMMAND_WORD + " " + NECKLACES.tagName;
        expectedResultMessage = String.format(MESSAGE_DELETE_PREFERENCE_SUCCESS, deletedPreference);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: filtered person list, delete non-existing preference in address book -> rejected */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Preference invalidPref = NECKLACES;
        command = DeletePreferenceCommand.COMMAND_WORD + " " + invalidPref.tagName;
        assertCommandFailure(command, MESSAGE_PREFERENCE_NOT_FOUND);

        /* --------------- Performing delete preference operation while a person card is selected ------------------ */

        /* Case: delete preference existing in the selected person -> person list panel still selects the person */
        showAllPersons();
        expectedModel = getModel();
        deletedPreference = COMPUTERS;
        Index selectedIndex = Index.fromOneBased(2);
        Index expectedIndex = selectedIndex;
        selectPerson(selectedIndex);
        command = DeletePreferenceCommand.COMMAND_WORD + " " + COMPUTERS.tagName;
        removePreference(expectedModel, COMPUTERS);
        expectedResultMessage = String.format(MESSAGE_DELETE_PREFERENCE_SUCCESS, deletedPreference);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------- Performing invalid delete preference operation ------------------------------ */

        /* Case: invalid arguments (non-alphanumeric arguments) -> rejected */
        assertCommandFailure(DeletePreferenceCommand.COMMAND_WORD + " sh!es",
                MESSAGE_INVALID_DELETE_PREFERENCE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeletePreferenceCommand.COMMAND_WORD + " shoes computers",
                MESSAGE_INVALID_DELETE_PREFERENCE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("pREFDelEte shoes", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Preference} in {@code model}'s address book.
     */
    private void removePreference(Model model, Preference toDelete) {
        try {
            model.deletePreference(toDelete);
        } catch (PreferenceNotFoundException pnfe) {
            throw new AssertionError("Preference should exist in address book.");
        }
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeletePreferenceCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
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
