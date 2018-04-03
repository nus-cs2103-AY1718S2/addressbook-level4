package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.building.Building;
import seedu.address.model.building.Room;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, ReadOnlyVenueInformation {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException, WrongPasswordException;

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook(Password password)
            throws DataConversionException, IOException, WrongPasswordException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException, WrongPasswordException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    //@@author Caijun7
    @Override
    Optional<Room> readVenueInformation() throws DataConversionException, IOException;

    @Override
    Optional<Building> readBuildingsAndRoomsInformation() throws DataConversionException, IOException;
    //@@author
}
