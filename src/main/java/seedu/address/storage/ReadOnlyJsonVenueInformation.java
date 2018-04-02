package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.building.Building;
import seedu.address.model.building.Room;

/**
 * A class to access VenueInformation stored in the hard disk as a json file
 */
public class ReadOnlyJsonVenueInformation implements ReadOnlyVenueInformation {

    private String filePath;

    public ReadOnlyJsonVenueInformation(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getVenueInformationFilePath() {
        return filePath;
    }

    @Override
    public Optional<Room> readVenueInformation() throws DataConversionException, IOException {
        return readVenueInformation(filePath);
    }

    /**
     * Converts Json file into HashMap of NUS Rooms
     * @param venueInformationFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Room> readVenueInformation(String venueInformationFilePath) throws DataConversionException {
        return JsonUtil.readJsonFileFromResource(venueInformationFilePath, Room.class);
    }

    @Override
    public Optional<Building> readBuildingsAndRoomsInformation() throws DataConversionException, IOException {
        return readBuildingsAndRoomsInformation(filePath);
    }

    /**
     * Converts Json file into HashMap of NUS Buildings and Rooms
     * @param buildingsAndRoomsInformationFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Building> readBuildingsAndRoomsInformation(String buildingsAndRoomsInformationFilePath)
            throws DataConversionException {
        return JsonUtil.readJsonFileFromResource(buildingsAndRoomsInformationFilePath, Building.class);
    }

}
