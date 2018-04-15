package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.building.Building;
import seedu.address.model.building.Room;

//@@author Caijun7
/**
 * Represents a storage for venue information.
 */
public interface ReadOnlyVenueInformation {

    /**
     * Returns the file path of the VenueInformation data file.
     */
    String getVenueInformationFilePath();

    /**
     * Reads VenueInformation data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Room> readVenueInformation() throws DataConversionException, IOException;

    /**
     * Reads BuildingsAndRooms data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Building> readBuildingsAndRoomsInformation() throws DataConversionException, IOException;
}
