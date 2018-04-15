package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.After;
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
import seedu.address.testutil.AddressBookBuilder;

public class XmlAddressBookStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAddressBookStorageTest/");
    private static final String TEST_PASSWORD = "test!!!!";
    private static final String TEST_DATA_FILE = FileUtil.getPath("src/test/data/sandbox/temp.xml");
    private static final String TEST_DATA_FILE_ALICE_BENSON = TEST_DATA_FOLDER + "aliceBensonAddressBook.xml";
    private static final String TEST_DATA_FILE_ALICE_BENSON_ENCRYPTED = TEST_DATA_FOLDER
            + "encryptedAliceBensonAddressBook.xml";
    private static final String TEST_DATA_FILE_ALICE_BENSON_ENCRYPTED_BACKUP = TEST_DATA_FOLDER
            + "encryptedAliceBensonAddressBookBackup.xml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private final AddressBook addressBookWithAliceAndBenson = new AddressBookBuilder().withPerson(ALICE)
            .withPerson(BENSON).build();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new XmlAddressBookStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    //@@author yeggasd
    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath, Password password)
                                                                            throws Exception {
        return new XmlAddressBookStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath), password);
    }
    //@@author

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
    public void readAddressBook_invalidPersonAddressBook_throwsDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwsDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    //@@author yeggasd
    @Test
    public void readAddressBookWithPassword_invalidAndValidPersonAddressBook_throwsDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBookWithPassword_wrongPassword_throwsWrongPasswordException() throws Exception {
        String filePath = "TempEncryptedAddressBook.xml";
        File file = new File(TEST_DATA_FOLDER + filePath);
        SecurityUtil.encrypt(file, SecurityUtil.hashPassword("wrongPassword"));
        thrown.expect(WrongPasswordException.class);
        readAddressBook(filePath, new Password("test"));
    }
    //@@author

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

    //@@author yeggasd
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
    public void saveAddressBook_changedPassword_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        original.updatePassword(new Password("test"));
        original.updatePassword(SecurityUtil.hashPassword("new"));
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.saveAddressBook(original, filePath);

        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBook(filePath, new Password(
                                        SecurityUtil.hashPassword("new"),
                                        SecurityUtil.hashPassword(TEST_PASSWORD))).get();
        assertEquals(original, new AddressBook(readBack));
    }
    //@@author

    //@@author Caijun7
    @Test
    public void importAddressBook_invalidFileFormat_throwsDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        String filePath = TEST_DATA_FOLDER + "invalidFileFormatAddressBook.xml";
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.importAddressBook(filePath, original, SecurityUtil.hashPassword(TEST_PASSWORD));
    }

    @Test
    public void importAddressBook_nonExistentFile_throwsFileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        String filePath = TEST_DATA_FOLDER + "nonExistentAddressBook.xml";
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.importAddressBook(filePath, original, SecurityUtil.hashPassword(TEST_PASSWORD));
    }

    @Test
    public void importAddressBook_encryptedFileWrongPassword_throwsWrongPasswordException() throws Exception {
        thrown.expect(WrongPasswordException.class);
        String encryptedFile = TEST_DATA_FILE_ALICE_BENSON_ENCRYPTED;
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(encryptedFile);
        xmlAddressBookStorage.importAddressBook(encryptedFile, original, SecurityUtil.hashPassword("Wrong password"));
    }

    @Test
    public void importAddressBook_encryptedValidFile_success() throws Exception {
        String encryptedFile = TEST_DATA_FILE_ALICE_BENSON;
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(encryptedFile);
        // Import file into existing address book
        xmlAddressBookStorage.importAddressBook(encryptedFile, original, SecurityUtil.hashPassword(TEST_PASSWORD));

        AddressBook expected = addressBookWithAliceAndBenson;
        assertEquals(expected, original);
    }

    @Test
    public void importAddressBook_unencryptedValidFile_success() throws Exception {
        String filePath = TEST_DATA_FILE_ALICE_BENSON;
        AddressBook original = new AddressBook();
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        // Import file into existing address book
        xmlAddressBookStorage.importAddressBook(filePath, original, null);

        AddressBook expected = addressBookWithAliceAndBenson;
        assertEquals(expected, original);
    }

    @Test
    public void exportAddressBook_invalidFilepath_throwsIoException() throws Exception {
        thrown.expect(IOException.class);
        String filePath = TEST_DATA_FOLDER;
        AddressBook addressBook = addressBookWithAliceAndBenson;
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.exportAddressBook(filePath, null, addressBook.getPersonList(),
                addressBook.getAliasList(), addressBook.getTagList());
    }

    @Test
    public void exportAddressBook_validFilepathUnencryptedAddressBook_success() throws Exception {
        String filePath = TEST_DATA_FILE;
        AddressBook original = addressBookWithAliceAndBenson;
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.exportAddressBook(filePath, null, original.getPersonList(),
                original.getAliasList(), original.getTagList());

        Path testDataPath = Paths.get(TEST_DATA_FILE);
        byte[] testData = Files.readAllBytes(testDataPath);

        Path expectedPath = Paths.get(TEST_DATA_FILE_ALICE_BENSON);
        byte[] expected = Files.readAllBytes(expectedPath);

        assertTrue(Arrays.equals(expected, testData));
    }

    @Test
    public void exportAddressBook_validFilepathEncryptedAddressBook_success() throws Exception {
        String filePath = TEST_DATA_FILE_ALICE_BENSON_ENCRYPTED;
        AddressBook original = addressBookWithAliceAndBenson;
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.exportAddressBook(filePath, new Password(TEST_PASSWORD), original.getPersonList(),
                original.getAliasList(), original.getTagList());

        Path testDataPath = Paths.get(TEST_DATA_FILE_ALICE_BENSON_ENCRYPTED);
        byte[] testData = Files.readAllBytes(testDataPath);

        Path expectedPath = Paths.get(TEST_DATA_FILE_ALICE_BENSON_ENCRYPTED_BACKUP);
        byte[] expected = Files.readAllBytes(expectedPath);

        assertTrue(Arrays.equals(expected, testData));
    }

    @Test
    public void exportAndImportUnencryptedAddressBook_allInOrder_success() throws Exception {
        String filePath = TEST_DATA_FILE;
        AddressBook original = addressBookWithAliceAndBenson;
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.exportAddressBook(filePath, null, original.getPersonList(),
                original.getAliasList(), original.getTagList());

        AddressBook expected = new AddressBook();
        XmlAddressBookStorage expectedXmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        expectedXmlAddressBookStorage.importAddressBook(filePath, expected, null);
        assertEquals(expected, original);
    }

    @Test
    public void exportAndImportEncryptedAddressBook_allInOrder_success() throws Exception {
        String filePath = TEST_DATA_FILE;
        AddressBook original = addressBookWithAliceAndBenson;
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.exportAddressBook(filePath, new Password(TEST_PASSWORD), original.getPersonList(),
                original.getAliasList(), original.getTagList());

        AddressBook expected = new AddressBook();
        XmlAddressBookStorage expectedXmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        expectedXmlAddressBookStorage.importAddressBook(filePath, expected, SecurityUtil.hashPassword(TEST_PASSWORD));
        assertEquals(expected, original);
    }
    //@@author

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
    //@@author yeggasd
    @After
    public void reset() throws Exception {
        String filePath = "TempEncryptedAddressBook.xml";
        File file = new File(TEST_DATA_FOLDER + filePath);
        SecurityUtil.decrypt(file, SecurityUtil.hashPassword("wrongPassword"));
    }
}
