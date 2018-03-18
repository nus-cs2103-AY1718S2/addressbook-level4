package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.ImdbChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyImdb;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Imdb data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private IMDBStorage IMDBStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(IMDBStorage IMDBStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.IMDBStorage = IMDBStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Imdb methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return IMDBStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyImdb> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(IMDBStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyImdb> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return IMDBStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyImdb addressBook) throws IOException {
        saveAddressBook(addressBook, IMDBStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyImdb addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        IMDBStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void backupAddressBook(ReadOnlyImdb addressBook) throws IOException {
        IMDBStorage.backupAddressBook(addressBook);
    }

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(ImdbChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
