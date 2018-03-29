package seedu.address.ui;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.Config;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.login.Login;
import seedu.address.login.LoginManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonUserPassStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.testutil.Assert;

//@@author ngshikang
public class UiManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager();
    private Logic logic = new LogicManager(model);
    private Config config = new Config();
    private UserPrefs prefs = new UserPrefs();
    private StorageManager storage;
    private Login login;
    private Ui ui;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonUserPassStorage userPassStorage = new JsonUserPassStorage(getTempFilePath("tempUserPass"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage, userPassStorage);
        login = new LoginManager(storage);
        ui = new UiManager(logic, config, prefs, login);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void checkUiValidity() {
        assertNotNull(ui);
    }

    @Test
    public void checkStartLogin() {
        Assert.assertThrows(NullPointerException.class, () -> ui.startLogin(null));
    }

    @Test
    public void checkStopLogin() {
        Assert.assertThrows(NullPointerException.class, () -> ui.stopLogin());
    }

    @Test
    public void checkAppStart() {
        Assert.assertThrows(NullPointerException.class, () -> ui.start(null));
    }

    @Test
    public void checkAppStop() {
        Assert.assertThrows(NullPointerException.class, () -> ui.stop());
    }

}
