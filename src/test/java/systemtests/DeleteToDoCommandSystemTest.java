//@@author nhatquang3112
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteToDoCommand.MESSAGE_DELETE_TODO_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndexToDo;
import static seedu.address.testutil.TestUtil.getToDo;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteToDoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

public class DeleteToDoCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteToDoCommand.MESSAGE_USAGE);

    @Test
    public void deleteToDo() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first to-do in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteToDoCommand.COMMAND_WORD + "      " + INDEX_FIRST_TODO.getOneBased() + "   ";
        ToDo deletedToDo = removeToDo(expectedModel, INDEX_FIRST_TODO);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TODO_SUCCESS, deletedToDo);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last to-do in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastToDoIndex = getLastIndexToDo(modelBeforeDeletingLast);
        assertCommandSuccess(lastToDoIndex);

        /* Case: undo deleting the last to-do in the list -> last to-do restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last to-do in the list -> last to-do deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeToDo(modelBeforeDeletingLast, lastToDoIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);


        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteToDoCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteToDoCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getToDoList().size() + 1);
        command = DeleteToDoCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(
                DeleteToDoCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(
                DeleteToDoCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_TODO_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETEtOdO 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code ToDo} at the specified {@code index} in {@code model}'s address book.
     * @return the removed to-do
     */
    private ToDo removeToDo(Model model, Index index) {
        ToDo targetToDo = getToDo(model, index);
        try {
            model.deleteToDo(targetToDo);
        } catch (ToDoNotFoundException tnfe) {
            throw new AssertionError("targetToDo is retrieved from model.");
        }
        return targetToDo;
    }

    /**
     * Deletes the to-do at {@code toDelete} by creating a default {@code DeleteToDoCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteToDoCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        ToDo deletedToDo = removeToDo(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TODO_SUCCESS, deletedToDo);

        assertCommandSuccess(
                DeleteToDoCommand.COMMAND_WORD + " "
                        + toDelete.getOneBased(), expectedModel, expectedResultMessage);
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
