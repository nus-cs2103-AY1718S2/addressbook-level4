package systemtests;
//@@author SuxianAlicia
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_GROUP_NOT_FOUND;
import static seedu.address.testutil.TypicalGroups.BUDDIES;
import static seedu.address.testutil.TypicalGroups.FRIENDS;
import static seedu.address.testutil.TypicalGroups.NEIGHBOURS;
import static seedu.address.testutil.TypicalGroups.TWITTER;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Group;
import seedu.address.model.tag.exceptions.GroupNotFoundException;

public class DeleteGroupCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);

    @Test
    public void deleteGroup() {
        /* ------------- Performing delete group operation while an unfiltered list is being shown -------------- */

        /* Case: delete the group "twitter" in the list, command with leading spaces and trailing spaces ->
        deleted */
        Model expectedModel = getModel();
        Model modelBeforeDeletingLast = getModel();
        String command = "     " + DeleteGroupCommand.COMMAND_WORD + "      " + TWITTER.tagName + "       ";
        Group deletedGroup = TWITTER;
        removeGroup(expectedModel, TWITTER);
        String expectedResultMessage = String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: undo deleting the group "twitter" in the list -> group "twitter" restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the group "twitter" in the list -> "twitter" deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeGroup(modelBeforeDeletingLast, TWITTER);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        assertEquals(expectedModel, modelBeforeDeletingLast);
        /* -------------- Performing delete group operation while a filtered list is being shown ------------------ */

        /* Case: filtered person list, delete existing group but not in filtered person list -> deleted */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        expectedModel = getModel();
        deletedGroup = FRIENDS;
        removeGroup(expectedModel, FRIENDS);
        command = DeleteGroupCommand.COMMAND_WORD + " " + FRIENDS.tagName;
        expectedResultMessage = String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: filtered person list, delete non-existing group in address book -> rejected */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Group invalidGroup = BUDDIES;
        command = DeleteGroupCommand.COMMAND_WORD + " " + invalidGroup.tagName;
        assertCommandFailure(command, MESSAGE_GROUP_NOT_FOUND);

        /* ----------------- Performing delete group operation while a person card is selected -------------------- */

        /* Case: delete group existing in the selected person -> person list panel still selects the person */
        showAllPersons();
        expectedModel = getModel();
        deletedGroup = NEIGHBOURS;
        Index selectedIndex = Index.fromOneBased(5);
        Index expectedIndex = selectedIndex;
        selectPerson(selectedIndex);
        command = DeleteGroupCommand.COMMAND_WORD + " " + NEIGHBOURS.tagName;
        removeGroup(expectedModel, NEIGHBOURS);
        expectedResultMessage = String.format(MESSAGE_DELETE_GROUP_SUCCESS, deletedGroup);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------- Performing invalid delete preference operation ------------------------------ */

        /* Case: invalid arguments (non-alphanumeric arguments) -> rejected */
        assertCommandFailure(DeleteGroupCommand.COMMAND_WORD + " fr!end3",
                MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteGroupCommand.COMMAND_WORD + " friends twitter",
                MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("GrouPDeletE neighbours", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Group} in {@code model}'s address book.
     */
    private void removeGroup(Model model, Group toDelete) {
        try {
            model.deleteGroup(toDelete);
        } catch (GroupNotFoundException gnfe) {
            throw new AssertionError("Group should exist in address book.");
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
     * @see DeleteGroupCommandSystemTest#assertCommandSuccess(String, Model, String)
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


