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

    private StorageManager storage;
    private Login login;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        UserPassStorage userPassStorage = new UserPassStorage();
        storage = new StorageManager(addressBookStorage, userPrefsStorage, userPassStorage);
        login = new LoginManager(storage);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_invalidUserPass_throwsInvalidException() {
        String invalidUsername = "";
        String invalidPassword = "";
        Assert.assertThrows(InvalidUsernameException.class,
                () -> login.checkLoginDetails(new UserPass(invalidUsername, "pass")));
        Assert.assertThrows(InvalidUsernameException.class,
                () -> login.checkLoginDetails(new UserPass("user", invalidPassword)));
    }

}
