package seedu.address.login;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.testutil.Assert;

public class LoginManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Login login;
    private StorageManager storage;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        UserPassStorage userPassStorage = new UserPassStorage();
        storage = new StorageManager(addressBookStorage, userPrefsStorage, userPassStorage);
        login = new LoginManager(storage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void execute_invalidUserPass_throwsInvalidException() {
        String invalidUsername = "";
        String invalidPassword = "";
        Assert.assertThrows(InvalidUsernameException.class, () -> login.checkLoginDetails(
                new UserPass(invalidUsername, "pass")));
        Assert.assertThrows(InvalidUsernameException.class, () -> login.checkLoginDetails(
                new UserPass("user", invalidPassword)));
    }

}
