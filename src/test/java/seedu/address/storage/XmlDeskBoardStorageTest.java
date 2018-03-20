package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT1;
import static seedu.address.testutil.TypicalActivities.ASSIGNMENT3;
import static seedu.address.testutil.TypicalActivities.DEMO1;
import static seedu.address.testutil.TypicalActivities.getTypicalDeskBoard;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.DeskBoard;
import seedu.address.model.ReadOnlyDeskBoard;

public class XmlDeskBoardStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlDeskBoardStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readDeskBoard_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readDeskBoard(null);
    }

    private java.util.Optional<ReadOnlyDeskBoard> readDeskBoard(String filePath) throws Exception {
        return new XmlDeskBoardStorage(filePath).readDeskBoard(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDeskBoard("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readDeskBoard("NotXmlFormatDeskBoard.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readDeskBoard_invalidActivityDeskBoard_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDeskBoard("invalidActivityDeskBoard.xml");
    }

    @Test
    public void readDeskBoard_invalidAndValidActivityDeskBoard_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDeskBoard("invalidAndValidActivityDeskBoard.xml");
    }

    @Test
    public void readAndSaveDeskBoard_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempDeskBoard.xml";
        DeskBoard original = getTypicalDeskBoard();
        XmlDeskBoardStorage xmlDeskBoardStorage = new XmlDeskBoardStorage(filePath);

        //Save in new file and read back
        xmlDeskBoardStorage.saveDeskBoard(original, filePath);
        ReadOnlyDeskBoard readBack = xmlDeskBoardStorage.readDeskBoard(filePath).get();
        assertEquals(original, new DeskBoard(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addActivity(ASSIGNMENT3);
        original.removeActivity(ASSIGNMENT1);
        xmlDeskBoardStorage.saveDeskBoard(original, filePath);
        readBack = xmlDeskBoardStorage.readDeskBoard(filePath).get();
        assertEquals(original, new DeskBoard(readBack));

        //Save and read without specifying file path
        original.addActivity(DEMO1);
        xmlDeskBoardStorage.saveDeskBoard(original); //file path not specified
        readBack = xmlDeskBoardStorage.readDeskBoard().get(); //file path not specified
        assertEquals(original, new DeskBoard(readBack));

    }

    @Test
    public void saveDeskBoard_nullDeskBoard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDeskBoard(null, "SomeFile.xml");
    }

    /**
     * Saves {@code DeskBoard} at the specified {@code filePath}.
     */
    private void saveDeskBoard(ReadOnlyDeskBoard DeskBoard, String filePath) {
        try {
            new XmlDeskBoardStorage(filePath).saveDeskBoard(DeskBoard, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDeskBoard_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveDeskBoard(new DeskBoard(), null);
    }


}
