package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.building.Building;
import seedu.address.model.building.Room;
import seedu.address.model.building.Week;
import seedu.address.testutil.BuildingBuilder;
import seedu.address.testutil.RoomBuilder;

//@@author Caijun7
public class ReadOnlyJsonVenueInformationTest {

    private static final String TEST_DATA_FOLDER = "/ReadOnlyJsonVenueInformationTest/";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void readVenueInformation_nullFilePath_throwsNullPointerException() throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readVenueInformation(null);
    }

    @Test
    public void readVenueInformation_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readVenueInformation("NonExistentFile.json").isPresent());
    }

    @Test
    public void readVenueInformation_notJsonFormat_exceptionThrown() throws DataConversionException {
        thrown.expect(DataConversionException.class);
        readVenueInformation("NotJsonFormatVenueInformation.json");
    }

    @Test
    public void readVenueInformation_fileInOrder_successfullyRead() throws DataConversionException {
        HashMap<String, Week> expected = getTypicalVenueInformation().getNusRooms();
        HashMap<String, Week> actual = readVenueInformation("TypicalVenueInformation.json").get().getNusRooms();
        assertEquals(expected, actual);
    }

    @Test
    public void readVenueInformation_valuesMissingFromFile_defaultValueUsed() throws DataConversionException {
        HashMap<String, Week> actual = readVenueInformation("EmptyVenueInformation.json").get().getNusRooms();
        assertEquals(null, actual);
    }

    @Test
    public void readVenueInformation_extraValuesInFile_extraValuesIgnored() throws DataConversionException {
        HashMap<String, Week> expected = getTypicalVenueInformation().getNusRooms();
        HashMap<String, Week> actual = readVenueInformation("ExtraValuesVenueInformation.json").get().getNusRooms();
        assertEquals(expected, actual);
    }

    @Test
    public void readBuildingsAndRoomsInformation_nullFilePath_throwsNullPointerException()
            throws DataConversionException {
        thrown.expect(NullPointerException.class);
        readBuildingsAndRoomsInformation(null);
    }

    @Test
    public void readBuildingsAndRoomsInformation_missingFile_emptyResult() throws DataConversionException {
        assertFalse(readBuildingsAndRoomsInformation("NonExistentFile.json").isPresent());
    }

    @Test
    public void readBuildingsAndRoomsInformation_notJsonFormat_exceptionThrown() throws DataConversionException {
        thrown.expect(DataConversionException.class);
        readBuildingsAndRoomsInformation("NotJsonFormatVenueInformation.json");
    }

    @Test
    public void readBuildingsAndRoomsInformation_fileInOrder_successfullyRead() throws DataConversionException {
        HashMap<String, ArrayList<String>> expected = getTypicalBuildingsAndRoomsInformation().getBuildingsAndRooms();
        HashMap<String, ArrayList<String>> actual =
                readBuildingsAndRoomsInformation("TypicalVenueInformation.json").get().getBuildingsAndRooms();
        assertEquals(expected, actual);
    }

    @Test
    public void readBuildingsAndRoomsInformation_valuesMissingFromFile_defaultValueUsed()
            throws DataConversionException {
        HashMap<String, ArrayList<String>> actual =
                readBuildingsAndRoomsInformation("EmptyVenueInformation.json").get().getBuildingsAndRooms();
        assertEquals(null, actual);
    }

    @Test
    public void readBuildingsAndRoomsInformation_extraValuesInFile_extraValuesIgnored()
            throws DataConversionException {
        HashMap<String, ArrayList<String>> expected = getTypicalBuildingsAndRoomsInformation().getBuildingsAndRooms();
        HashMap<String, ArrayList<String>> actual =
                readBuildingsAndRoomsInformation("ExtraValuesVenueInformation.json").get().getBuildingsAndRooms();
        assertEquals(expected, actual);
    }

    private Optional<Room> readVenueInformation(String venueInformationFileInTestDataFolder)
            throws DataConversionException {
        String venueInformationFilePath = addToTestDataPathIfNotNull(venueInformationFileInTestDataFolder);
        return new ReadOnlyJsonVenueInformation(venueInformationFilePath)
                .readVenueInformation(venueInformationFilePath);
    }

    private Optional<Building> readBuildingsAndRoomsInformation(String venueInformationFileInTestDataFolder)
            throws DataConversionException {
        String venueInformationFilePath = addToTestDataPathIfNotNull(venueInformationFileInTestDataFolder);
        return new ReadOnlyJsonVenueInformation(venueInformationFilePath)
                .readBuildingsAndRoomsInformation(venueInformationFilePath);
    }

    private String addToTestDataPathIfNotNull(String venueInformationFileInTestDataFolder) {
        return venueInformationFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + venueInformationFileInTestDataFolder
                : null;
    }

    private Room getTypicalVenueInformation() {
        Room venueInformation = new RoomBuilder().build();
        return venueInformation;
    }

    private Building getTypicalBuildingsAndRoomsInformation() {
        Building buildingsAndRoomsInformation = new BuildingBuilder().build();
        return buildingsAndRoomsInformation;
    }

}
