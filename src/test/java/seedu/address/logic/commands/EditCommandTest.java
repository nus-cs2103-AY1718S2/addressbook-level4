package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showCoinAtIndex;
import static seedu.address.testutil.TypicalCoins.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COIN;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COIN;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditCoinDescriptor;
import seedu.address.model.CoinBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.coin.Coin;
import seedu.address.testutil.CoinBuilder;
import seedu.address.testutil.EditCoinDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Coin editedCoin = new CoinBuilder().build();
        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder(editedCoin).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_COIN, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COIN_SUCCESS, editedCoin);

        Model expectedModel = new ModelManager(new CoinBook(model.getCoinBook()), new UserPrefs());
        expectedModel.updateCoin(model.getFilteredCoinList().get(0), editedCoin);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastCoin = Index.fromOneBased(model.getFilteredCoinList().size());
        Coin lastCoin = model.getFilteredCoinList().get(indexLastCoin.getZeroBased());

        CoinBuilder coinInList = new CoinBuilder(lastCoin);
        Coin editedCoin = coinInList.withName(VALID_NAME_BOB).withCode(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = prepareCommand(indexLastCoin, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COIN_SUCCESS, editedCoin);

        Model expectedModel = new ModelManager(new CoinBook(model.getCoinBook()), new UserPrefs());
        expectedModel.updateCoin(lastCoin, editedCoin);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = prepareCommand(INDEX_FIRST_COIN, new EditCoinDescriptor());
        Coin editedCoin = model.getFilteredCoinList().get(INDEX_FIRST_COIN.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COIN_SUCCESS, editedCoin);

        Model expectedModel = new ModelManager(new CoinBook(model.getCoinBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showCoinAtIndex(model, INDEX_FIRST_COIN);

        Coin coinInFilteredList = model.getFilteredCoinList().get(INDEX_FIRST_COIN.getZeroBased());
        Coin editedCoin = new CoinBuilder(coinInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_COIN,
                new EditCoinDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_COIN_SUCCESS, editedCoin);

        Model expectedModel = new ModelManager(new CoinBook(model.getCoinBook()), new UserPrefs());
        expectedModel.updateCoin(model.getFilteredCoinList().get(0), editedCoin);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCoinUnfilteredList_failure() {
        Coin firstCoin = model.getFilteredCoinList().get(INDEX_FIRST_COIN.getZeroBased());
        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder(firstCoin).build();
        EditCommand editCommand = prepareCommand(INDEX_SECOND_COIN, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_COIN);
    }

    @Test
    public void execute_duplicateCoinFilteredList_failure() {
        showCoinAtIndex(model, INDEX_FIRST_COIN);

        // edit coin in filtered list into a duplicate in address book
        Coin coinInList = model.getCoinBook().getCoinList().get(INDEX_SECOND_COIN.getZeroBased());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_COIN,
                new EditCoinDescriptorBuilder(coinInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_COIN);
    }

    @Test
    public void execute_invalidCoinIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCoinList().size() + 1);
        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_COIN_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCoinIndexFilteredList_failure() {
        showCoinAtIndex(model, INDEX_FIRST_COIN);
        Index outOfBoundIndex = INDEX_SECOND_COIN;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCoinBook().getCoinList().size());

        EditCommand editCommand = prepareCommand(outOfBoundIndex,
                new EditCoinDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_COIN_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Coin editedCoin = new CoinBuilder().build();
        Coin coinToEdit = model.getFilteredCoinList().get(INDEX_FIRST_COIN.getZeroBased());
        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder(editedCoin).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_COIN, descriptor);
        Model expectedModel = new ModelManager(new CoinBook(model.getCoinBook()), new UserPrefs());

        // edit -> first coin edited
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered coin list to show all coins
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first coin edited again
        expectedModel.updateCoin(coinToEdit, editedCoin);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCoinList().size() + 1);
        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);

        // execution failed -> editCommand not pushed into undoRedoStack
        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_COIN_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Coin} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited coin in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the coin object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameCoinEdited() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Coin editedCoin = new CoinBuilder().build();
        EditCoinDescriptor descriptor = new EditCoinDescriptorBuilder(editedCoin).build();
        EditCommand editCommand = prepareCommand(INDEX_FIRST_COIN, descriptor);
        Model expectedModel = new ModelManager(new CoinBook(model.getCoinBook()), new UserPrefs());

        showCoinAtIndex(model, INDEX_SECOND_COIN);
        Coin coinToEdit = model.getFilteredCoinList().get(INDEX_FIRST_COIN.getZeroBased());
        // edit -> edits second coin in unfiltered coin list / first coin in filtered coin list
        editCommand.execute();
        undoRedoStack.push(editCommand);

        // undo -> reverts addressbook back to previous state and filtered coin list to show all coins
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.updateCoin(coinToEdit, editedCoin);
        assertNotEquals(model.getFilteredCoinList().get(INDEX_FIRST_COIN.getZeroBased()), coinToEdit);
        // redo -> edits same second coin in unfiltered coin list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        final EditCommand standardCommand = prepareCommand(INDEX_FIRST_COIN, DESC_AMY);

        // same values -> returns true
        EditCoinDescriptor copyDescriptor = new EditCoinDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = prepareCommand(INDEX_FIRST_COIN, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // one command preprocessed when previously equal -> returns false
        commandWithSameValues.preprocessUndoableCommand();
        assertFalse(standardCommand.equals(commandWithSameValues));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_COIN, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_COIN, DESC_BOB)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditCoinDescriptor descriptor) {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
