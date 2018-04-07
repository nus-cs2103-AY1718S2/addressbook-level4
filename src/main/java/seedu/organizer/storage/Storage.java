package seedu.organizer.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.organizer.commons.events.model.OrganizerChangedEvent;
import seedu.organizer.commons.events.storage.DataSavingExceptionEvent;
import seedu.organizer.commons.exceptions.DataConversionException;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends OrganizerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getOrganizerFilePath();

    @Override
    Optional<ReadOnlyOrganizer> readOrganizer() throws DataConversionException, IOException;

    @Override
    void saveOrganizer(ReadOnlyOrganizer organizer) throws IOException;

    /**
     * Saves the current version of the Organizer to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleOrganizerChangedEvent(OrganizerChangedEvent abce);
}
