//@@author LeonidAgarth
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_BROWN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR_RED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeTagColorCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;

public class ChangeTagColorCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void changeTagColor() throws Exception {
        Model model = getModel();

        /* ---------- Performing change tag color operation while an unfiltered list is being shown ------------- */

        /* Case: change tag color fields, command with leading spaces, trailing spaces and multiple spaces
         * between each field -> changed
         */
        String command = " " + ChangeTagColorCommand.COMMAND_WORD + "  " + VALID_TAG_FRIEND
                + "  " + VALID_TAG_COLOR_BROWN + " ";
        Tag changedTag = new Tag(VALID_TAG_FRIEND, VALID_TAG_COLOR_BROWN);
        assertCommandSuccess(command, changedTag);

        /* Case: undo changeTagColoring the last tag in the list -> last tag restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo changeTagColoring the last tag in the list -> last tag changeTagColored again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateTag(new Tag(VALID_TAG_FRIEND), changedTag);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: changeTagColor a tag with new values same as existing values -> changeTagColored */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + " " + VALID_TAG_COLOR_BROWN;
        assertCommandSuccess(command, changedTag);

        /* Case: tag specified not in list -> rejected
         */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_COLOR_RED + " " + VALID_TAG_COLOR_BROWN;
        assertCommandFailure(command, ChangeTagColorCommand.MESSAGE_TAG_NOT_IN_LIST);

        /* Case: color specified is not supported by application -> rejected
         */
        command = ChangeTagColorCommand.COMMAND_WORD + " " + VALID_TAG_FRIEND + " " + INVALID_TAG_COLOR;
        assertCommandFailure(command, Tag.MESSAGE_TAG_COLOR_CONSTRAINTS);
    }

    /**
     * Performs the verification: <br>
     * 1. Asserts that result display node displays the success message of executing {@code ChangeTagColorCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the tag being updated to values
     * specified in {@code changeTagColoredTag}.<br>
     */
    private void assertCommandSuccess(String command, Tag changedTag) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateTag(new Tag(VALID_TAG_FRIEND), changedTag);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (TagNotFoundException tnfe) {
            throw new IllegalArgumentException("Tag isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(ChangeTagColorCommand.MESSAGE_EDIT_TAG_SUCCESS, changedTag.name, changedTag.color));
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays an empty string.<br>
     * 2. Asserts that the result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command node has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command node displays {@code command}.<br>
     * 2. Asserts that result display node displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command node has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
