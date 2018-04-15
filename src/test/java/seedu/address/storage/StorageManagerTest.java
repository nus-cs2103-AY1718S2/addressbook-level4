package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;
import static seedu.address.testutil.TypicalRules.getTypicalRuleBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.CoinBookChangedEvent;
import seedu.address.commons.events.model.RuleBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.CoinBook;
import seedu.address.model.ReadOnlyCoinBook;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlCoinBookStorage addressBookStorage = new XmlCoinBookStorage(getTempFilePath("ab"));
        XmlRuleBookStorage ruleBookStorage = new XmlRuleBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, ruleBookStorage, userPrefsStorage);
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
    public void coinBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlCoinBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlCoinBookStorageTest} class.
         */
        CoinBook original = getTypicalCoinBook();
        storageManager.saveCoinBook(original);
        ReadOnlyCoinBook retrieved = storageManager.readCoinBook().get();
        assertEquals(original, new CoinBook(retrieved));
    }

    @Test
    public void getCoinBookFilePath() {
        assertNotNull(storageManager.getCoinBookFilePath());
    }

    @Test
    public void handleCoinBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlCoinBookStorageExceptionThrowingStub("dummy"),
                                             new XmlRuleBookStorage("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleCoinBookChangedEvent(new CoinBookChangedEvent(new CoinBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    @Test
    public void ruleBookReadSave() throws Exception {
        RuleBook original = getTypicalRuleBook();
        storageManager.saveRuleBook(original);
        ReadOnlyRuleBook retrieved = storageManager.readRuleBook().get();
        assertEquals(original, new RuleBook(retrieved));
    }

    @Test
    public void getRuleBookFilePath() {
        assertNotNull(storageManager.getRuleBookFilePath());
    }

    @Test
    public void handleRuleBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlCoinBookStorage("dummy"),
                                             new XmlRuleBookStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleRuleBookChangedEvent(new RuleBookChangedEvent(new RuleBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlCoinBookStorageExceptionThrowingStub extends XmlCoinBookStorage {

        public XmlCoinBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveCoinBook(ReadOnlyCoinBook addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlRuleBookStorageExceptionThrowingStub extends XmlRuleBookStorage {

        public XmlRuleBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveRuleBook(ReadOnlyRuleBook ruleBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
