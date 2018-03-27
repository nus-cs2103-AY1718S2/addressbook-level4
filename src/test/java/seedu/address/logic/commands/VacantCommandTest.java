package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.building.Building;

public class VacantCommandTest {

    private static final String TEST_BUILDING_COM1 = "COM1";
    private static final String TEST_BUILDING_COM2 = "COM2";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validBuildingRetrieval_success() throws Exception {
        VacantCommand vacantCommand = prepareCommand(TEST_BUILDING_COM1);
        String expectedMessage = String.format(VacantCommand.MESSAGE_SUCCESS);
        assertEquals(String.format(VacantCommand.MESSAGE_SUCCESS, vacantCommand), expectedMessage);
    }

    @Test
    public void equals() throws Exception {
        VacantCommand vacantCommand = prepareCommand(TEST_BUILDING_COM1);
        VacantCommand passwordSecondCommand = prepareCommand(TEST_BUILDING_COM2);

        // same object -> returns true
        assertTrue(vacantCommand.equals(vacantCommand));

        // same values -> returns true
        VacantCommand vacantCommandCopy = prepareCommand(TEST_BUILDING_COM1);
        assertTrue(vacantCommand.equals(vacantCommandCopy));

        // different types -> returns false
        assertFalse(vacantCommand.equals(1));

        // null -> returns false
        assertFalse(vacantCommand.equals(null));

        // different vacant -> returns false
        assertFalse(vacantCommand.equals(TEST_BUILDING_COM2));
    }

    /**
     * Returns a {@code VacantCommand} with the parameter {@code password}.
     */
    private VacantCommand prepareCommand(String building) {
        VacantCommand command = new VacantCommand(new Building(building));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
