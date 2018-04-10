package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
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
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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

    /**
     * Generates a new MapCommand given the locations specified
     */
    private MapCommand prepareCommand(String locations, Model model) {
        MapCommand command = new MapCommand(locations);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
