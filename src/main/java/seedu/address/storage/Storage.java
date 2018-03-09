package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.BookShelfChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, BookShelfStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getBookShelfFilePath();

    @Override
    Optional<ReadOnlyBookShelf> readBookShelf() throws DataConversionException, IOException;

    @Override
    void saveBookShelf(ReadOnlyBookShelf bookShelf) throws IOException;

    /**
     * Saves the current version of the Book Shelf to the hard disk. Creates a new file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleBookShelfChangedEvent(BookShelfChangedEvent bsce);

    //// deprecated

    @Deprecated
    @Override
    String getAddressBookFilePath();

    @Deprecated
    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Deprecated
    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    @Deprecated
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
