package seedu.ptman.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.ptman.testutil.TypicalEmployees.getTypicalPartTimeManager;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.ptman.commons.events.model.OutletDataChangedEvent;
import seedu.ptman.commons.events.model.PartTimeManagerChangedEvent;
import seedu.ptman.commons.events.storage.DataSavingExceptionEvent;
import seedu.ptman.model.PartTimeManager;
import seedu.ptman.model.ReadOnlyPartTimeManager;
import seedu.ptman.model.UserPrefs;
import seedu.ptman.model.outlet.OutletInformation;
import seedu.ptman.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlPartTimeManagerStorage partTimeManagerStorage = new XmlPartTimeManagerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        XmlOutletInformationStorage outletInformationStorage =
                new XmlOutletInformationStorage(getTempFilePath("outlet"));
        storageManager = new StorageManager(partTimeManagerStorage, userPrefsStorage, outletInformationStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void partTimeManagerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlPartTimeManagerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlPartTimeManagerStorageTest} class.
         */
        PartTimeManager original = getTypicalPartTimeManager();
        storageManager.savePartTimeManager(original);
        ReadOnlyPartTimeManager retrieved = storageManager.readPartTimeManager(false).get();
        assertEquals(original, new PartTimeManager(retrieved));
    }


    @Test
    public void getPartTimeManagerFilePath() {
        assertNotNull(storageManager.getPartTimeManagerFilePath());
    }

    @Test
    public void getUserOrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void handlePartTimeManagerChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlPartTimeManagerStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"),
                new XmlOutletInformationStorage("dummy"));
        storage.handlePartTimeManagerChangedEvent(new PartTimeManagerChangedEvent(new PartTimeManager()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    //@@author SunBangjie
    @Test
    public void outletInformationReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlOutletInformationStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlOutletInformationStorageTest} class.
         */
        OutletInformation original = new OutletInformation();
        storageManager.saveOutletInformation(original);
        OutletInformation retrieved = storageManager.readOutletInformation().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void getOutletInformationFilePath() {
        assertNotNull(storageManager.getOutletInformationFilePath());
    }

    @Test
    public void handleOutletDataChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlPartTimeManagerStorage("dummy"),
                new JsonUserPrefsStorage("dummy"),
                new XmlOutletInformationStorageExceptionThrowingStub("dummy"));
        storage.handleOutletDataChangedEvent(new OutletDataChangedEvent(new OutletInformation()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleOutletDataChangedEvent_validInput_eventRaised() throws Exception {
        OutletInformation original = new OutletInformation();
        XmlOutletInformationStorage outletInformationStorage = new XmlOutletInformationStorage("dummy");
        Storage storage = new StorageManager(new XmlPartTimeManagerStorage("dummy"),
                new JsonUserPrefsStorage("dummy"),
                outletInformationStorage);
        storage.handleOutletDataChangedEvent(new OutletDataChangedEvent(original));
        OutletInformation readBack = outletInformationStorage
                .readOutletInformation("dummy").get();
        assertEquals(original, readBack);
    }

    @Test
    public void backupPartTimeManager_nullValue_exceptionThrown() throws Exception {
        thrown.expect(NullPointerException.class);
        storageManager.backupPartTimeManager(null);
    }

    @Test
    public void backupPartTimeManager_validValue_success() throws Exception {
        PartTimeManager original = getTypicalPartTimeManager();
        storageManager.backupPartTimeManager(original);
        XmlPartTimeManagerStorage partTimeManagerStorage = new XmlPartTimeManagerStorage(getTempFilePath("ab"));
        ReadOnlyPartTimeManager readBack =
                partTimeManagerStorage.readPartTimeManager(false, getTempFilePath("ab.backup")).get();
        assertEquals(original, new PartTimeManager(readBack));
    }

    @Test
    public void backupOutletInformation_nullValue_exceptionThrown() throws Exception {
        thrown.expect(NullPointerException.class);
        storageManager.backupOutletInformation(null);
    }

    @Test
    public void backupOutletInformation_validValue_success() throws Exception {
        OutletInformation original = new OutletInformation();
        storageManager.backupOutletInformation(original);
        XmlOutletInformationStorage outletInformationStorage =
                new XmlOutletInformationStorage(getTempFilePath("outlet"));
        OutletInformation readBack = outletInformationStorage
                .readOutletInformation(getTempFilePath("outlet.backup")).get();
        assertEquals(original, readBack);
    }

    //@@author

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlPartTimeManagerStorageExceptionThrowingStub extends XmlPartTimeManagerStorage {

        public XmlPartTimeManagerStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void savePartTimeManager(ReadOnlyPartTimeManager partTimeManager, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlOutletInformationStorageExceptionThrowingStub extends XmlOutletInformationStorage {

        public XmlOutletInformationStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveOutletInformation(OutletInformation outletInformation, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
