package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.MoviePlannerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMoviePlanner;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends MoviePlannerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getMoviePlannerFilePath();

    @Override
    String getEncryptedMoviePlannerFilePath();

    @Override
    Optional<ReadOnlyMoviePlanner> readMoviePlanner() throws DataConversionException, IOException;

    @Override
    void saveMoviePlanner(ReadOnlyMoviePlanner moviePlanner) throws IOException;

    /**
     * Saves the current version of the Movie Planner to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleMoviePlannerChangedEvent(MoviePlannerChangedEvent abce);
}
