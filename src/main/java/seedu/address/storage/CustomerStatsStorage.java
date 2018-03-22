package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCustomerStats;

/**
 * Represents a storage for customers' orders count.
 */
public interface CustomerStatsStorage {

    /**
     * Returns the file path of the data file.
     */
    String getCustomerStatsFilePath();

    /**
     * Returns CustomerStats data as a {@link ReadOnlyCustomerStats}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCustomerStats> readCustomerStats() throws DataConversionException, IOException;

    /**
     * @see #getCustomerStatsFilePath()
     */
    Optional<ReadOnlyCustomerStats> readCustomerStats(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCustomerStats} to the storage.
     * @param customerStats cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCustomerStats(ReadOnlyCustomerStats customerStats) throws IOException;

    /**
     * @see #saveCustomerStats(ReadOnlyCustomerStats)
     */
    void saveCustomerStats(ReadOnlyCustomerStats customerStats, String filePath) throws IOException;

}
