package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMoviePlanner;

/**
 * Represents a storage for {@link seedu.address.model.MoviePlanner}.
 */
public interface MoviePlannerStorage {

    /**
     * Returns the file path of the data file.
     */
    String getMoviePlannerFilePath();

    /**
     * Returns the file path of the encrypted data file
     */
    String getEncryptedMoviePlannerFilePath();

    /**
     * Returns MoviePlanner data as a {@link ReadOnlyMoviePlanner}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMoviePlanner> readMoviePlanner() throws DataConversionException, IOException;

    /**
     * @see #getMoviePlannerFilePath()
     */
    Optional<ReadOnlyMoviePlanner> readMoviePlanner(String filePath) throws DataConversionException, IOException;

    /**
     * @see #getMoviePlannerFilePath()
     */
    Optional<ReadOnlyMoviePlanner> readMoviePlanner(String filePath, String encryptedFilePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMoviePlanner} to the storage.
     *
     * @param moviePlanner cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMoviePlanner(ReadOnlyMoviePlanner moviePlanner) throws IOException;

    /**
     * @see #saveMoviePlanner(ReadOnlyMoviePlanner)
     */
    void saveMoviePlanner(ReadOnlyMoviePlanner moviePlanner, String filePath) throws IOException;

    /**
     * Saves the given (@link ReadOnlyMoviePlanner) to a fixed temporary location.
     */
    void backupMoviePlanner(ReadOnlyMoviePlanner moviePlanner) throws IOException;

}
