//@@author amad-person
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_INFORMATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_ORDER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.order.exceptions.OrderNotFoundException;
import seedu.address.testutil.OrderBuilder;

public class EditOrderCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_THIRD_ORDER;
        String command = " " + EditOrderCommand.COMMAND_WORD + "  " + index.getOneBased() + "    "
                + ORDER_INFORMATION_DESC_COMPUTER + " " + PRICE_DESC_COMPUTER + "  " + QUANTITY_DESC_COMPUTER
                + "    " + DELIVERY_DATE_DESC_COMPUTER;

        Order editedOrder = new OrderBuilder().withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_COMPUTER).withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER).build();

        assertCommandSuccess(command, index, editedOrder);

        /* Case: undo editing the order in the list -> order restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateOrder(
                getModel().getFilteredOrderList().get(INDEX_THIRD_ORDER.getZeroBased()), editedOrder);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit some fields -> edited */
        index = INDEX_THIRD_ORDER;
        command = EditOrderCommand.COMMAND_WORD + " " + index.getOneBased() + QUANTITY_DESC_COMPUTER;
        Order orderToEdit = getModel().getFilteredOrderList().get(index.getZeroBased());
        editedOrder = new OrderBuilder(orderToEdit).withQuantity(VALID_QUANTITY_COMPUTER).build();
        assertCommandSuccess(command, index, editedOrder);


        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " 0" + ORDER_INFORMATION_DESC_BOOKS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " -1" + ORDER_INFORMATION_DESC_BOOKS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredOrderList().size() + 1;
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + invalidIndex
                        + ORDER_INFORMATION_DESC_BOOKS,
                Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + ORDER_INFORMATION_DESC_BOOKS,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased(),
                EditOrderCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid order information -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased()
                        + INVALID_ORDER_INFORMATION_DESC,
                OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased()
                        + INVALID_PRICE_DESC,
                Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased()
                        + INVALID_QUANTITY_DESC,
                Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid delivery date -> rejected */
        assertCommandFailure(EditOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased()
                        + INVALID_DELIVERY_DATE_DESC,
                DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * selected card remains unchanged.
     * @see EditOrderCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Person, Index)} except that
     * the selected card remains unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditOrderCommandSystemTest#assertCommandSuccess(String, Index, Order, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Order editedOrder) {
        assertCommandSuccess(command, toEdit, editedOrder, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditOrderCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the order at index {@code toEdit} being
     * updated to values specified {@code editedOrder}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditOrderCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Order editedOrder,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateOrder(
                    expectedModel.getFilteredOrderList().get(toEdit.getZeroBased()), editedOrder);
            expectedModel.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        } catch (UniqueOrderList.DuplicateOrderException | OrderNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedOrder is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder), expectedSelectedCardIndex);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the selected card updates accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
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
     * 4. Asserts that the selected card and status bar remain unchanged.<br>
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
