package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.EventBookChangedEvent;
import seedu.address.commons.events.model.TaskBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.ExistingFileException;
import seedu.address.commons.exceptions.InvalidFileException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEventBook;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, EventBookStorage, TaskBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    void exportAddressBook(ReadOnlyAddressBook addressBook)
            throws IOException, InvalidFileException, ExistingFileException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce) throws InvalidFileException;

    @Override
    String getEventBookFilePath();

    @Override
    Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException;

    @Override
    void saveEventBook(ReadOnlyEventBook eventBook) throws IOException;

    @Override
    void exportEventBook() throws FileNotFoundException, ParserConfigurationException, IOException;

    /**
     * Saves the current version of the Event Book to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleEventBookChangedEvent(EventBookChangedEvent event);

    @Override
    String getTaskBookFilePath();

    @Override
    Optional<ReadOnlyTaskBook> readTaskBook() throws DataConversionException, IOException;

    @Override
    void saveTaskBook(ReadOnlyTaskBook taskBook) throws IOException;

    void exportTaskBook() throws ParserConfigurationException, IOException;

    /**
     * Saves the current version of the Task Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleTaskBookChangedEvent(TaskBookChangedEvent tbce) throws InvalidFileException;

}
