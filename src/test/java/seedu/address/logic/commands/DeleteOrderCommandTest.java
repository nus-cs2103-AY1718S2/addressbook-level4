//@@author amad-person
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import seedu.address.model.CalendarManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteOrderCommand}.
 */
public class DeleteOrderCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithOrders(), new CalendarManager(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = prepareCommand(INDEX_FIRST_ORDER);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(),
                new UserPrefs());
        expectedModel.deleteOrder(orderToDelete);

        assertCommandSuccess(deleteOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Order orderToDelete = model.getFilteredOrderList().get(INDEX_FIRST_ORDER.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = prepareCommand(INDEX_FIRST_ORDER);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getCalendarManager(), new UserPrefs());

        // delete -> first order deleted
        deleteOrderCommand.execute();
        undoRedoStack.push(deleteOrderCommand);

        // undo -> reverts address book back to previous state and order list to show all orders
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first order deleted again
        expectedModel.deleteOrder(orderToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredOrderList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteOrderCommand not pushed into undoRedoStack
        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        DeleteOrderCommand deleteFirstOrderCommand = prepareCommand(INDEX_FIRST_ORDER);
        DeleteOrderCommand deleteSecondOrderCommand = prepareCommand(INDEX_SECOND_ORDER);

        // same object -> returns true
        assertTrue(deleteFirstOrderCommand.equals(deleteFirstOrderCommand));

        // same values -> returns true
        DeleteOrderCommand deleteFirstOrderCommandCopy = prepareCommand(INDEX_FIRST_ORDER);
        assertTrue(deleteFirstOrderCommand.equals(deleteFirstOrderCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstOrderCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstOrderCommand.equals(deleteFirstOrderCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstOrderCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstOrderCommand.equals(null));

        // different order -> returns false
        assertFalse(deleteFirstOrderCommand.equals(deleteSecondOrderCommand));
    }

    /**
     * Returns a {@code DeleteOrderCommand} with the parameter {@code index}.
     */
    private DeleteOrderCommand prepareCommand(Index index) {
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(index);
        deleteOrderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteOrderCommand;
    }
}
