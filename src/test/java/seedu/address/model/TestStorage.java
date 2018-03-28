package seedu.address.model;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.login.User;
import seedu.address.storage.Storage;

/**
 * Acts as a dummy StorageManager
 */
public class TestStorage extends ComponentManager implements Storage {

    public TestStorage(){}

    @Override
    public String getUserPrefsFilePath() {
        return null;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {}

    @Override
    public String getAddressBookFilePath() {
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) {
        return null;
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {}

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {}

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    @Override
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {}


    @Override
    public String getUserDatabaseFilePath() {
        return null;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return null;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase(String filePath)
            throws DataConversionException, IOException {
        return null;
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {}

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, String filePath) throws IOException {}

    public void update(User user) {}
}
