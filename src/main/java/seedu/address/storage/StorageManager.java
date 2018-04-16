package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.JournalChangedEvent;
import seedu.address.commons.events.model.PersonChangedEvent;
import seedu.address.commons.events.model.TimetableChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.ui.ShowTimetableRequestEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyJournal;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private PersonStorage personStorage;
    private JournalStorage journalStorage;
    private TimetableStorage timetableStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(PersonStorage personStorage, JournalStorage journalStorage,
                          UserPrefsStorage userPrefsStorage, TimetableStorage timetableStorage) {
        super();
        this.personStorage = personStorage;
        this.journalStorage = journalStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.timetableStorage = timetableStorage;
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


    // ================ Person methods ==============================

    @Override
    public String getPersonFilePath() {
        return personStorage.getPersonFilePath();
    }

    @Override
    public Optional<ReadOnlyPerson> readPerson() throws DataConversionException, IOException {
        return readPerson(personStorage.getPersonFilePath());
    }

    @Override
    public Optional<ReadOnlyPerson> readPerson(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return personStorage.readPerson(filePath);
    }

    @Override
    public void savePerson(ReadOnlyPerson person) throws IOException {
        savePerson(person, personStorage.getPersonFilePath());
    }

    @Override
    public void savePerson(ReadOnlyPerson person, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        personStorage.savePerson(person, filePath);
    }


    @Override
    @Subscribe
    public void handlePersonChangedEvent(PersonChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            savePerson(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ Journal methods ==============================

    //@@author traceurgan
    @Override
    public String getJournalFilePath() {
        return journalStorage.getJournalFilePath();
    }

    @Override
    public Optional<ReadOnlyJournal> readJournal() throws DataConversionException, IOException {
        return readJournal(journalStorage.getJournalFilePath());
    }

    @Override
    public Optional<ReadOnlyJournal> readJournal(String filePath) throws DataConversionException, IOException {
        return journalStorage.readJournal(filePath);
    }

    @Override
    public void saveJournal(ReadOnlyJournal journal) throws IOException {
        saveJournal(journal, journalStorage.getJournalFilePath());
        logger.info(getJournalFilePath());
    }

    @Override
    public void saveJournal(ReadOnlyJournal journal, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        journalStorage.saveJournal(journal, filePath);
    }

    /**
     * Saves the current version of the Journal Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    @Subscribe
    public void handleJournalChangedEvent(JournalChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveJournal(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ================ Timetable methods ==============================

    //@@author marlenekoh
    @Override
    public void createTimetablePageCssFile() {
        timetableStorage.createTimetablePageCssFile();
    }

    @Override
    public void setUpTimetablePageHtmlFile() {
        timetableStorage.setUpTimetablePageHtmlFile();
    }

    @Override
    public void setUpTimetableDisplayFiles(String toWrite) {
        timetableStorage.setUpTimetableDisplayFiles(toWrite);
    }

    @Override
    public void writeToFile(String toWrite, String path) {
        timetableStorage.writeToFile(toWrite, path);
    }

    @Override
    public String getFileContents(String path) throws FileNotFoundException {
        return timetableStorage.getFileContents(path);
    }

    @Override
    public String replaceLineExcludingStartEnd(String contents, String replace, String start, String end) {
        return timetableStorage.replaceLineExcludingStartEnd(contents, replace, start, end);
    }

    @Subscribe
    public void handleTimetableChangedEvent(TimetableChangedEvent event) {
        setUpTimetableDisplayFiles(event.timetable.getTimetableDisplayInfo());
        setUpTimetablePageHtmlFile();
        raise(new ShowTimetableRequestEvent());
    }
}
