//@@author amad-person
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ChangeOrderStatusCommand.MESSAGE_INVALID_ORDER_STATUS;
import static seedu.address.logic.commands.ChangeOrderStatusCommand.MESSAGE_ORDER_STATUS_CHANGED_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_DONE;
import static seedu.address.model.order.OrderStatus.ORDER_STATUS_ONGOING;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.address.testutil.TypicalOrders.getTypicalAddressBookWithOrders;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

public class ChangeOrderStatusCommandTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithOrders(), new CalendarManager(), new UserPrefs());
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeOrderStatusCommand(null, ORDER_STATUS_ONGOING);
    }

    @Test
    public void constructor_nullOrderStatus_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ChangeOrderStatusCommand(INDEX_FIRST_ORDER, null);
    }

    @Test
    public void execute_orderStatusAccepted_statusChanged() throws Exception {
        Order orderToChangeStatus = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        ChangeOrderStatusCommand changeOrderStatusToDoneCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);

        String expectedMessage = String.format(MESSAGE_ORDER_STATUS_CHANGED_SUCCESS,
                INDEX_FIRST_ORDER.getOneBased(), ORDER_STATUS_DONE);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new CalendarManager(),
                new UserPrefs());

        expectedModel.updateOrderStatus(orderToChangeStatus, ORDER_STATUS_DONE);
        assertCommandSuccess(changeOrderStatusToDoneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidOrderIndex_throwsCommandException() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(outOfBoundsIndex,
                ORDER_STATUS_DONE, model);

        String expectedMessage = Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;

        assertCommandFailure(changeOrderStatusCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidOrderStatus_throwsCommandException() {
        String invalidOrderStatus = "fulfill3ed";
        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                invalidOrderStatus, model);

        String expectedMessage = String.format(MESSAGE_INVALID_ORDER_STATUS, invalidOrderStatus);

        assertCommandFailure(changeOrderStatusCommand, model, expectedMessage);
    }

    @Test
    public void executeUndoRedo_validOrderIndexAndOrderStatus_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Order orderToChangeStatus = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());

        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new CalendarManager(), new UserPrefs());

        // execute changeOrderStatusCommand -> order status of order updated
        changeOrderStatusCommand.execute();
        undoRedoStack.push(changeOrderStatusCommand);

        // undo -> order status of order reverted back to original
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same order has its order status updated again
        expectedModel.updateOrderStatus(orderToChangeStatus, ORDER_STATUS_DONE);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidOrderIndex_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);

        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(outOfBoundsIndex,
                ORDER_STATUS_DONE, model);

        String expectedMessage = Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;

        // execution failed -> changeOrderStatusCommand not pushed into undoRedoStack
        assertCommandFailure(changeOrderStatusCommand, model, expectedMessage);

        // no commands in undoRedoStack -> undo and redo both fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void executeUndoRedo_invalidOrderStatus_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);

        String invalidOrderStatus = "fulfill3d";

        ChangeOrderStatusCommand changeOrderStatusCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                invalidOrderStatus, model);

        String expectedMessage = String.format(MESSAGE_INVALID_ORDER_STATUS, invalidOrderStatus);

        // execution failed -> changeOrderStatusCommand not pushed into undoRedoStack
        assertCommandFailure(changeOrderStatusCommand, model, expectedMessage);

        // no commands in undoRedoStack -> undo and redo both fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        ChangeOrderStatusCommand firstCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);
        ChangeOrderStatusCommand firstCommandCopy = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);

        assertEquals(firstCommand, firstCommandCopy);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        ChangeOrderStatusCommand firstCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);

        assertTrue(firstCommand.equals(firstCommand));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        ChangeOrderStatusCommand firstCommand = getChangeOrderStatusCommand(INDEX_FIRST_ORDER,
                ORDER_STATUS_DONE, model);

        // null -> returns false
        assertNotEquals(null, firstCommand);

        // different types -> returns false
        assertNotEquals(1, firstCommand);

        // different index -> returns false
        assertNotEquals(new ChangeOrderStatusCommand(INDEX_SECOND_ORDER, ORDER_STATUS_DONE), firstCommand);

        // different order status -> returns false
        assertNotEquals(new ChangeOrderStatusCommand(INDEX_FIRST_ORDER, ORDER_STATUS_ONGOING), firstCommand);
    }

    /**
     * Generates a new ChangeOrderStatusCommand with the given index and new order status.
     */
    private ChangeOrderStatusCommand getChangeOrderStatusCommand(Index index, String orderStatus, Model model) {
        ChangeOrderStatusCommand command = new ChangeOrderStatusCommand(index, orderStatus);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
