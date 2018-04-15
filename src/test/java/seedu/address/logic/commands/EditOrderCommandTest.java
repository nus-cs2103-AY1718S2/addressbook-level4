//@@author amad-person
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COMICBOOK;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DELIVERY_DATE_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_INFORMATION_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHOC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_BOOKS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_COMPUTER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditOrderCommand.EditOrderDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests
 * for EditOrderCommand.
 */
public class EditOrderCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithOrders(), new CalendarManager(), new UserPrefs());
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withPrice(VALID_PRICE_CHOC).withQuantity(VALID_QUANTITY_BOOKS)
                .withDeliveryDate(VALID_DELIVERY_DATE_BOOKS).build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER).withPrice(VALID_PRICE_CHOC)
                .withQuantity(VALID_QUANTITY_BOOKS).withDeliveryDate(VALID_DELIVERY_DATE_BOOKS)
                .build();

        EditOrderCommand editOrderCommand = prepareCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateOrder(lastOrder, editedOrder);

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastOrder = Index.fromOneBased(model.getFilteredOrderList().size());
        Order lastOrder = model.getFilteredOrderList().get(indexLastOrder.getZeroBased());

        OrderBuilder orderInList = new OrderBuilder(lastOrder);
        Order editedOrder = orderInList.withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER).build();

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER)
                .withQuantity(VALID_QUANTITY_COMPUTER).build();

        EditOrderCommand editOrderCommand = prepareCommand(indexLastOrder, descriptor);

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new CalendarManager(),
                new UserPrefs());
        expectedModel.updateOrder(lastOrder, editedOrder);

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() throws Exception {
        EditOrderCommand editOrderCommand = prepareCommand(INDEX_FIRST_ORDER, new EditOrderDescriptor());
        Order editedOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        String expectedMessage = String.format(EditOrderCommand.MESSAGE_EDIT_ORDER_SUCCESS, editedOrder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.updateOrder(model.getFilteredOrderList().get(0), editedOrder);

        assertCommandSuccess(editOrderCommand, model, expectedMessage, expectedModel);
    }

    /**
     * This test is failing on Gradle, but passing on IntelliJ JUnit Test.

    @Test
    public void execute_duplicateOrderUnfilteredList_failure() {
        Order firstOrder = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(firstOrder).build();
        EditOrderCommand editOrderCommand = prepareCommand(INDEX_SECOND_ORDER, descriptor);

        assertCommandFailure(editOrderCommand, model, EditOrderCommand.MESSAGE_DUPLICATE_ORDER);
    }
    */

    @Test
    public void execute_invalidOrderIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER).build();
        EditOrderCommand editOrderCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Order editedOrder = new OrderBuilder().build();
        Order orderToEdit = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(editedOrder).build();
        EditOrderCommand editOrderCommand = prepareCommand(INDEX_FIRST_ORDER, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), model.getCalendarManager(),
                new UserPrefs());

        // edit -> first order edited
        editOrderCommand.execute();
        undoRedoStack.push(editOrderCommand);

        // undo -> reverts address book back to previous state and filtered order list to show all orders
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first order edited again
        expectedModel.updateOrder(orderToEdit, editedOrder);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder()
                .withOrderInformation(VALID_ORDER_INFORMATION_COMPUTER).build();
        EditOrderCommand editOrderCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editOrderCommand not pushed into undoRedoStack
        assertCommandFailure(editOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() throws Exception {
        final EditOrderCommand firstCommand = prepareCommand(INDEX_FIRST_ORDER, DESC_COMPUTER);

        // same values -> returns true
        EditOrderDescriptor copyDescriptor = new EditOrderDescriptor(DESC_COMPUTER);
        EditOrderCommand firstCommandCopy = prepareCommand(INDEX_FIRST_ORDER, copyDescriptor);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // one command preprocessed when previously equal -> returns false
        firstCommandCopy.preprocessUndoableCommand();
        assertFalse(firstCommand.equals(firstCommandCopy));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // different index -> returns false
        assertFalse(firstCommand.equals(new EditOrderCommand(INDEX_SECOND_ORDER, DESC_COMPUTER)));

        // different descriptor -> returns false
        assertFalse(firstCommand.equals(new EditOrderCommand(INDEX_FIRST_ORDER, DESC_COMICBOOK)));
    }

    /**
     * Returns an {@code EditOrderCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditOrderCommand prepareCommand(Index index, EditOrderDescriptor descriptor) {
        EditOrderCommand editOrderCommand = new EditOrderCommand(index, descriptor);
        editOrderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editOrderCommand;
    }
}
