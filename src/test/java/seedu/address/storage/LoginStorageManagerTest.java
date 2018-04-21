package seedu.address.storage;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalUsers.getTypicalLoginManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.LoginManager;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author JoonKai1995
public class LoginStorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private LoginStorageManager loginStorageManager;

    @Before
    public void setUp() {
        XmlLoginStorage loginStorage = new XmlLoginStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        loginStorageManager = new LoginStorageManager(loginStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    /**
     * Checks if the info on the 2 Login Managers are the same.
     * @param loginManager1
     * @param loginManager2
     * @return
     */
    private boolean checkUsers(LoginManager loginManager1, LoginManager loginManager2) {
        if (loginManager1.getUserList().size() != loginManager2.getUserList().size()) {
            return false;
        }
        for (int i = 0; i < loginManager1.getUserList().size(); i++) {
            if (!loginManager1.getUserList().get(i).getUsername().getUsername()
                    .equals(loginManager2.getUserList().get(i).getUsername().getUsername())
                    || !loginManager1.getUserList().get(i).getPassword().getPassword()
                    .equals(loginManager2.getUserList().get(i).getPassword().getPassword())) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void loginReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        LoginManager original = getTypicalLoginManager();
        loginStorageManager.saveLogin(original);
        LoginManager retrieved = loginStorageManager.readLogin().get();
        assertTrue(checkUsers(original, retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(loginStorageManager.getLoginFilePath());
    }
}

