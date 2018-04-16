package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.ExistingFileException;
import seedu.address.commons.exceptions.InvalidFileException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(String filePath)
            throws DataConversionException, IOException, JAXBException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException, InvalidFileException;

    /**
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to given filepath.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void exportAddressBook(ReadOnlyAddressBook addressBook, String filePath)
            throws IOException, InvalidFileException, ExistingFileException;

    /**
     * Backups the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem backing up the file.
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException, InvalidFileException;

    void exportAddressBookCsv(ReadOnlyAddressBook addressBook, String filePath)
            throws IOException, InvalidFileException, ExistingFileException;
}
