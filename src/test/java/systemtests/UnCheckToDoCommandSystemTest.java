//@@author nhatquang3112
package systemtests;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UnCheckToDoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Status;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

public class UnCheckToDoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void unCheckToDo() throws Exception {
        Model model = getModel();

        /* ----------------- Performing uncheck operation while an unfiltered list is being shown --------------------*/

        /* Case: uncheck the first to-do in the address book, command with leading space and trailing space and multiple
        spaces between each field
         * -> unchecked
         */
        Index index = INDEX_FIRST_TODO;
        ToDo toDoToUnCheck = model.getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased());
        String command = " " + UnCheckToDoCommand.COMMAND_WORD + " " + " " + index.getOneBased() + " ";
        ToDo unCheckedToDo = new ToDo(toDoToUnCheck.getContent(), new Status("undone"));

        assertCommandSuccess(command, index, unCheckedToDo);

        /* Case: undo unchecking the last to-do in the list -> last to-do restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo unchecking the last to-do in the list -> last to-do unchecked again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateToDo(
                getModel().getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased()), unCheckedToDo);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* --------------------------------- Performing invalid UnCheckToDo operation --------------------------------*/

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(UnCheckToDoCommand.COMMAND_WORD + " 0",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(UnCheckToDoCommand.COMMAND_WORD + " -1",
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredToDoList().size() + 1;
        assertCommandFailure(UnCheckToDoCommand.COMMAND_WORD + " " + invalidIndex,
                Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(UnCheckToDoCommand.COMMAND_WORD,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, UnCheckToDoCommand.MESSAGE_USAGE));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ToDo, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toUnCheck the index of the current model's filtered list
     * @see UnCheckToDoCommandSystemTest#assertCommandSuccess(String, Index, ToDo, Index)
     */
    private void assertCommandSuccess(String command, Index toUnCheck, ToDo unCheckedToDo) {
        assertCommandSuccess(command, toUnCheck, unCheckedToDo, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display node displays the success message of executing {@code UnCheckToDoCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the to-do at index {@code toUnCheck} being
     * updated to values specified {@code unCheckedToDo}.<br>
     *
     * @param toUnCheck the index of the current model's filtered list.
     * @see UnCheckToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toUnCheck, ToDo unCheckedToDo,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateToDo(
                    expectedModel.getFilteredToDoList().get(toUnCheck.getZeroBased()), unCheckedToDo);
            expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        } catch (DuplicateToDoException | ToDoNotFoundException e) {
            throw new IllegalArgumentException(
                    "unCheckedToDo is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(
                        UnCheckToDoCommand.MESSAGE_UNCHECK_TODO_SUCCESS, unCheckedToDo), expectedSelectedCardIndex);
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
