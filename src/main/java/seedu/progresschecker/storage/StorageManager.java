package seedu.progresschecker.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.progresschecker.commons.core.ComponentManager;
import seedu.progresschecker.commons.core.LogsCenter;
import seedu.progresschecker.commons.events.model.ProgressCheckerChangedEvent;
import seedu.progresschecker.commons.events.storage.DataSavingExceptionEvent;
import seedu.progresschecker.commons.exceptions.DataConversionException;
import seedu.progresschecker.model.ReadOnlyProgressChecker;
import seedu.progresschecker.model.UserPrefs;

/**
 * Manages storage of ProgressChecker data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProgressCheckerStorage progressCheckerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ProgressCheckerStorage progressCheckerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.progressCheckerStorage = progressCheckerStorage;
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


    // ================ ProgressChecker methods ==============================

    @Override
    public String getProgressCheckerFilePath() {
        return progressCheckerStorage.getProgressCheckerFilePath();
    }

    @Override
    public Optional<ReadOnlyProgressChecker> readProgressChecker() throws DataConversionException, IOException {
        return readProgressChecker(progressCheckerStorage.getProgressCheckerFilePath());
    }

    @Override
    public Optional<ReadOnlyProgressChecker> readProgressChecker(String filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return progressCheckerStorage.readProgressChecker(filePath);
    }

    @Override
    public void saveProgressChecker(ReadOnlyProgressChecker progressChecker) throws IOException {
        saveProgressChecker(progressChecker, progressCheckerStorage.getProgressCheckerFilePath());
    }

    @Override
    public void saveProgressChecker(ReadOnlyProgressChecker progressChecker, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        progressCheckerStorage.saveProgressChecker(progressChecker, filePath);
    }


    @Override
    @Subscribe
    public void handleProgressCheckerChangedEvent(ProgressCheckerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveProgressChecker(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
