package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.CalendarManagerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCalendarManager;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private CalendarManagerStorage calendarManagerStorage;


    public StorageManager(AddressBookStorage addressBookStorage, CalendarManagerStorage calendarManagerStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.calendarManagerStorage = calendarManagerStorage;
        this.userPrefsStorage = userPrefsStorage;
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
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================== CalendarManager methods ==============================

    //@@author SuxianAlicia
    @Override
    public String getCalendarManagerFilePath() {
        return calendarManagerStorage.getCalendarManagerFilePath();
    }

    @Override
    public Optional<ReadOnlyCalendarManager> readCalendarManager() throws DataConversionException, IOException {
        return readCalendarManager(calendarManagerStorage.getCalendarManagerFilePath());
    }

    @Override
    public Optional<ReadOnlyCalendarManager> readCalendarManager(String filePath)
            throws DataConversionException, IOException {

        logger.fine("Attempting to read calendar data from file: " + filePath);
        return calendarManagerStorage.readCalendarManager(filePath);
    }

    @Override
    public void saveCalendarManager(ReadOnlyCalendarManager calendarManager) throws IOException {
        saveCalendarManager(calendarManager, calendarManagerStorage.getCalendarManagerFilePath());
    }

    @Override
    public void saveCalendarManager(ReadOnlyCalendarManager calendarManager, String filePath) throws IOException {
        logger.fine("Attempting to write to calendar data file: " + filePath);
        calendarManagerStorage.saveCalendarManager(calendarManager, filePath);
    }
    //@@author


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    //@@author SuxianAlicia
    @Override
    @Subscribe
    public void handleCalendarManagerChangedEvent(CalendarManagerChangedEvent event) {
        logger.info(LogsCenter
                .getEventHandlingLogMessage(event, "Local calendar data changed, saving to file"));

        try {
            saveCalendarManager(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    //@@author
}
