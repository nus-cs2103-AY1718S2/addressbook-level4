package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.CONTENT_E;
import static seedu.address.testutil.TypicalToDos.TODO_E;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddToDoCommand;
import seedu.address.model.Model;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.testutil.ToDoUtil;

public class AddToDoCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void addToDo() throws Exception {
        /* ------------------------ Perform addToDo operations on the shown unfiltered list ---------------------- */

        /* Case: add a to-do to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ToDo toAdd = TODO_E;
        String command = "   " + AddToDoCommand.COMMAND_WORD + "  " + CONTENT_E + " ";
        assertCommandSuccess(command, toAdd);

        /* Case: invalid keyword -> rejected */
        command = "addsToDo " + ToDoUtil.getToDoDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: add a duplicate to-do -> rejected */
        command = ToDoUtil.getAddToDoCommand(TODO_E);
        assertCommandFailure(command, AddToDoCommand.MESSAGE_DUPLICATE_TODO);
    }

    /**
     * Executes the {@code AddToDoCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddToDoCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ToDo toAdd) {
        assertCommandSuccess(ToDoUtil.getAddToDoCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ToDo)}. Executes {@code command}
     * instead.
     * @see AddToDoCommandSystemTest#assertCommandSuccess(ToDo)
     */
    private void assertCommandSuccess(String command, ToDo toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addToDo(toAdd);
        } catch (DuplicateToDoException dpt) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddToDoCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ToDo)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code ToDoListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddToDoCommandSystemTest#assertCommandSuccess(String, ToDo)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxAndResultDisplayShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
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
