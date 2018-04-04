package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalCinemas.getTypicalMoviePlanner;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.MoviePlannerChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.MoviePlanner;
import seedu.address.model.ReadOnlyMoviePlanner;
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
        XmlMoviePlannerStorage moviePlannerStorage = new XmlMoviePlannerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(moviePlannerStorage, userPrefsStorage);
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
    public void moviePlannerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlMoviePlannerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlMoviePlannerStorageTest} class.
         */
        MoviePlanner original = getTypicalMoviePlanner();
        storageManager.saveMoviePlanner(original);
        ReadOnlyMoviePlanner retrieved = storageManager.readMoviePlanner().get();
        assertEquals(original, new MoviePlanner(retrieved));
    }

    @Test
    public void getMoviePlannerFilePath() {
        assertNotNull(storageManager.getMoviePlannerFilePath());
    }

    @Test
    public void handleMoviePlannerChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlMoviePlannerStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleMoviePlannerChangedEvent(new MoviePlannerChangedEvent(new MoviePlanner()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlMoviePlannerStorageExceptionThrowingStub extends XmlMoviePlannerStorage {

        public XmlMoviePlannerStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveMoviePlanner(ReadOnlyMoviePlanner moviePlanner, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
