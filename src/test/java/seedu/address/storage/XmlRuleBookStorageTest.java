package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalRules.ALIS;
import static seedu.address.testutil.TypicalRules.EQUAL;
import static seedu.address.testutil.TypicalRules.INCR;
import static seedu.address.testutil.TypicalRules.getTypicalRuleBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyRuleBook;
import seedu.address.model.RuleBook;

public class XmlRuleBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlRuleBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readRuleBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRuleBook(null);
    }

    private java.util.Optional<ReadOnlyRuleBook> readRuleBook(String filePath) throws Exception {
        return new XmlRuleBookStorage(filePath).readRuleBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRuleBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRuleBook("NotXmlFormatRuleBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readRuleBook_invalidRuleRuleBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRuleBook("invalidRuleRuleBook.xml");
    }

    @Test
    public void readRuleBook_invalidAndValidRuleRuleBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRuleBook("invalidAndValidRuleRuleBook.xml");
    }

    @Test
    public void readAndSaveRuleBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempRuleBook.xml";
        RuleBook original = getTypicalRuleBook();
        XmlRuleBookStorage xmlRuleBookStorage = new XmlRuleBookStorage(filePath);

        //Save in new file and read back
        xmlRuleBookStorage.saveRuleBook(original, filePath);
        ReadOnlyRuleBook readBack = xmlRuleBookStorage.readRuleBook(filePath).get();
        assertEquals(original, new RuleBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addRule(EQUAL);
        original.removeRule(ALIS);
        xmlRuleBookStorage.saveRuleBook(original, filePath);
        readBack = xmlRuleBookStorage.readRuleBook(filePath).get();
        assertEquals(original, new RuleBook(readBack));

        //Save and read without specifying file path
        original.addRule(INCR);
        xmlRuleBookStorage.saveRuleBook(original); //file path not specified
        readBack = xmlRuleBookStorage.readRuleBook().get(); //file path not specified
        assertEquals(original, new RuleBook(readBack));

    }

    @Test
    public void saveRuleBook_nullRuleBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRuleBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code coinBook} at the specified {@code filePath}.
     */
    private void saveRuleBook(ReadOnlyRuleBook coinBook, String filePath) {
        try {
            new XmlRuleBookStorage(filePath).saveRuleBook(coinBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRuleBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveRuleBook(new RuleBook(), null);
    }


}
