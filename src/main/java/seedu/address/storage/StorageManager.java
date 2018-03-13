package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.MoviePlannerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMoviePlanner;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of MoviePlanner data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private MoviePlannerStorage moviePlannerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(MoviePlannerStorage moviePlannerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.moviePlannerStorage = moviePlannerStorage;
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


    // ================ MoviePlanner methods ==============================

    @Override
    public String getMoviePlannerFilePath() {
        return moviePlannerStorage.getMoviePlannerFilePath();
    }

    @Override
    public Optional<ReadOnlyMoviePlanner> readMoviePlanner() throws DataConversionException, IOException {
        return readMoviePlanner(moviePlannerStorage.getMoviePlannerFilePath());
    }

    @Override
    public Optional<ReadOnlyMoviePlanner> readMoviePlanner(String filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return moviePlannerStorage.readMoviePlanner(filePath);
    }

    @Override
    public void saveMoviePlanner(ReadOnlyMoviePlanner moviePlanner) throws IOException {
        saveMoviePlanner(moviePlanner, moviePlannerStorage.getMoviePlannerFilePath());
    }

    @Override
    public void saveMoviePlanner(ReadOnlyMoviePlanner moviePlanner, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        moviePlannerStorage.saveMoviePlanner(moviePlanner, filePath);
    }


    @Override
    @Subscribe
    public void handleMoviePlannerChangedEvent(MoviePlannerChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveMoviePlanner(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    public void backupMoviePlanner(ReadOnlyMoviePlanner moviePlanner) throws IOException {
        moviePlannerStorage.backupMoviePlanner(moviePlanner);
    }

}
