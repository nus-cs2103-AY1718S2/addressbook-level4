//@@author sarahgoh97
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.logic.commands.DeleteCellCommand.MESSAGE_NOT_IMPRISONED;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class DeleteCellCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public DeleteCellCommandTest() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        DeleteCellCommand deleteCellCommand = prepareCommand(INDEX_THIRD_PERSON);
        Person prisoner = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        String expectedMessage = String.format(DeleteCellCommand.MESSAGE_DELETE_CELL_SUCCESS,
                prisoner.getName().toString());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.deletePrisonerFromCell(prisoner);

        assertCommandSuccess(deleteCellCommand, model, expectedMessage, expectedModel);
        deleteCellCommand.undo();
        expectedModel.addPrisonerToCellFromUndo(prisoner, prisoner.getCellAddress().toString());
    }

    @Test
    public void execute_indexOfNotImprisonedPrisoner_failure() throws Exception {
        DeleteCellCommand deleteCellCommand = prepareCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(deleteCellCommand, model, MESSAGE_NOT_IMPRISONED);
    }

    @Test
    public void execute_indexOfNotImprisonedGuard_failure() throws Exception {
        DeleteCellCommand deleteCellCommand = prepareCommand(INDEX_SECOND_PERSON);
        assertCommandFailure(deleteCellCommand, model, MESSAGE_NOT_IMPRISONED);
    }

    @Test
    public void execute_invalidIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCellCommand deleteCellCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(deleteCellCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Person prisonerReleased = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        DeleteCellCommand deleteCellCommand = prepareCommand(INDEX_THIRD_PERSON);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // delete -> first person deleted
        deleteCellCommand.execute();
        undoRedoStack.push(deleteCellCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.deletePrisonerFromCell(prisonerReleased);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        deleteCellCommand.undo();
    }

    @Test
    public void equals() throws Exception {
        DeleteCellCommand deleteCellFirstCommand = prepareCommand(INDEX_THIRD_PERSON);
        DeleteCellCommand deleteCellSecondCommand = prepareCommand(Index.fromOneBased(5));

        // same object -> returns true
        assertTrue(deleteCellFirstCommand.equals(deleteCellFirstCommand));

        // same values -> returns true
        DeleteCellCommand deleteCellFirstCommandCopy = prepareCommand(INDEX_THIRD_PERSON);
        assertTrue(deleteCellFirstCommand.equals(deleteCellFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteCellFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteCellFirstCommand.equals(deleteCellFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteCellFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteCellFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteCellFirstCommand.equals(deleteCellSecondCommand));
    }

    /**
     * Returns a {@code DeleteCellCommand} with the parameter {@code index}.
     */
    private DeleteCellCommand prepareCommand(Index index) {
        DeleteCellCommand deleteCellCommand = new DeleteCellCommand(index);
        deleteCellCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCellCommand;
    }
}
