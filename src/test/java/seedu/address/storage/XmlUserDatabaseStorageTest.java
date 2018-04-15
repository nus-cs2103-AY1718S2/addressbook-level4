package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalUsers.DEFAULT_USER;
import static seedu.address.testutil.TypicalUsers.RACH;
import static seedu.address.testutil.TypicalUsers.RICK;
import static seedu.address.testutil.TypicalUsers.getTypicalUserDatabase;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyUserDatabase;
import seedu.address.model.UserDatabase;

//@@author kaisertanqr
public class XmlUserDatabaseStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlUserDatabaseStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readUserDatabase_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readUserDatabase(null);
    }

    private java.util.Optional<ReadOnlyUserDatabase> readUserDatabase(String filePath) throws Exception {
        return new XmlUserDatabaseStorage(filePath).readUserDatabase(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUserDatabase("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readUserDatabase("NotXmlFormatUserDatabase.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAndSaveUserDatabase_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        UserDatabase original = getTypicalUserDatabase();
        XmlUserDatabaseStorage xmlUserDatabaseStorage = new XmlUserDatabaseStorage(filePath);

        //Save in new file and read back
        xmlUserDatabaseStorage.saveUserDatabase(original, filePath);
        ReadOnlyUserDatabase readBack = xmlUserDatabaseStorage.readUserDatabase(filePath).get();
        assertEquals(original, new UserDatabase(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addUser(RACH);
        original.removeUser(DEFAULT_USER);
        xmlUserDatabaseStorage.saveUserDatabase(original, filePath);
        readBack = xmlUserDatabaseStorage.readUserDatabase(filePath).get();
        assertEquals(original, new UserDatabase(readBack));

        //Save and read without specifying file path
        original.addUser(RICK);
        xmlUserDatabaseStorage.saveUserDatabase(original); //file path not specified
        readBack = xmlUserDatabaseStorage.readUserDatabase().get(); //file path not specified
        assertEquals(original, new UserDatabase(readBack));

    }

    @Test
    public void readUserDatabase_invalidPersonUserDatabase_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readUserDatabase("invalidUserUserDatabase.xml");
    }

    @Test
    public void readUserDatabase_invalidAndValidUserDatabase_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readUserDatabase("invalidAndValidUserUserDatabase.xml");
    }

    @Test
    public void saveUserDatabase_nullUserDatabase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveUserDatabase(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveUserDatabase(ReadOnlyUserDatabase userDatabase, String filePath) {
        try {
            new XmlUserDatabaseStorage(filePath).saveUserDatabase(userDatabase, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveUserDatabase_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveUserDatabase(new UserDatabase(), null);
    }
}
