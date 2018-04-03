# ngshikang
###### \java\guitests\guihandles\LoginPaneHandle.java
``` java
/**
 * Provides a handle for {@code LoginPane}.
 */
public class LoginPaneHandle extends NodeHandle<AnchorPane>  {

    public static final String LOGIN_PANE_ID = "#loginPanePlaceholder";
    private static final String PASSWORD_FIELD_ID = "#passwordField";
    private static final String USERNAME_FIELD_ID = "#usernameTextField";
    private static final String LOGIN_BUTTON_ID = "#loginButton";
    private static final String CREATE_BUTTON_ID = "#createButton";
    private static final String EXIT_BUTTON_ID = "#exitButton";

    private final PasswordField passwordField;
    private final TextField textField;
    private final Button loginButton;
    private final Button createButton;
    private final Button exitButton;

    public LoginPaneHandle(AnchorPane mainLoginNode) {
        super(mainLoginNode);

        this.passwordField = getChildNode(PASSWORD_FIELD_ID);
        this.textField = getChildNode(USERNAME_FIELD_ID);
        this.loginButton = getChildNode(LOGIN_BUTTON_ID);
        this.createButton = getChildNode(CREATE_BUTTON_ID);
        this.exitButton = getChildNode(EXIT_BUTTON_ID);

    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getTextField() {
        return textField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getCreateButton() {
        return createButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

}
```
###### \java\seedu\address\login\LoginManagerTest.java
``` java
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
        JsonUserPassStorage userPassStorage = new JsonUserPassStorage(getTempFilePath("tempUserPass"));
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
```
###### \java\seedu\address\login\UserPassStorageTest.java
``` java
public class UserPassStorageTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void addUserPassToStorage() {
        UserPass testProfile = new UserPass("user", "pass");
        JsonUserPassStorage userPassStorage = new JsonUserPassStorage(getTempFilePath("tempUserPass"));
        userPassStorage.put(testProfile);
        Assert.assertTrue(userPassStorage.containsKey(testProfile.getUsername()));
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

}
```
###### \java\seedu\address\login\UserPassTest.java
``` java
public class UserPassTest {

    @Test
    public void createUserPass() {
        UserPass testProfile = new UserPass("user", "pass");
        Assert.assertEquals(testProfile.getUsername(), "user");
        Assert.assertEquals(testProfile.getPassword(), UserPass.hash("pass"));
    }

}
```
###### \java\seedu\address\ui\LoginPaneTest.java
``` java
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
```
###### \java\seedu\address\ui\UiManagerTest.java
``` java
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
```
