package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EventBook;
import seedu.address.model.event.ReadOnlyEventBook;

//@@author x3tsunayh

public class XmlEventBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil
            .getPath("./src/test/data/XmlEventBookStorageTest/");

    private static final String HEADER = "Title,Description,Location,Datetime";
    private static final String EXPORT_DATA = "TempEventBook.csv";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEventBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEventBook(null);
    }

    private java.util.Optional<ReadOnlyEventBook> readEventBook(String filePath) throws Exception {
        return new XmlEventBookStorage(filePath)
                .readEventBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void readAndSaveEventBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempEventBook.xml";
        EventBook original = getTypicalEventBook();
        XmlEventBookStorage xmlEventBookStorage = new XmlEventBookStorage(filePath);

        // saves in new file and read again; ensures data integrity
        xmlEventBookStorage.saveEventBook(original, filePath);
        ReadOnlyEventBook readBack = xmlEventBookStorage.readEventBook(filePath).get();
        assertEquals(original, new EventBook(readBack));


    }

    @Test
    public void saveEventBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventBook(null, "SomeFile.xml");
    }

    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException()
            throws IllegalValueException, CommandException {
        XmlSerializableEventBook eventBook = new XmlSerializableEventBook();
        thrown.expect(UnsupportedOperationException.class);
        eventBook.toModelType().getEventList().remove(0);
    }

    /**
     * Saves {@code eventBook} at the specified {@code filePath}.
     */
    private void saveEventBook(ReadOnlyEventBook eventBook, String filePath) {
        try {
            new XmlEventBookStorage(filePath)
                    .saveEventBook(eventBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should be no error writing to this file.", ioe);
        }
    }

    @Test
    public void saveEventBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveEventBook(new EventBook(), null);
    }
}
