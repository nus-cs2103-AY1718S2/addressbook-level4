//@@author ZacZequn
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyMenu;

/**
 * Represents a storage for menu.
 */
public interface MenuStorage {

    /**
     * Returns the file path of the data file.
     */
    String getMenuFilePath();

    /**
     * Returns Menu data as a {@link ReadOnlyMenu}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMenu> readMenu() throws DataConversionException, IOException;

    /**
     * @see #getMenuFilePath()
     */
    Optional<ReadOnlyMenu> readMenu(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMenu} to the storage.
     * @param menu cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMenu(ReadOnlyMenu menu) throws IOException;

    /**
     * @see #saveMenu(ReadOnlyMenu)
     */
    void saveMenu(ReadOnlyMenu menu, String filePath) throws IOException;

}
