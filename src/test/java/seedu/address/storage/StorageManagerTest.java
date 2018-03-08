package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.BookShelfChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;
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
        XmlBookShelfStorage bookShelfStorage = new XmlBookShelfStorage(getTempFilePath("biblio"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(bookShelfStorage, userPrefsStorage);
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
    public void bookShelfReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlBookShelfStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlBookShelfStorageTest} class.
         */
        BookShelf original = getTypicalBookShelf();
        storageManager.saveBookShelf(original);
        ReadOnlyBookShelf retrieved = storageManager.readBookShelf().get();
        assertEquals(original, new BookShelf(retrieved));
    }

    @Test
    public void getBookShelfFilePath() {
        assertNotNull(storageManager.getBookShelfFilePath());
    }

    @Test
    public void handleBookShelfChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlBookShelfStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleBookShelfChangedEvent(new BookShelfChangedEvent(new BookShelf()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlBookShelfStorageExceptionThrowingStub extends XmlBookShelfStorage {

        public XmlBookShelfStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveBookShelf(ReadOnlyBookShelf bookShelf, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
