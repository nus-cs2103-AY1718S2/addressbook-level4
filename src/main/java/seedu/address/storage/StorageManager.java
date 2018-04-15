package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.InfoPanelEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.building.Building;
import seedu.address.model.building.Room;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private ReadOnlyVenueInformation venueInformationStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage,
                          ReadOnlyVenueInformation venueInformationStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.venueInformationStorage = venueInformationStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException,
                                                                            WrongPasswordException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException,
                                                                            WrongPasswordException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Password password)
            throws DataConversionException, IOException, WrongPasswordException {
        return addressBookStorage.readAddressBook(addressBookStorage.getAddressBookFilePath(), password);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath, Password password)
            throws DataConversionException, IOException, WrongPasswordException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath, password);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException, WrongPasswordException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException,
                                                                            WrongPasswordException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    //@@author AzuraAiR-reused
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException, WrongPasswordException {
        logger.fine("Attempting to write to backup data file: ");
        addressBookStorage.backupAddressBook(addressBook);
    }
    //@@author

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
            raise(new InfoPanelEvent());
        } catch (WrongPasswordException wpe) {
            logger.severe("Unable to save due to wrong password. Should not happen.");
            raise(new DataSavingExceptionEvent(wpe));
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }


    // ================ VenueInformation methods ==============================

    //@@author Caijun7
    @Override
    public String getVenueInformationFilePath() {
        return venueInformationStorage.getVenueInformationFilePath();
    }

    @Override
    public Optional<Room> readVenueInformation() throws DataConversionException, IOException {
        return venueInformationStorage.readVenueInformation();
    }

    @Override
    public Optional<Building> readBuildingsAndRoomsInformation() throws DataConversionException, IOException {
        return venueInformationStorage.readBuildingsAndRoomsInformation();
    }
    //@@author

}
