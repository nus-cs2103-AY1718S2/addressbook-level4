//@@author sarahgoh97
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.AddCellCommand.MESSAGE_ALREADY_IN_CELL;
import static seedu.address.logic.commands.AddCellCommand.MESSAGE_FULL_CELL;
import static seedu.address.logic.commands.AddCellCommand.MESSAGE_NOT_PRISONER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.address.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalCells.FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.FULL_CELL;
import static seedu.address.testutil.TypicalCells.INVALID_FIRST_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.INVALID_SECOND_CELL_ADDRESS;
import static seedu.address.testutil.TypicalCells.LAST_CELL_ADDRESS;
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

public class AddCellCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public AddCellCommandTest() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Test
    public void execute_validIndexUnfilteredListValidCellAddress_success() throws Exception {
        Person prisonerToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String cellAddress = LAST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, cellAddress);

        String expectedMessage = String.format(AddCellCommand.MESSAGE_ADD_CELL_SUCCESS,
                prisonerToAdd.getName().toString(), cellAddress);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.addPrisonerToCell(prisonerToAdd, cellAddress);

        assertCommandSuccess(addCellCommand, model, expectedMessage, expectedModel);
        addCellCommand.undo();
    }

    @Test
    public void execute_inValidIndexUnfilteredListValidCellAddress_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String cellAddress = FIRST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(outOfBoundIndex, cellAddress);

        assertCommandFailure(addCellCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    }

    @Test
    public void execute_validIndexUnfilteredListInvalidFirstDigitCellAddress_failure() {
        String cellAddress = INVALID_FIRST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model, String.format(AddCellCommand.MESSAGE_NON_EXISTENT_CELL,
                cellAddress));

    }

    @Test
    public void execute_validIndexUnfilteredListInvalidSecondDigitCellAddress_failure() {
        String cellAddress = INVALID_SECOND_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model, String.format(AddCellCommand.MESSAGE_NON_EXISTENT_CELL,
                cellAddress));

    }

    @Test
    public void execute_fullCell_failure() {
        String cellAddress = FULL_CELL.getCellAddress();
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model,
                String.format(MESSAGE_FULL_CELL, cellAddress));

    }

    @Test
    public void execute_personNotPrisoner_failure() {
        Person prisonerToAdd = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        String cellAddress = LAST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_SECOND_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model,
                String.format(MESSAGE_NOT_PRISONER, prisonerToAdd.getName().toString()));

    }

    @Test
    public void execute_personInCell_failure() {
        Person prisonerToAdd = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        String cellAddress = LAST_CELL_ADDRESS;
        AddCellCommand addCellCommand = prepareCommand(INDEX_THIRD_PERSON, cellAddress);

        assertCommandFailure(addCellCommand, model,
                String.format(MESSAGE_ALREADY_IN_CELL,
                        prisonerToAdd.getName().toString(), prisonerToAdd.getCellAddress()));

    }

    @Test
    public void equals() {
        AddCellCommand acFirstCommand = prepareCommand(INDEX_FIRST_PERSON, FIRST_CELL_ADDRESS);
        AddCellCommand acSecondCommand = prepareCommand(INDEX_FIRST_PERSON, LAST_CELL_ADDRESS);
        AddCellCommand acThirdCommand = prepareCommand(INDEX_SECOND_PERSON, FIRST_CELL_ADDRESS);


        // same object -> returns true
        assertTrue(acFirstCommand.equals(acFirstCommand));

        // same values -> returns true
        AddCellCommand copy = prepareCommand(INDEX_FIRST_PERSON, FIRST_CELL_ADDRESS);
        assertTrue(acFirstCommand.equals(copy));

        // different types -> returns false
        assertFalse(acFirstCommand.equals(1));

        // null -> returns false
        assertFalse(acFirstCommand.equals(null));

        // different cell -> returns false
        assertFalse(acFirstCommand.equals(acSecondCommand));

        //different person/index -> returns false
        assertFalse(acFirstCommand.equals(acThirdCommand));
    }

    @Test
    public void executeUndoRedo() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        AddCellCommand addCellCommand = prepareCommand(INDEX_FIRST_PERSON, FIRST_CELL_ADDRESS);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // add cell -> first person added to a cell
        Person prisonerToAdd = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        addCellCommand.preprocessUndoableCommand();
        addCellCommand.execute();
        undoRedoStack.push(addCellCommand);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.addPrisonerToCell(prisonerToAdd, FIRST_CELL_ADDRESS);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
        addCellCommand.undo();
    }

    private AddCellCommand prepareCommand(Index index, String cellAddress) {
        AddCellCommand addCellCommand = new AddCellCommand(index, cellAddress);
        addCellCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addCellCommand;
    }
}
