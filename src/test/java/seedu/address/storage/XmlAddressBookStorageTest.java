package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.SecurityUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyAddressBook;

public class XmlAddressBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");
    private static final String TEST_PASSWORD = "test";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new XmlAddressBookStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath, Password password)
                                                                            throws Exception {
        return new XmlAddressBookStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath), password);
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBookWithPassword_invalidAndValidPersonAddressBook_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml", new Password("test"));
    }

    @Test
    public void readAddressBookWithPassword_wrongPassword_throwWrongPasswordException() throws Exception {
        String filePath = "TempEncryptedAddressBook.xml";
        File file = new File(TEST_DATA_FOLDER + filePath);
        SecurityUtil.encrypt(file, SecurityUtil.hashPassword("wrongPassword"));
        thrown.expect(WrongPasswordException.class);
        readAddressBook(filePath, new Password("test"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        readBack = xmlAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlAddressBookStorage.saveAddressBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readAddressBook().get(); //file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void readAndSaveEncryptedAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        original.updatePassword(new Password("test"));
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBook(filePath, new Password("test")).get();
        assertEquals(original, new AddressBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        readBack = xmlAddressBookStorage.readAddressBook(filePath, new Password("test")).get();
        assertEquals(original, new AddressBook(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlAddressBookStorage.saveAddressBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readAddressBook(new Password("test")).get(); //file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void importAddressBook_invalidFileFormat_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        String filePath = TEST_DATA_FOLDER + "invalidFileFormatAddressBook.xml";
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.importAddressBook(filePath, original, SecurityUtil.hashPassword(TEST_PASSWORD));
    }

    @Test
    public void importAddressBook_nonExistentFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        String filePath = TEST_DATA_FOLDER + "nonExistentAddressBook.xml";
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.importAddressBook(filePath, original, SecurityUtil.hashPassword(TEST_PASSWORD));
    }

    @Test
    public void importAddressBook_validFile_success() throws Exception {
        String filePath = TEST_DATA_FOLDER + "validAddressBook.xml";
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        // Import file into existing address book
        xmlAddressBookStorage.importAddressBook(filePath, original, SecurityUtil.hashPassword(TEST_PASSWORD));
        AddressBook expected = original;
        expected.importPerson(ALICE);
        expected.importPerson(BENSON);
        assertEquals(original, expected);
    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new XmlAddressBookStorage(filePath).saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (WrongPasswordException wpe) {
            throw new AssertionError("There should not be any encryption for the file.", wpe);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new AddressBook(), null);
    }


}
