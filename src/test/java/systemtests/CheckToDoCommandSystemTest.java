//@@author nhatquang3112
package systemtests;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CheckToDoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

public class CheckToDoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void checkToDo() throws Exception {
        Model model = getModel();

        /* ----------------- Performing check operation while an unfiltered list is being shown --------------------- */

        /* Case: check the first to-do in the address book, command with leading space and trailing space and multiple
        spaces between each field
         * -> checked
         */
        Index index = INDEX_FIRST_TODO;
        ToDo toDoToCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        String command = " " + CheckToDoCommand.COMMAND_WORD + " " + " " + index.getOneBased() + " ";
        ToDo checkedToDo = new ToDo(toDoToCheck.getContent(), new Status("done"));

        assertCommandSuccess(command, index, checkedToDo);

        /* Case: undo checking the last to-do in the list -> last to-do restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo checking the last to-do in the list -> last to-do checked again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateToDo(
                getModel().getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased()), checkedToDo);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* --------------------------------- Performing invalid checkToDo operation --------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(CheckToDoCommand.COMMAND_WORD + " 0",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CheckToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(CheckToDoCommand.COMMAND_WORD + " -1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CheckToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredToDoList().size() + 1;
        assertCommandFailure(CheckToDoCommand.COMMAND_WORD + " " + invalidIndex,
                Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(CheckToDoCommand.COMMAND_WORD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CheckToDoCommand.MESSAGE_USAGE));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ToDo, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toCheck the index of the current model's filtered list
     * @see CheckToDoCommandSystemTest#assertCommandSuccess(String, Index, ToDo, Index)
     */
    private void assertCommandSuccess(String command, Index toCheck, ToDo checkedToDo) {
        assertCommandSuccess(command, toCheck, checkedToDo, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display node displays the success message of executing {@code CheckToDoCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the to-do at index {@code toCheck} being
     * updated to values specified {@code checkedToDo}.<br>
     *
     * @param toCheck the index of the current model's filtered list.
     * @see CheckToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toCheck, ToDo checkedToDo,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateToDo(
                    expectedModel.getFilteredToDoList().get(toCheck.getZeroBased()), checkedToDo);
            expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        } catch (DuplicateToDoException | ToDoNotFoundException e) {
            throw new IllegalArgumentException(
                    "checkedToDo is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(CheckToDoCommand.MESSAGE_CHECK_TODO_SUCCESS, checkedToDo), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see CheckToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
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
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
