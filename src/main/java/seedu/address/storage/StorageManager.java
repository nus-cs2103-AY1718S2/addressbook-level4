package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.CustomerStatsChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCustomerStats;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data and CustomerStats data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private CustomerStatsStorage customerStatsStorage;


    public StorageManager(AddressBookStorage addressBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          CustomerStatsStorage customerStatsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.customerStatsStorage = customerStatsStorage;
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

    @Override
    public void backUpAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.backUpAddressBook(addressBook);
    }

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

    // ================ CustomerStats methods ==============================

    @Override
    public String getCustomerStatsFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public void saveCustomerStats(ReadOnlyCustomerStats customerStats) throws IOException {
        saveCustomerStats(customerStats, customerStatsStorage.getCustomerStatsFilePath());
    }

    @Override
    public void saveCustomerStats(ReadOnlyCustomerStats customerStats, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        customerStatsStorage.saveCustomerStats(customerStats, filePath);
    }

    @Override
    @Subscribe
    public void handleCustomerStatsChangedEvent(CustomerStatsChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveCustomerStats(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
