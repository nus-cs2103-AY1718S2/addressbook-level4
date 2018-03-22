package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyBookShelf;

/**
 * Represents a storage for recently selected books.
 */
public interface RecentBooksStorage {

    /**
     * Returns the file path of the data file.
     */
    String getRecentBooksFilePath();

    /**
     * Returns recently selected books data from storage as a {@link ReadOnlyBookShelf}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyBookShelf> readRecentBooksList() throws DataConversionException, IOException;

    /**
     * Saves the given recently selected books represented as {@link ReadOnlyBookShelf}
     * to the storage.
     * @param recentBooksList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecentBooksList(ReadOnlyBookShelf recentBooksList) throws IOException;
}
