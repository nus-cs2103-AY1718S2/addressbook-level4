package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.login.User;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, UserDatabaseStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);


    @Override
    String getUserDatabaseFilePath();

    @Override
    Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyUserDatabase> readUserDatabase(String filePath) throws DataConversionException, IOException;

    @Override
    void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException;

    @Override
    void saveUserDatabase(ReadOnlyUserDatabase userDatabase, String filePath) throws IOException;

    void update(User user);
}
