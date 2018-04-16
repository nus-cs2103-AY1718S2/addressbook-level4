package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalTasks.TASKFOUR;
import static seedu.address.testutil.TypicalTasks.TASKONE;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.InvalidFileException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.TaskBook;

//@@author CYX28
public class XmlTaskBookStorageTest {

    private static final String TEST_DATA_FOLDER =
            FileUtil.getPath("./src/test/data/XmlTaskBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTaskBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTaskBook(null);
    }

    private java.util.Optional<ReadOnlyTaskBook> readTaskBook(String filePath) throws Exception {
        return new XmlTaskBookStorage(filePath).readTaskBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTaskBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readTaskBook("NotXmlFormatTaskBook.xml");
    }

    @Test
    public void readTaskBook_invalidTaskBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTaskBook("invalidTaskBook.xml");
    }

    @Test
    public void readTaskBook_invalidAndValidTaskBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTaskBook("invalidAndValidTaskBook.xml");
    }

    @Test
    public void readAndSaveTaskBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempTaskBook.xml";
        TaskBook original = getTypicalTaskBook();
        XmlTaskBookStorage xmlTaskBookStorage = new XmlTaskBookStorage(filePath);

        //Save in new file and read back
        xmlTaskBookStorage.saveTaskBook(original, filePath);
        ReadOnlyTaskBook readBack = xmlTaskBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new TaskBook(readBack));

        //Modify tast data, overwrite exiting file, and read back
        original.addTask(TASKFOUR);
        original.removeTask(TASKONE);
        xmlTaskBookStorage.saveTaskBook(original, filePath);
        readBack = xmlTaskBookStorage.readTaskBook(filePath).get();
        assertEquals(original, new TaskBook(readBack));
    }

    @Test
    public void saveTaskBook_nullTaskBook_throwsNullPointerException() throws InvalidFileException {
        thrown.expect(NullPointerException.class);
        saveTaskBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code taskBook} at the specified {@code filePath}.
     */
    private void saveTaskBook(ReadOnlyTaskBook taskBook, String filePath) throws InvalidFileException {
        try {
            new XmlTaskBookStorage(filePath).saveTaskBook(taskBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaskBook_nullFilePath_throwsNullPointerException() throws IOException, InvalidFileException {
        thrown.expect(NullPointerException.class);
        saveTaskBook(new TaskBook(), null);
    }

}
