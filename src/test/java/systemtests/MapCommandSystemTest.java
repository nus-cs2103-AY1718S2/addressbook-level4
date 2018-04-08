package systemtests;

import org.junit.Test;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_ADDRESS_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_ADDRESS_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BUILDING_LOWERCASE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BUILDING_UPPERCASE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_BUILDING_UPPERCASE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_POSTAL_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_POSTAL_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THREE_LOCATIONS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWO_LOCATIONS_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWO_LOCATIONS_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWO_LOCATIONS_3;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWO_LOCATIONS_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWO_LOCATIONS_BUILDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TWO_LOCATIONS_POSTAL;

import static seedu.address.logic.commands.MapCommand.MESSAGE_SUCCESS;
import static seedu.address.model.building.Building.retrieveNusBuildingIfExist;

//@@author jingyinno
/**
 * A system test class for the Google Maps Display panel, which contains interaction with other UI components.
 */
public class MapCommandSystemTest extends AddressBookSystemTest {

    private static final boolean isOneLocation = true;

    @Test
    public void map() {
        String bufferOneLocation = MapCommand.COMMAND_WORD + " " + VALID_LOCATION_BUILDING_LOWERCASE;
        executeCommand(bufferOneLocation);
        String bufferTwoLocations = MapCommand.COMMAND_WORD + " " + VALID_TWO_LOCATIONS_1;
        executeCommand(bufferTwoLocations);
        /* ----------------------------------- Perform valid map operations ----------------------------------- */

        /* Case: NUS building specified, command with no leading and trailing spaces
        * -> retrieved
        */
        String expectedQuery = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_LOWERCASE);
        String command = MapCommand.COMMAND_WORD + " " + VALID_LOCATION_BUILDING_LOWERCASE;
        assertCommandSuccess(command, expectedQuery, isOneLocation);

        /* Case: NUS building specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedQuery = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_2);
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_LOCATION_BUILDING_UPPERCASE_2 + "   ";
        assertCommandSuccess(command, expectedQuery, isOneLocation);

        /* Case: Postal code specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedQuery = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_1);
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_LOCATION_POSTAL_1 + "   ";
        assertCommandSuccess(command, expectedQuery, isOneLocation);

        /* Case: Address specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedQuery = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_2).replaceAll(" ", "%20");
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_LOCATION_ADDRESS_2 + "   ";
        assertCommandSuccess(command, expectedQuery, isOneLocation);

        /* Case: Two NUS buildings specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        String expectedFirstBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_1);
        String expectedSecondBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_2);
        expectedQuery = expectedFirstBuilding + "/" + expectedSecondBuilding;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_BUILDING + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: Two postal codes specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        String expectedFirstPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_1);
        String expectedSecondPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_2);
        expectedQuery = expectedFirstPostal + "/" + expectedSecondPostal;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_POSTAL + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: Two addresses specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        String expectedFirstAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_1).replaceAll(" ", "%20");
        String expectedSecondAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_2).replaceAll(" ", "%20");
        expectedQuery = expectedFirstAddress + "/" + expectedSecondAddress;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_ADDRESS + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: NUS building and postal code specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        String expectedBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_1);
        String expectedPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_1);
        expectedQuery = expectedBuilding + "/" + expectedPostal;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_1 + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: NUS building and address specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_UPPERCASE_1);
        String expectedAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_1).replaceAll(" ", "%20");
        expectedQuery = expectedBuilding + "/" + expectedAddress;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_2 + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: postal and address specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_1);
        expectedAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_1).replaceAll(" ", "%20");
        expectedQuery = expectedPostal + "/" + expectedAddress;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_TWO_LOCATIONS_3 + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: Three locations specified, command with leading spaces and trailing spaces
         * -> retrieved
         */
        expectedBuilding = retrieveNusBuildingIfExist(VALID_LOCATION_BUILDING_LOWERCASE);
        expectedPostal = retrieveNusBuildingIfExist(VALID_LOCATION_POSTAL_2);
        expectedAddress = retrieveNusBuildingIfExist(VALID_LOCATION_ADDRESS_1).replaceAll(" ", "%20");
        expectedQuery = expectedAddress + "/" + expectedBuilding + "/" + expectedPostal;
        command = "   " + MapCommand.COMMAND_WORD + "  " + VALID_THREE_LOCATIONS + "   ";
        assertCommandSuccess(command, expectedQuery, !isOneLocation);

        /* Case: undo previous command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid map operations ----------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(MapCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MapCommand.MESSAGE_USAGE));
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing select command with the
     * {@code expectedSelectedCardIndex} of the selected person.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Selected card is at {@code expectedSelectedCardIndex} and the browser url is updated accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String query, boolean isOneLocation) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(MESSAGE_SUCCESS);

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertMapDisplayChanged(isOneLocation, query);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
