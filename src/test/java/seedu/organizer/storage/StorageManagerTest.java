package seedu.organizer.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.organizer.commons.events.model.OrganizerChangedEvent;
import seedu.organizer.commons.events.storage.DataSavingExceptionEvent;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlOrganizerStorage organizerStorage = new XmlOrganizerStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(organizerStorage, userPrefsStorage);
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
    public void organizerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlOrganizerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlOrganizerStorageTest} class.
         */
        Organizer original = getTypicalOrganizer();
        storageManager.saveOrganizer(original);
        ReadOnlyOrganizer retrieved = storageManager.readOrganizer().get();
        assertEquals(original, new Organizer(retrieved));
    }

    @Test
    public void getOrganizerFilePath() {
        assertNotNull(storageManager.getOrganizerFilePath());
    }

    @Test
    public void handleOrganizerChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlOrganizerStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"));
        storage.handleOrganizerChangedEvent(new OrganizerChangedEvent(new Organizer()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlOrganizerStorageExceptionThrowingStub extends XmlOrganizerStorage {

        public XmlOrganizerStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveOrganizer(ReadOnlyOrganizer organizer, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
