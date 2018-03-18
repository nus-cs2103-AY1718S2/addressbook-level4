package seedu.organizer.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.organizer.commons.core.ComponentManager;
import seedu.organizer.commons.core.LogsCenter;
import seedu.organizer.commons.events.model.OrganizerChangedEvent;
import seedu.organizer.commons.events.storage.DataSavingExceptionEvent;
import seedu.organizer.commons.exceptions.DataConversionException;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.UserPrefs;

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
    public String getOrganizerFilePath() {
        return organizerStorage.getOrganizerFilePath();
    }

    @Override
    public Optional<ReadOnlyOrganizer> readOrganizer() throws DataConversionException, IOException {
        return readOrganizer(organizerStorage.getOrganizerFilePath());
    }

    @Override
    public Optional<ReadOnlyOrganizer> readOrganizer(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return organizerStorage.readOrganizer(filePath);
    }

    @Override
    public void saveOrganizer(ReadOnlyOrganizer organizer) throws IOException {
        saveOrganizer(organizer, organizerStorage.getOrganizerFilePath());
    }

    @Override
    public void saveOrganizer(ReadOnlyOrganizer organizer, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        organizerStorage.saveOrganizer(organizer, filePath);
    }


    @Override
    @Subscribe
    public void handleOrganizerChangedEvent(OrganizerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveOrganizer(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
