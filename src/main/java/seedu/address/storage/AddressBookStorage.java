package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.IMDB;
import seedu.address.model.ReadOnlyIMDB;

/**
 * Represents a storage for {@link IMDB}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns IMDB data as a {@link ReadOnlyIMDB}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyIMDB> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyIMDB> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyIMDB} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyIMDB addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyIMDB)
     */
    void saveAddressBook(ReadOnlyIMDB addressBook, String filePath) throws IOException;

    void backupAddressBook(ReadOnlyIMDB addressBook) throws IOException;
}
