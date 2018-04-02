package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONTENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TODOS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TODO;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditToDoCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.Content;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;
import seedu.address.testutil.ToDoBuilder;
import seedu.address.testutil.ToDoUtil;

public class EditToDoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void editToDo() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit to-do operation while an unfiltered list is being shown ---------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_TODO;
        String command = " " + EditToDoCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + CONTENT_DESC + "  ";
        ToDo editedToDo = new ToDoBuilder().withContent(VALID_CONTENT).build();
        assertCommandSuccess(command, index, editedToDo);

        /* Case: undo editing the last to-do in the list -> last to-do restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last to-do in the list -> last to-do edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateToDo(
                getModel().getFilteredToDoList().get(INDEX_FIRST_TODO.getZeroBased()), editedToDo);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a to-do with new content same as existing content -> edited */
        command = EditToDoCommand.COMMAND_WORD + " " + index.getOneBased() + CONTENT_DESC;
        ToDo validToDo = new ToDoBuilder().withContent(VALID_CONTENT).build();
        assertCommandSuccess(command, index, validToDo);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " 0" + CONTENT_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " -1" + CONTENT_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredToDoList().size() + 1;
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " " + invalidIndex + CONTENT_DESC,
                Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + CONTENT_DESC,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditToDoCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " " + INDEX_FIRST_TODO.getOneBased(),
                EditToDoCommand.MESSAGE_NOT_EDITED_TODO);

        /* Case: invalid content -> rejected */
        assertCommandFailure(EditToDoCommand.COMMAND_WORD + " " + INDEX_FIRST_TODO.getOneBased()
                + INVALID_CONTENT_DESC, Content.MESSAGE_CONTENT_CONSTRAINTS);

        /* Case: edit a to-do with new values same as another to-do's values -> rejected */
        ToDo anotherToDo = new ToDoBuilder().withContent("Another thing to do").build();
        executeCommand(ToDoUtil.getAddToDoCommand(anotherToDo));
        assertTrue(getModel().getAddressBook().getToDoList().contains(anotherToDo));
        index = INDEX_FIRST_TODO;
        assertFalse(getModel().getFilteredToDoList().get(index.getZeroBased()).equals(anotherToDo));
        command = EditToDoCommand.COMMAND_WORD + " " + index.getOneBased() + " c/" + "Another thing to do";
        assertCommandFailure(command, EditToDoCommand.MESSAGE_DUPLICATE_TODO);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ToDo, Index)} except that
     * the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @see EditToDoCommandSystemTest#assertCommandSuccess(String, Index, ToDo, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ToDo editedToDo) {
        assertCommandSuccess(command, toEdit, editedToDo, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditToDoCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the to-do at index {@code toEdit} being
     * updated to values specified {@code editedToDo}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see EditToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ToDo editedToDo,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateToDo(
                    expectedModel.getFilteredToDoList().get(toEdit.getZeroBased()), editedToDo);
            expectedModel.updateFilteredToDoList(PREDICATE_SHOW_ALL_TODOS);
        } catch (DuplicateToDoException | ToDoNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedToDo is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditToDoCommand.MESSAGE_EDIT_TODO_SUCCESS, editedToDo), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see EditToDoCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
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
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
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
