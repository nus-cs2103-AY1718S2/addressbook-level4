package seedu.organizer.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.organizer.commons.exceptions.DataConversionException;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;

/**
 * Represents a storage for {@link Organizer}.
 */
public interface OrganizerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getOrganizerFilePath();

    /**
     * Returns Organizer data as a {@link ReadOnlyOrganizer}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyOrganizer> readOrganizer() throws DataConversionException, IOException;

    /**
     * @see #getOrganizerFilePath()
     */
    Optional<ReadOnlyOrganizer> readOrganizer(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyOrganizer} to the storage.
     * @param organizer cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveOrganizer(ReadOnlyOrganizer organizer) throws IOException;

    /**
     * @see #saveOrganizer(ReadOnlyOrganizer)
     */
    void saveOrganizer(ReadOnlyOrganizer organizer, String filePath) throws IOException;

}
