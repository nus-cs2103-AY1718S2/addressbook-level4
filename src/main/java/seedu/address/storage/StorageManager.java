package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AppointmentChangedEvent;
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
    private ImdbStorage imdbStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ImdbStorage imdbStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.imdbStorage = imdbStorage;
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
        return imdbStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyImdb> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(imdbStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyImdb> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return imdbStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyImdb addressBook) throws IOException {
        saveAddressBook(addressBook, imdbStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyImdb addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        imdbStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void backupAddressBook(ReadOnlyImdb addressBook) throws IOException {
        imdbStorage.backupAddressBook(addressBook);
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

    //@@author Kyholmes
    @Override
    @Subscribe
    public void handleAppointmentChangedEvent(AppointmentChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.readOnlyImdb);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
