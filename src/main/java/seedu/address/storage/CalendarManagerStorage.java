package seedu.address.storage;
//@@author SuxianAlicia
import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCalendarManager;

/**
 * Represents a storage for {@link seedu.address.model.CalendarManager}.
 */
public interface CalendarManagerStorage {
    /**
     * Returns the file path of the data file.
     */
    String getCalendarManagerFilePath();

    /**
     * Returns CalendarManager data as a {@link ReadOnlyCalendarManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCalendarManager> readCalendarManager() throws DataConversionException, IOException;

    /**
     * @see #getCalendarManagerFilePath()
     */
    Optional<ReadOnlyCalendarManager> readCalendarManager(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCalendarManager} to the storage.
     * @param calendarManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCalendarManager(ReadOnlyCalendarManager calendarManager) throws IOException;

    /**
     * @see #saveCalendarManager(ReadOnlyCalendarManager)
     */
    void saveCalendarManager(ReadOnlyCalendarManager calendarManager, String filePath) throws IOException;

}
