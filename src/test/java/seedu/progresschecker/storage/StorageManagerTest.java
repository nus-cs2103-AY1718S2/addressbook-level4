package seedu.progresschecker.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.progresschecker.testutil.TypicalPersons.getTypicalProgressChecker;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.progresschecker.commons.events.model.ProgressCheckerChangedEvent;
import seedu.progresschecker.commons.events.storage.DataSavingExceptionEvent;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.ReadOnlyProgressChecker;
import seedu.progresschecker.model.UserPrefs;
import seedu.progresschecker.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlProgressCheckerStorage progressCheckerStorage = new XmlProgressCheckerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(progressCheckerStorage, userPrefsStorage);
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
    public void progressCheckerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlProgressCheckerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlProgressCheckerStorageTest} class.
         */
        ProgressChecker original = getTypicalProgressChecker();
        storageManager.saveProgressChecker(original);
        ReadOnlyProgressChecker retrieved = storageManager.readProgressChecker().get();
        assertEquals(original, new ProgressChecker(retrieved));
    }

    @Test
    public void getProgressCheckerFilePath() {
        assertNotNull(storageManager.getProgressCheckerFilePath());
    }

    @Test
    public void handleProgressCheckerChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlProgressCheckerStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleProgressCheckerChangedEvent(new ProgressCheckerChangedEvent(new ProgressChecker()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlProgressCheckerStorageExceptionThrowingStub extends XmlProgressCheckerStorage {

        public XmlProgressCheckerStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveProgressChecker(ReadOnlyProgressChecker progressChecker, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
