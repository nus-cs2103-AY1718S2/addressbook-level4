//@@author amad-person
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_DATE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_INFORMATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRICE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_INFORMATION_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalOrders.COMICBOOK;
import static seedu.address.testutil.TypicalOrders.COMPUTER;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddOrderCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.order.DeliveryDate;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderInformation;
import seedu.address.model.order.Price;
import seedu.address.model.order.Quantity;
import seedu.address.model.order.UniqueOrderList;
import seedu.address.model.person.Person;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.OrderUtil;

public class AddOrderCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void addOrder() throws Exception {
        Model model = getModel();

        /* --------------------- Perform addOrder operations on the shown unfiltered list -------------------------- */

        Index index = INDEX_FIRST_PERSON;
        Order toAdd = COMPUTER;

        /* Case: add an order to a non-empty address book,
         * command with leading spaces and trailing spaces -> added
         */
        String command = "   " + AddOrderCommand.COMMAND_WORD + " " + index.getOneBased() + " "
                + ORDER_INFORMATION_DESC_COMPUTER + "   " + PRICE_DESC_COMPUTER + "   " + QUANTITY_DESC_COMPUTER + "  "
                + DELIVERY_DATE_DESC_COMPUTER + "   ";
        assertCommandSuccess(command, index, toAdd);

        /* Case: undo adding Books to the list -> Books deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Books to the list -> Books added again */
        command = RedoCommand.COMMAND_WORD;
        model.addOrderToOrderList(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add order with all fields same as another order in address book except order information -> added */
        toAdd = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_CHOC)
                .withPrice(VALID_PRICE_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
                .build();
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_CHOC + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandSuccess(command, index, toAdd);

        /* Case: add order with all fields same as another order in address book except price -> added */
        toAdd = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_CHOC)
                .withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
                .build();
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_CHOC
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandSuccess(command, index, toAdd);

        /* Case: add order with all fields same as another order in address book except quantity -> added */
        toAdd = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_COMPUTER)
                .withQuantity(VALID_QUANTITY_CHOC)
                .withDeliveryDate(VALID_DELIVERY_DATE_COMPUTER)
                .build();
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_CHOC + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandSuccess(command, index, toAdd);

        /* Case: add order with all fields same as another order in address book except delivery date -> added */
        toAdd = new OrderBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER)
                .withDeliveryDate(VALID_DELIVERY_DATE_CHOC)
                .build();
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_CHOC;
        assertCommandSuccess(command, index, toAdd);

        /* Case: add an order command with parameters in random order -> added */
        toAdd = COMICBOOK;
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + " d/01-01-2018" + " i/Comic Book" + " q/1" + " pr/17.99";
        assertCommandSuccess(command, index, toAdd);

        /* --------------------- Perform addOrder operations on the shown filtered list -------------------------- */

        /* --------------------------------- Perform invalid addOrder operations --------------------------------- */

        /* Case: missing person index -> rejected */
        command = AddOrderCommand.COMMAND_WORD + ORDER_INFORMATION_DESC_COMPUTER
                + PRICE_DESC_COMPUTER + QUANTITY_DESC_COMPUTER
                + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: missing order information -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + PRICE_DESC_COMPUTER + QUANTITY_DESC_COMPUTER
                + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: missing price -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased() + ORDER_INFORMATION_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: missing quantity -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased() + ORDER_INFORMATION_DESC_COMPUTER
                + PRICE_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: missing delivery date -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased() + ORDER_INFORMATION_DESC_COMPUTER
                + PRICE_DESC_COMPUTER + QUANTITY_DESC_COMPUTER;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "ordersadds " + " " + index.getOneBased() + OrderUtil.getOrderDetails(toAdd);
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid order information -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + INVALID_ORDER_INFORMATION_DESC + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, OrderInformation.MESSAGE_ORDER_INFORMATION_CONSTRAINTS);

        /* Case: invalid price -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + INVALID_PRICE_DESC
                + QUANTITY_DESC_COMPUTER + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, Price.MESSAGE_PRICE_CONSTRAINTS);

        /* Case: invalid quantity -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + INVALID_QUANTITY_DESC + DELIVERY_DATE_DESC_COMPUTER;
        assertCommandFailure(command, Quantity.MESSAGE_QUANTITY_CONSTRAINTS);

        /* Case: invalid delivery date -> rejected */
        command = AddOrderCommand.COMMAND_WORD + " " + index.getOneBased()
                + ORDER_INFORMATION_DESC_COMPUTER + PRICE_DESC_COMPUTER
                + QUANTITY_DESC_COMPUTER + INVALID_DELIVERY_DATE_DESC;
        assertCommandFailure(command, DeliveryDate.MESSAGE_DELIVERY_DATE_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddOrderCommand} that adds {@code toAdd} to the model and asserts that the:<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code AddOrderCommand} with details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Index index, Order toAdd) {
        assertCommandSuccess(OrderUtil.getAddOrderCommand(index.getZeroBased(), toAdd), index, toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Index, Order)}. Executes {@code command}
     * instead.
     * @see AddOrderCommandSystemTest#assertCommandSuccess(Index, Order)
     */
    private void assertCommandSuccess(String command, Index index, Order toAdd) {
        Model expectedModel = getModel();
        Person person = expectedModel.getAddressBook().getPersonList().get(index.getZeroBased());

        try {
            expectedModel.addOrderToOrderList(toAdd);
        } catch (UniqueOrderList.DuplicateOrderException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(
                AddOrderCommand.MESSAGE_ADD_ORDER_SUCCESS, person.getName(), toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Order)} except asserts that
     * the:<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see AddOrderCommandSystemTest#assertCommandSuccess(String, Index, Order)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the:<br>
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
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
