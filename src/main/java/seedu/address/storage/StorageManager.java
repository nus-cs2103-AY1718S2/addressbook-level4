package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.OrganizerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyOrganizer;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Organizer data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private OrganizerStorage organizerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(OrganizerStorage organizerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.organizerStorage = organizerStorage;
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


    // ================ Organizer methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return organizerStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyOrganizer> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(organizerStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyOrganizer> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return organizerStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyOrganizer addressBook) throws IOException {
        saveAddressBook(addressBook, organizerStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyOrganizer addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        organizerStorage.saveAddressBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(OrganizerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
