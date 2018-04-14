package seedu.address.storage;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.FileUtil;
import seedu.address.storage.exceptions.RequestTimeoutException;

//@@author Caijun7
public class GoogleDriveStorageTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/GoogleDriveStorageTest/");
    private static final String TEST_DATA_FILE_VALID = TEST_DATA_FOLDER + "validAddressBook.xml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setTestEnvironment() {
        GoogleDriveStorage.setTestEnvironment();
    }

    @Test
    public void constructor_noUserResponse_throwsRequestTimeoutException() throws Exception {
        thrown.expect(RequestTimeoutException.class);
        GoogleDriveStorage.resetTestEnvironment();
        GoogleDriveStorage googleDriveStorage = new GoogleDriveStorage("test");
        GoogleDriveStorage.setTestEnvironment();
    }

    @Test
    public void constructor_allInOrder_success() throws Exception {
        GoogleDriveStorage googleDriveStorage = new GoogleDriveStorage("test");
    }

    @Test
    public void uploadFile_invalidFilePath_throwsIoException() throws Exception {
        thrown.expect(IOException.class);
        String invalidFilePath = "nonExistentAddressBook.xml";
        GoogleDriveStorage googleDriveStorage = new GoogleDriveStorage(invalidFilePath);
        googleDriveStorage.uploadFile();
    }

    @Test
    public void uploadFile_validFilePath_success() throws Exception {
        GoogleDriveStorage googleDriveStorage = new GoogleDriveStorage(TEST_DATA_FILE_VALID);
        googleDriveStorage.uploadFile();
    }

    @AfterClass
    public static void resetTestEnvironment() {
        GoogleDriveStorage.resetTestEnvironment();
    }

}
