package seedu.address.ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import guitests.guihandles.LoginPaneHandle;

import seedu.address.login.Login;
import seedu.address.login.LoginManager;
import seedu.address.storage.JsonUserPassStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;

//@@author ngshikang
public class LoginPaneTest extends GuiUnitTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Login login;
    private StorageManager storage;
    private LoginPane loginPane;
    private LoginPaneHandle loginPaneHandle;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonUserPassStorage userPassStorage = new JsonUserPassStorage(getTempFilePath("tempUserPass.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage, userPassStorage);
        login = new LoginManager(storage);
        loginPane = new LoginPane(login);
        loginPaneHandle = new LoginPaneHandle(getChildNode(loginPane.getRoot(),
                LoginPaneHandle.LOGIN_PANE_ID));
        uiPartRule.setUiPart(loginPane);
    }
    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    @Test
    public void loginPane_checkInitialAccessPermission() {
        Assert.assertFalse(loginPane.isAccessPermitted());
    }

    @Test
    public void loginPane_checkSuccessfulLoginAccess() {
        loginPaneHandle.getTextField().setText("user");
        loginPaneHandle.getPasswordField().setText("pass");
        loginPaneHandle.getCreateButton().fire();
        loginPaneHandle.getLoginButton().fire();
        Assert.assertTrue(loginPane.isAccessPermitted());
    }

}
