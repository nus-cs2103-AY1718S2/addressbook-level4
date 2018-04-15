package seedu.ptman.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.ptman.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.ptman.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.ptman.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.ptman.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.ptman.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_FIRST_SHIFT;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_SECOND_EMPLOYEE;
import static seedu.ptman.testutil.TypicalShifts.getTypicalPartTimeManagerWithShifts;

import org.junit.Before;
import org.junit.Test;

import seedu.ptman.commons.core.Messages;
import seedu.ptman.commons.core.index.Index;
import seedu.ptman.logic.CommandHistory;
import seedu.ptman.logic.UndoRedoStack;
import seedu.ptman.model.Model;
import seedu.ptman.model.ModelManager;
import seedu.ptman.model.Password;
import seedu.ptman.model.UserPrefs;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.employee.exceptions.DuplicateEmployeeException;
import seedu.ptman.model.employee.exceptions.EmployeeNotFoundException;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.model.shift.Shift;
import seedu.ptman.model.shift.exceptions.ShiftFullException;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalPartTimeManagerWithShifts(),
            new UserPrefs(), new OutletInformation());

    @Before
    public void setMode_adminMode() {
        model.setTrueAdminMode(new Password());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete);

        ModelManager expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs(),
                new OutletInformation());
        expectedModel.deleteEmployee(employeeToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_notInAdminMode_throwsCommandExceptionAccessDenied() throws Exception {
        model.setFalseAdminMode();
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size());
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_ACCESS_DENIED);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete);

        Model expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs(), new OutletInformation());
        expectedModel.deleteEmployee(employeeToDelete);
        showNoEmployee(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexEmployeeInShift_employeeRemovedFromShiftSuccess()
            throws ShiftFullException, DuplicateEmployeeException, EmployeeNotFoundException {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        model.updateFilteredShiftList(Model.PREDICATE_SHOW_ALL_SHIFTS);
        Shift shift = model.getFilteredShiftList().get(INDEX_FIRST_SHIFT.getZeroBased());
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        shift.addEmployee(employeeToDelete);
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_EMPLOYEE_SUCCESS, employeeToDelete);

        Model expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs(), new OutletInformation());
        expectedModel.updateFilteredShiftList(Model.PREDICATE_SHOW_ALL_SHIFTS);
        expectedModel.deleteEmployee(employeeToDelete);
        showNoEmployee(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);

        Index outOfBoundIndex = INDEX_SECOND_EMPLOYEE;
        // ensures that outOfBoundIndex is still in bounds of ptman book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPartTimeManager().getEmployeeList().size());

        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);
        Model expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs(), new OutletInformation());

        // delete -> first employee deleted
        deleteCommand.execute();
        undoRedoStack.push(deleteCommand);

        // undo -> reverts parttimemanager back to previous state and filtered employee list to show all employees
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first employee deleted again
        expectedModel.deleteEmployee(employeeToDelete);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEmployeeList().size() + 1);
        DeleteCommand deleteCommand = prepareCommand(outOfBoundIndex);

        // execution failed -> deleteCommand not pushed into undoRedoStack
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX);

        // no commands in undoRedoStack -> undoCommand and redoCommand fail
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Employee} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted employee in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the employee object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameEmployeeDeleted() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        DeleteCommand deleteCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);
        Model expectedModel = new ModelManager(model.getPartTimeManager(), new UserPrefs(), new OutletInformation());

        showEmployeeAtIndex(model, INDEX_SECOND_EMPLOYEE);
        Employee employeeToDelete = model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased());
        // delete -> deletes second employee in unfiltered employee list / first employee in filtered employee list
        deleteCommand.execute();
        undoRedoStack.push(deleteCommand);

        // undo -> reverts parttimemanager back to previous state and filtered employee list to show all employees
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        expectedModel.deleteEmployee(employeeToDelete);
        assertNotEquals(employeeToDelete, model.getFilteredEmployeeList().get(INDEX_FIRST_EMPLOYEE.getZeroBased()));
        // redo -> deletes same second employee in unfiltered employee list
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        DeleteCommand deleteFirstCommand = prepareCommand(INDEX_FIRST_EMPLOYEE);
        DeleteCommand deleteSecondCommand = prepareCommand(INDEX_SECOND_EMPLOYEE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = prepareCommand(INDEX_FIRST_EMPLOYEE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // one command preprocessed when previously equal -> returns false
        deleteFirstCommandCopy.preprocessUndoableCommand();
        assertFalse(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        DeleteCommand deleteCommand = new DeleteCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoEmployee(Model model) {
        model.updateFilteredEmployeeList(p -> false);

        assertTrue(model.getFilteredEmployeeList().isEmpty());
    }
}
