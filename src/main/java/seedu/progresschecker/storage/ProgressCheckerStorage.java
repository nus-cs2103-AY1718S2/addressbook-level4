package seedu.progresschecker.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.progresschecker.commons.exceptions.DataConversionException;
import seedu.progresschecker.model.ReadOnlyProgressChecker;

/**
 * Represents a storage for {@link seedu.progresschecker.model.ProgressChecker}.
 */
public interface ProgressCheckerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getProgressCheckerFilePath();

    /**
     * Returns ProgressChecker data as a {@link ReadOnlyProgressChecker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyProgressChecker> readProgressChecker() throws DataConversionException, IOException;

    /**
     * @see #getProgressCheckerFilePath()
     */
    Optional<ReadOnlyProgressChecker> readProgressChecker(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyProgressChecker} to the storage.
     * @param progressChecker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveProgressChecker(ReadOnlyProgressChecker progressChecker) throws IOException;

    /**
     * @see #saveProgressChecker(ReadOnlyProgressChecker)
     */
    void saveProgressChecker(ReadOnlyProgressChecker progressChecker, String filePath) throws IOException;

}
