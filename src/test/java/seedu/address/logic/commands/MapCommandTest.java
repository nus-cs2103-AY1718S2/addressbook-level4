package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BUILDING_UPPERCASE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THREE_LOCATIONS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWO_LOCATIONS_ADDRESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author jingyinno
/**
 * Contains integration tests (interaction with the Model) and unit tests for MapCommand.
 */
public class MapCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullLocation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new MapCommand(null);
    }

    @Test
    public void execute_inputOneLocation_retrievalSuccessful() throws Exception {
        String expectedMessage = String.format(MapCommand.MESSAGE_SUCCESS);
        CommandResult result = prepareCommand(VALID_LOCATION_BUILDING_UPPERCASE_1, model).execute();
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_inputTwoLocations_retrievalSuccessful() throws Exception {
        String expectedMessage = String.format(MapCommand.MESSAGE_SUCCESS);
        CommandResult result = prepareCommand(VALID_TWO_LOCATIONS_ADDRESS, model).execute();
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void execute_inputThreeLocations_retrievalSuccessful() throws Exception {
        String expectedMessage = String.format(MapCommand.MESSAGE_SUCCESS);
        CommandResult result = prepareCommand(VALID_THREE_LOCATIONS, model).execute();
        assertEquals(expectedMessage, result.feedbackToUser);
    }

    @Test
    public void equals() {
        MapCommand nus = new MapCommand("nus");
        MapCommand soc = new MapCommand("soc");
        MapCommand nusCopy = new MapCommand("nus");

        // same object & values -> returns true
        assertTrue(nus.equals(nusCopy));

        // different types -> returns false
        assertFalse(nus.equals(1));

        // null -> returns false
        assertFalse(nus == null);

        // different person -> returns false
        assertFalse(nus.equals(soc));
    }

    /**
     * Generates a new MapCommand given the locations specified
     */
    private MapCommand prepareCommand(String locations, Model model) {
        MapCommand command = new MapCommand(locations);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
