package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.CommandTestUtil.showPairAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PAIR;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PAIR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.pair.Pair;
import seedu.address.model.pair.PairHash;
import seedu.address.model.person.Person;
//@@author alexawangzi
public class UnmatchCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Pair pairToDelete = model.getFilteredPairList().get(INDEX_FIRST_PAIR.getZeroBased());
        //update persons in model to have the correct pairhash to test deletion
        Person alice = model.getFilteredPersonList().get(0);
        Person benson = model.getFilteredPersonList().get(1);
        Person newAlice = model.getFilteredPersonList().get(0);
        Person newBenson = model.getFilteredPersonList().get(1);
        newAlice.addPairHash(pairToDelete.getPairHash());
        newBenson.addPairHash(pairToDelete.getPairHash());
        newBenson.addPairHash(new PairHash(1234)); //ensure benson status stays matched after unmatch
        model.updatePerson(newAlice, alice);
        model.updatePerson(newBenson, benson);

        UnmatchCommand unmatchCommand = prepareCommand(INDEX_FIRST_PAIR);

        String expectedMessage = String.format(UnmatchCommand.MESSAGE_UNMATCH_PAIR_SUCCESS, pairToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePair(pairToDelete);

        assertCommandSuccess(unmatchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPairList().size() + 1);
        UnmatchCommand unmatchCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPairAtIndex(model, INDEX_FIRST_PAIR);

        Pair pairToDelete = model.getFilteredPairList().get(INDEX_FIRST_PAIR.getZeroBased());
        UnmatchCommand unmatchCommand = prepareCommand(INDEX_FIRST_PAIR);

        String expectedMessage = String.format(UnmatchCommand.MESSAGE_UNMATCH_PAIR_SUCCESS, pairToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePair(pairToDelete);
        showNoPair(expectedModel);

        assertCommandSuccess(unmatchCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPairAtIndex(model, INDEX_FIRST_PAIR);

        Index outOfBoundIndex = INDEX_SECOND_PAIR;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPairList().size());

        UnmatchCommand unmatchCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Pair pairToDelete = model.getFilteredPairList().get(INDEX_FIRST_PAIR.getZeroBased());
        UnmatchCommand unmatchCommand = prepareCommand(INDEX_FIRST_PAIR);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // delete -> first pair deleted
        unmatchCommand.execute();
        undoRedoStack.push(unmatchCommand);

        // undo -> reverts addressbook back to previous state and filtered pair list to show all pairs
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first pair deleted again
        expectedModel.deletePair(pairToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPairList().size() + 1);
        UnmatchCommand unmatchCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> unmatchCommand not pushed into undoRedoStack
        assertCommandFailure(unmatchCommand, model, Messages.MESSAGE_INVALID_PAIR_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() throws Exception {
        UnmatchCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_PAIR);
        UnmatchCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_PAIR);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        UnmatchCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_PAIR);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different pair -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code UnmatchCommand} with the parameter {@code index}.
     */
    private UnmatchCommand prepareCommand(Index index) {
        UnmatchCommand unmatchCommand = new UnmatchCommand(index);
        unmatchCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return unmatchCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPair(Model model) {
        model.updateFilteredPairList(p -> false);

        assertTrue(model.getFilteredPairList().isEmpty());
    }
}
