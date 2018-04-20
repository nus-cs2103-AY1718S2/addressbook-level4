package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.AppointmentChangedEvent;
import seedu.address.commons.events.model.ImdbChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyImdb;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ImdbStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyImdb> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyImdb addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(ImdbChangedEvent abce);

    //@@author Kyholmes
    /**
     * Saves the current version of the IMDB to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAppointmentChangedEvent(AppointmentChangedEvent ace);
}
