package seedu.address.storage;

import java.io.IOException;

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
