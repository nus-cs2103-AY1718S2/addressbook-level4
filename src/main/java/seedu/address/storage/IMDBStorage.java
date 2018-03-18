package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Imdb;
import seedu.address.model.ReadOnlyImdb;

/**
 * Represents a storage for {@link Imdb}.
 */
public interface IMDBStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns Imdb data as a {@link ReadOnlyImdb}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyImdb> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyImdb> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyImdb} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyImdb addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyImdb)
     */
    void saveAddressBook(ReadOnlyImdb addressBook, String filePath) throws IOException;

    void backupAddressBook(ReadOnlyImdb addressBook) throws IOException;
}
