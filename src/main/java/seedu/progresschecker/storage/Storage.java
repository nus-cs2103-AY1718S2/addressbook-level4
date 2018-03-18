package seedu.progresschecker.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.progresschecker.commons.events.model.ProgressCheckerChangedEvent;
import seedu.progresschecker.commons.events.storage.DataSavingExceptionEvent;
import seedu.progresschecker.commons.exceptions.DataConversionException;
import seedu.progresschecker.model.ReadOnlyProgressChecker;
import seedu.progresschecker.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ProgressCheckerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getProgressCheckerFilePath();

    @Override
    Optional<ReadOnlyProgressChecker> readProgressChecker() throws DataConversionException, IOException;

    @Override
    void saveProgressChecker(ReadOnlyProgressChecker progressChecker) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleProgressCheckerChangedEvent(ProgressCheckerChangedEvent abce);
}
