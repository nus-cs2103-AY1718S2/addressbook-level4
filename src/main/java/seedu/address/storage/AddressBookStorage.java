package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.model.Password;
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
     * @throws WrongPasswordException if the password is wrong.
     */
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException, WrongPasswordException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException,
                                                                        WrongPasswordException;
    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(Password password) throws DataConversionException,
                                                                            IOException, WrongPasswordException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(String filePath, Password password) throws DataConversionException,
            IOException, WrongPasswordException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException, WrongPasswordException;

    /**
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException, WrongPasswordException;

    /**
     * Saves the given (@link ReadOnlyAddressBook) to a offline backup file
     * @param addressBook cannot be null
     * @throws IOException
     */
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException, WrongPasswordException;
}
