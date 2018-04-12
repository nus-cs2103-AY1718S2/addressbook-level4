package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalCoins.ALIS;
import static seedu.address.testutil.TypicalCoins.HORSE;
import static seedu.address.testutil.TypicalCoins.IDT;
import static seedu.address.testutil.TypicalCoins.getTypicalCoinBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.CoinBook;
import seedu.address.model.ReadOnlyCoinBook;

public class XmlCoinBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlCoinBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readCoinBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readCoinBook(null);
    }

    private java.util.Optional<ReadOnlyCoinBook> readCoinBook(String filePath) throws Exception {
        return new XmlCoinBookStorage(filePath).readCoinBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCoinBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readCoinBook("NotXmlFormatCoinBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readCoinBook_invalidCoinCoinBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCoinBook("invalidCoinCoinBook.xml");
    }

    @Test
    public void readCoinBook_invalidAndValidCoinCoinBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readCoinBook("invalidAndValidCoinCoinBook.xml");
    }

    @Test
    public void readAndSaveCoinBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempCoinBook.xml";
        CoinBook original = getTypicalCoinBook();
        XmlCoinBookStorage xmlCoinBookStorage = new XmlCoinBookStorage(filePath);

        //Save in new file and read back
        xmlCoinBookStorage.saveCoinBook(original, filePath);
        ReadOnlyCoinBook readBack = xmlCoinBookStorage.readCoinBook(filePath).get();
        assertEquals(original, new CoinBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addCoin(HORSE);
        original.removeCoin(ALIS);
        xmlCoinBookStorage.saveCoinBook(original, filePath);
        readBack = xmlCoinBookStorage.readCoinBook(filePath).get();
        assertEquals(original, new CoinBook(readBack));

        //Save and read without specifying file path
        original.addCoin(IDT);
        xmlCoinBookStorage.saveCoinBook(original); //file path not specified
        readBack = xmlCoinBookStorage.readCoinBook().get(); //file path not specified
        assertEquals(original, new CoinBook(readBack));

    }

    @Test
    public void saveCoinBook_nullCoinBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCoinBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code coinBook} at the specified {@code filePath}.
     */
    private void saveCoinBook(ReadOnlyCoinBook coinBook, String filePath) {
        try {
            new XmlCoinBookStorage(filePath).saveCoinBook(coinBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCoinBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveCoinBook(new CoinBook(), null);
    }


}
