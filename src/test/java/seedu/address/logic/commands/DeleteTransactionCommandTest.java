package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.testutil.AddressBookBuilder.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TRANSACTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TRANSACTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SEVENTH_TRANSACTION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SIXTH_TRANSACTION;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.transaction.Transaction;
//@@author ongkc
public class DeleteTransactionCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(
                INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);
        transactionToDelete.setTransactionType("paydebt");
        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
        transactionToDelete.setTransactionType("payment");
    }
    @Test
    public void execute_validIndexUnfilteredListPaydebt_success() throws Exception {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(
                INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_validIndexUnfilteredListUnits_success() throws Exception {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(
                INDEX_SIXTH_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_SIXTH_TRANSACTION);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_validIndexUnfilteredListPercentage_success() throws Exception {
        Transaction transactionToDelete = model.getFilteredTransactionList().get(
                INDEX_SEVENTH_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_SEVENTH_TRANSACTION);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {

        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);

        String expectedMessage = String.format(DeleteTransactionCommand.MESSAGE_DELETE_TRANSACTION_SUCCESS,
                transactionToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTransaction(transactionToDelete);
        showNoTransaction(expectedModel);

        assertCommandSuccess(deleteTransactionCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() throws PersonNotFoundException {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first person deleted
        deleteTransactionCommand.execute();
        undoRedoStack.push(deleteTransactionCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deleteTransaction(transactionToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() throws PersonNotFoundException {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTransactionList().size() + 1);
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteTransactionCommand, model, Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Transaction} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted transaction in the
     * unfiltered list is same from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the transaction object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameTransactionDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteTransactionCommand deleteTransactionCommand = prepareCommand(INDEX_FIRST_TRANSACTION);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Transaction transactionToDelete = model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased());
        deleteTransactionCommand.execute();
        undoRedoStack.push(deleteTransactionCommand);

        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteTransaction(transactionToDelete);
        assertEquals(transactionToDelete, model.getFilteredTransactionList()
                .get(INDEX_FIRST_TRANSACTION.getZeroBased()));
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_payerOrPayeesDoNotExist_throwsCommandException() throws CommandException,
            PersonNotFoundException {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Transaction transactionToDelete = model.getAddressBook().getTransactionList().get(1);

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteTransactionCommand.MESSAGE_NONEXISTENT_PAYER_PAYEES);

        getDeleteTransactionCommand(transactionToDelete, expectedModel).execute();
    }

    @Test
    public void execute_emptyTransactionList_throwsCommandException() throws CommandException,
            PersonNotFoundException {
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_EMPTY_TRANSACTION_LIST);

        getDeleteTransactionCommand(expectedModel).execute();
    }

    @Test
    public void equals() throws Exception {
        DeleteTransactionCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_TRANSACTION);
        DeleteTransactionCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_TRANSACTION);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTransactionCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_TRANSACTION);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different transaction -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteTransactionCommand prepareCommand(Index index) {
        DeleteTransactionCommand deleteTransactionCommand = new DeleteTransactionCommand(index);
        deleteTransactionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTransactionCommand;
    }
    /**
     * Generates a new AddPersonCommand with the details of the given person.
     */
    private DeleteTransactionCommand getDeleteTransactionCommand(Transaction transaction, Model model) throws
            PersonNotFoundException, CommandException {
        DeleteTransactionCommand command = new DeleteTransactionCommand(INDEX_SECOND_TRANSACTION);
        model.deletePerson(transaction.getPayer());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new AddPersonCommand with the details of the given person.
     */
    private DeleteTransactionCommand getDeleteTransactionCommand(Model model) {
        DeleteTransactionCommand command = new DeleteTransactionCommand(INDEX_FIRST_TRANSACTION);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTransaction(Model model) {
        model.updateFilteredTransactionList(p -> false);

        assertTrue(model.getFilteredTransactionList().isEmpty());
    }
}
