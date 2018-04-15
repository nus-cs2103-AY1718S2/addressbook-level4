package seedu.ptman.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_FIRST_SHIFT;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_OUT_OF_BOUNDS_SHIFT;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_SECOND_SHIFT;
import static seedu.ptman.testutil.TypicalIndexes.INDEX_SEVENTH_SHIFT;
import static seedu.ptman.testutil.TypicalShifts.SHIFT_MONDAY_AM;
import static seedu.ptman.testutil.TypicalShifts.SHIFT_MONDAY_PM;
import static seedu.ptman.testutil.TypicalShifts.SHIFT_THURSDAY_PM;
import static seedu.ptman.testutil.TypicalShifts.getTypicalPartTimeManagerWithShifts;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.ptman.commons.core.Messages;
import seedu.ptman.commons.core.index.Index;
import seedu.ptman.logic.CommandHistory;
import seedu.ptman.logic.UndoRedoStack;
import seedu.ptman.logic.commands.exceptions.CommandException;
import seedu.ptman.model.Model;
import seedu.ptman.model.ModelManager;
import seedu.ptman.model.UserPrefs;
import seedu.ptman.model.employee.Employee;
import seedu.ptman.model.outlet.OutletInformation;

//@@author hzxcaryn
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ViewShiftCommandTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPartTimeManagerWithShifts(),
                new UserPrefs(), new OutletInformation());
        model.setFilteredShiftListToWeek(SHIFT_MONDAY_AM.getDate().getLocalDate());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredShiftList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_SHIFT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexShiftWithEmployees_success() {
        assertExecutionSuccess(INDEX_SEVENTH_SHIFT,
                SHIFT_THURSDAY_PM.getEmployeeList().sorted()); //8th Shift has employees
    }

    @Test
    public void execute_validIndexShiftWithoutEmployees_success() {
        assertExecutionSuccess(INDEX_SECOND_SHIFT, SHIFT_MONDAY_PM.getEmployeeList());
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        Index outOfBoundsIndex = INDEX_OUT_OF_BOUNDS_SHIFT;
        // ensures that outOfBoundIndex is still in bounds of ptman shift list
        assertTrue(outOfBoundsIndex.getZeroBased() > model.getPartTimeManager().getShiftList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_SHIFT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ViewShiftCommand viewShiftFirstCommand = new ViewShiftCommand(INDEX_FIRST_SHIFT);
        ViewShiftCommand viewShiftSecondCommand = new ViewShiftCommand(INDEX_SECOND_SHIFT);

        // same object -> returns true
        assertTrue(viewShiftFirstCommand.equals(viewShiftFirstCommand));

        // same values -> returns true
        ViewShiftCommand viewShiftFirstCommandCopy = new ViewShiftCommand(INDEX_FIRST_SHIFT);
        assertTrue(viewShiftFirstCommand.equals(viewShiftFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewShiftFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewShiftFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(viewShiftFirstCommand.equals(viewShiftSecondCommand));
    }

    /**
     * Executes a {@code ViewShiftCommand} with the given {@code index}, and checks that {@code filteredEmployeeList}
     * is updated with the correct employees.
     */
    private void assertExecutionSuccess(Index index, ObservableList<Employee> expectedEmployeeList) {
        ViewShiftCommand viewShiftCommand = prepareCommand(index);

        try {
            CommandResult commandResult = viewShiftCommand.execute();
            assertEquals(String.format(ViewShiftCommand.MESSAGE_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        // checks that filteredEmployeeList is updated with the correct employees.
        assertEquals(expectedEmployeeList, model.getFilteredEmployeeList());
    }

    /**
     * Executes a {@code ViewShiftCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ViewShiftCommand viewShiftCommand = prepareCommand(index);

        try {
            viewShiftCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
        }
    }

    /**
     * Returns a {@code ViewShiftCommand} with parameters {@code index}.
     */
    private ViewShiftCommand prepareCommand(Index index) {
        ViewShiftCommand viewShiftCommand = new ViewShiftCommand(index);
        viewShiftCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return viewShiftCommand;
    }


}
