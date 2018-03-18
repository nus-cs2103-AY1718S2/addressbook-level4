package seedu.progresschecker.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.progresschecker.testutil.TypicalPersons.ALICE;
import static seedu.progresschecker.testutil.TypicalPersons.HOON;
import static seedu.progresschecker.testutil.TypicalPersons.IDA;
import static seedu.progresschecker.testutil.TypicalPersons.getTypicalProgressChecker;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.progresschecker.commons.exceptions.DataConversionException;
import seedu.progresschecker.commons.util.FileUtil;
import seedu.progresschecker.model.ProgressChecker;
import seedu.progresschecker.model.ReadOnlyProgressChecker;

public class XmlProgressCheckerStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlProgressCheckerStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readProgressChecker_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readProgressChecker(null);
    }

    private java.util.Optional<ReadOnlyProgressChecker> readProgressChecker(String filePath) throws Exception {
        return new XmlProgressCheckerStorage(filePath).readProgressChecker(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readProgressChecker("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readProgressChecker("NotXmlFormatProgressChecker.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readProgressChecker_invalidPersonProgressChecker_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readProgressChecker("invalidPersonProgressChecker.xml");
    }

    @Test
    public void readProgressChecker_invalidAndValidPersonProgressChecker_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readProgressChecker("invalidAndValidPersonProgressChecker.xml");
    }

    @Test
    public void readAndSaveProgressChecker_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempProgressChecker.xml";
        ProgressChecker original = getTypicalProgressChecker();
        XmlProgressCheckerStorage xmlProgressCheckerStorage = new XmlProgressCheckerStorage(filePath);

        //Save in new file and read back
        xmlProgressCheckerStorage.saveProgressChecker(original, filePath);
        ReadOnlyProgressChecker readBack = xmlProgressCheckerStorage.readProgressChecker(filePath).get();
        assertEquals(original, new ProgressChecker(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlProgressCheckerStorage.saveProgressChecker(original, filePath);
        readBack = xmlProgressCheckerStorage.readProgressChecker(filePath).get();
        assertEquals(original, new ProgressChecker(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlProgressCheckerStorage.saveProgressChecker(original); //file path not specified
        readBack = xmlProgressCheckerStorage.readProgressChecker().get(); //file path not specified
        assertEquals(original, new ProgressChecker(readBack));

    }

    @Test
    public void saveProgressChecker_nullProgressChecker_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveProgressChecker(null, "SomeFile.xml");
    }

    /**
     * Saves {@code progressChecker} at the specified {@code filePath}.
     */
    private void saveProgressChecker(ReadOnlyProgressChecker progressChecker, String filePath) {
        try {
            new XmlProgressCheckerStorage(filePath).saveProgressChecker(progressChecker,
                    addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveProgressChecker_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveProgressChecker(new ProgressChecker(), null);
    }


}
