package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastOrderIndex;
import static seedu.address.testutil.TestUtil.getMidOrderIndex;
import static seedu.address.testutil.TestUtil.getOrder;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.exceptions.OrderNotFoundException;

public class DeleteOrderCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteOrderCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first order in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     " + DeleteOrderCommand.COMMAND_WORD + "      "
                + INDEX_FIRST_ORDER.getOneBased() + "       ";
        Order deletedOrder = removeOrder(expectedModel, INDEX_FIRST_ORDER);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ORDER_SUCCESS, deletedOrder);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last order in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastOrderIndex = getLastOrderIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastOrderIndex);

        /* Case: undo deleting the last order in the list -> last order restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last order in the list -> last order deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeOrder(modelBeforeDeletingLast, lastOrderIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle order in the list -> deleted */
        Index middlePersonIndex = getMidOrderIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* --------------------------------- Performing invalid delete operations ----------------------------------- */

        /* Case: invalid index (0) -> rejected */
        command = DeleteOrderCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteOrderCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getOrderList().size() + 1);
        command = DeleteOrderCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteOrderCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteOrderCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_ORDER_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Order} at the specified {@code index} in {@code model}'s address book.
     * @return the removed order
     */
    private Order removeOrder(Model model, Index index) {
        Order targetOrder = getOrder(model, index);
        try {
            model.deleteOrder(targetOrder);
        } catch (OrderNotFoundException onfe) {
            throw new AssertionError("targetOrder is retrieved from model.");
        }
        return targetOrder;
    }

    /**
     * Deletes the order at {@code toDelete} by creating a default {@code DeleteOrderCommand} using {@code toDelete}
     * and performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteOrderCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Order deletedOrder = removeOrder(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_ORDER_SUCCESS, deletedOrder);

        assertCommandSuccess(
                DeleteOrderCommand.COMMAND_WORD + " "
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
     * @see DeleteOrderCommandSystemTest#assertCommandSuccess(String, Model, String)
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
