//@@author jas5469
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_DID_YOU_MEAN;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_GROUP_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteGroupCommand.MESSAGE_DELETE_GROUP_SUCCESS;
import static seedu.address.testutil.TestUtil.getGroup;
import static seedu.address.testutil.TestUtil.getLastIndexGroup;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_GROUP;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.Information;
import seedu.address.model.group.exceptions.GroupNotFoundException;

public class DeleteGroupCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);

    @Test
    public void deleteGroup() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */


        /* Case: delete the first group in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "  " + DeleteGroupCommand.COMMAND_WORD + "  " + "Group A" + "  ";
        Group deletedGroup = removeGroup(expectedModel, INDEX_FIRST_GROUP);
        String expectedResultMessage = String.format(MESSAGE_DELETE_GROUP_SUCCESS,
                deletedGroup.getInformation().toString());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last group in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastGroupIndex = getLastIndexGroup(modelBeforeDeletingLast);
        assertCommandSuccess(lastGroupIndex);

        /* Case: undo deleting the last group in the list -> last group restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last group in the list -> last group deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeGroup(modelBeforeDeletingLast, lastGroupIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);


        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid groupName (!) -> rejected */
        command = DeleteGroupCommand.COMMAND_WORD + " !";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT);

        /* Case: group Not in Fastis -> rejected */
        Information information = new Information("Group Z");
        command = DeleteGroupCommand.COMMAND_WORD + " " + information.toString();
        assertCommandFailure(command, MESSAGE_INVALID_GROUP_NAME);

        /* Case: invalid arguments (non - alphabets or numbers) -> rejected */
        assertCommandFailure(
                DeleteGroupCommand.COMMAND_WORD + " #@!@", MESSAGE_INVALID_DELETE_GROUP_COMMAND_FORMAT);


        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEGroup Group A",
                MESSAGE_UNKNOWN_COMMAND + MESSAGE_DID_YOU_MEAN + DeleteGroupCommand.COMMAND_WORD);
    }

    /**
     * Removes the {@code Group} at the specified {@code index} in {@code model}'s address book.
     * @return the removed group
     */
    private Group removeGroup(Model model, Index index) {
        Group targetGroup = getGroup(model, index);
        try {
            model.deleteGroup(targetGroup);
        } catch (GroupNotFoundException tnfe) {
            throw new AssertionError("targetGroup is retrieved from model.");
        }

        return targetGroup;
    }

    /**
     * Deletes the group at {@code toDelete} by creating a default {@code DeleteGroupCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteGroupCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();

        Group deletedGroup = removeGroup(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_GROUP_SUCCESS,
                deletedGroup.getInformation().toString());
        assertCommandSuccess(DeleteGroupCommand.COMMAND_WORD + " "
                        + "Group H", expectedModel, expectedResultMessage);
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
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
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

        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
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
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
