package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ScheduleChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.ProfilePictureChangeEvent;
import seedu.address.commons.events.storage.RequiredStudentIndexChangeEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.miscellaneousInfo.ProfilePicturePath;
import seedu.address.model.student.Student;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private XmlRequiredIndexStorage  xmlRequiredIndexStorage;
    private ScheduleStorage scheduleStorage;
    private ProfilePictureStorage profilePictureStorage;


    public StorageManager(AddressBookStorage addressBookStorage,
                          UserPrefsStorage userPrefsStorage, ScheduleStorage scheduleStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        xmlRequiredIndexStorage = new XmlRequiredIndexStorage("out/production/resources/view/requiredStudentIndex.xml");
        this.scheduleStorage = scheduleStorage;
        profilePictureStorage = new ProfilePictureStorage("data/profilePictures");

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
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        addressBookStorage.backupAddressBook(addressBook);
    }

    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Local addressbook data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }


    /**
     * Saves the required index of the {@code Student}
     * @param newIndex
     * @throws IOException
     */
    public void saveRequiredIndex(int newIndex) throws IOException {
        String requiredIndexFilePath = xmlRequiredIndexStorage.getFilePath();
        logger.fine("Attempting to write to data file: " + requiredIndexFilePath);
        XmlRequiredIndexStorage.updateData(newIndex, requiredIndexFilePath);
    }

    /**
     * Handles the event where the required student index for displaying misc info is changed
     */
    @Override
    @Subscribe
    public void handleRequiredStudentIndexChangedEvent(RequiredStudentIndexChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRequiredIndex(event.getNewIndex());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    /**
     * Saves the new profile picture of the {@code Student}
     * @throws IOException
     */
    public void saveProfilePicture(ProfilePicturePath pathToChangeTo, Student student) throws IOException {
        ensureProfilePictureStorageExist();

        Path newPath = pathToChangeTo.getProfilePicturePath();
        String extension = pathToChangeTo.getExtension();

        Path studentPictureFilePath = Paths.get(profilePictureStorage.getFilePath() + "/"
                + student.getUniqueKey().toString());
        deleteExistingProfilePicture(studentPictureFilePath);
        Path studentPictureFilePathWithExtension = Paths.get(studentPictureFilePath.toString() + extension);
        logger.fine("Attempting to write to data file: data/" + student.getUniqueKey().toString());


        Files.copy(newPath, studentPictureFilePathWithExtension);

    }


    /**
     * Deletes the existing profile picture
     */
    private void deleteExistingProfilePicture(Path studentPictureFilePath) {
        File tobeReplacedWithJpg = new File(studentPictureFilePath.toString() + ".jpg");
        File tobeReplacedWithPng = new File(studentPictureFilePath.toString() + ".png");

        if (tobeReplacedWithJpg.exists()) {
            tobeReplacedWithJpg.delete();
        }
        else {
            tobeReplacedWithPng.delete();
        }

    }

    /**
     * Makes a picture storage folder if it does not already exist.
     */
    private void ensureProfilePictureStorageExist() {
        if(!profilePictureStorage.storageFileExist()) {
            File pictureStorage = new File(profilePictureStorage.getFilePath());
            pictureStorage.mkdir();

        }
    }

    @Override
    @Subscribe
    public void handleProfilePictureChangeEvent(ProfilePictureChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveProfilePicture(event.getUrlToChangeTo(), event.getStudent());
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }

    }

    // ================ Schedule methods ==============================

    @Override
    public String getScheduleFilePath() {
        return scheduleStorage.getScheduleFilePath();
    }

    @Override
    public Optional<ReadOnlySchedule> readSchedule() throws DataConversionException, IOException {
        return readSchedule(scheduleStorage.getScheduleFilePath());
    }

    @Override
    public Optional<ReadOnlySchedule> readSchedule(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read schedule data from file: " + filePath);
        return scheduleStorage.readSchedule(filePath);
    }

    @Override
    public void saveSchedule(ReadOnlySchedule schedule) throws IOException {
        saveSchedule(schedule, scheduleStorage.getScheduleFilePath());
    }

    @Override
    public void saveSchedule(ReadOnlySchedule schedule, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        scheduleStorage.saveSchedule(schedule, filePath);
    }

    @Override
    public void backupSchedule(ReadOnlySchedule schedule) throws IOException {
        scheduleStorage.backupSchedule(schedule);
    }
    /**
     * TODO implement this later
     */
    @Override
    @Subscribe
    public void handleScheduleChangedEvent(ScheduleChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local schedule data changed, saving to file"));
        try {
            saveSchedule(event.data);

        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
