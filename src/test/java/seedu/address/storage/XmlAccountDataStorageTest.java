//@@author Jason1im
package seedu.address.storage;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Account;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class XmlAccountDataStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAccountDataStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAccountData_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAccountData(null);
    }

    private java.util.Optional<Account> readAccountData(String filePath) throws Exception {
        return new XmlAccountDataStorage(filePath).readAccountData(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAccountData("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAccountData("NotXmlFormatAccountData.xml");
    }

    @Test
    public void readAccountData_invalidAccountData_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAccountData("invalidAccountData.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAccountData.xml";
        Account original = new Account();
        XmlAccountDataStorage xmlAccountDataStorage = new XmlAccountDataStorage(filePath);

        //Save in new file and read back
        xmlAccountDataStorage.saveAccountData(original, filePath);
        Account readBack = xmlAccountDataStorage.readAccountData(filePath).get();
        assertEquals(original, readBack);

        //Modify data, overwrite exiting file, and read back
        original.updateUsername("Bill");
        original.updatePassword("gan123");
        xmlAccountDataStorage.saveAccountData(original, filePath);
        readBack = xmlAccountDataStorage.readAccountData(filePath).get();
        assertEquals(original, readBack);

        //Save and read without specifying file path
        original.updatePassword("boy2");
        xmlAccountDataStorage.saveAccountData(original); //file path not specified
        readBack = xmlAccountDataStorage.readAccountData().get(); //file path not specified
        assertEquals(original, readBack);

    }

    @Test
    public void saveAccountData_nullAccountData_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAccountData(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAccountData(Account account, String filePath) {
        try {
            new XmlAccountDataStorage(filePath).saveAccountData(account, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAccountData_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAccountData(new Account(), null);
    }


}
