package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Organizer;
import seedu.address.model.ReadOnlyOrganizer;

/**
 * Represents a storage for {@link Organizer}.
 */
public interface OrganizerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns Organizer data as a {@link ReadOnlyOrganizer}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyOrganizer> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyOrganizer> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyOrganizer} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyOrganizer addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyOrganizer)
     */
    void saveAddressBook(ReadOnlyOrganizer addressBook, String filePath) throws IOException;

}
