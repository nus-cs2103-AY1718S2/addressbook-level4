package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyRecipeBook;
import seedu.address.model.RecipeBook;

public class XmlRecipeBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlRecipeBookStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRecipeBook(null);
    }

    private java.util.Optional<ReadOnlyRecipeBook> readRecipeBook(String filePath) throws Exception {
        return new XmlRecipeBookStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRecipeBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRecipeBook("NotXmlFormatRecipeBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRecipeBook("invalidPersonRecipeBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRecipeBook("invalidAndValidPersonRecipeBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempRecipeBook.xml";
        RecipeBook original = getTypicalAddressBook();
        XmlRecipeBookStorage xmlRecipeBookStorage = new XmlRecipeBookStorage(filePath);

        //Save in new file and read back
        xmlRecipeBookStorage.saveAddressBook(original, filePath);
        ReadOnlyRecipeBook readBack = xmlRecipeBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new RecipeBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlRecipeBookStorage.saveAddressBook(original, filePath);
        readBack = xmlRecipeBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new RecipeBook(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlRecipeBookStorage.saveAddressBook(original); //file path not specified
        readBack = xmlRecipeBookStorage.readAddressBook().get(); //file path not specified
        assertEquals(original, new RecipeBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyRecipeBook addressBook, String filePath) {
        try {
            new XmlRecipeBookStorage(filePath).saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new RecipeBook(), null);
    }


}
