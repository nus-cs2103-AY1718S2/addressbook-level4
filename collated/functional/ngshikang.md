# ngshikang
###### \java\seedu\address\login\InvalidPasswordException.java
``` java
/**
 * Returns an exception for wrong password in login
 */
public class InvalidPasswordException extends Exception {
}
```
###### \java\seedu\address\login\InvalidUsernameException.java
``` java
/**
 * Returns an exception for invalid username in login
 */
public class InvalidUsernameException extends Exception{
}
```
###### \java\seedu\address\login\Login.java
``` java
/**
 * API of the Login component
 */
public interface Login {

    /** Returns a boolean indicating if access can be allowed to user given login inputs */
    boolean checkLoginDetails(UserPass userpass) throws InvalidUsernameException, InvalidPasswordException;

    /** Returns a boolean indicating if username input is valid */
    boolean checkUsername();

    /** Returns a boolean indicating if password input corresponds to given username */
    boolean checkPassword();

    /** Stores a new username and password pair */
    void storeUserPass(UserPass userpass) throws UsernameTakenException;

    /** Initiates App for successful login **/
    void accessPermitted();

    /** Returns String that represents profile ID **/
    String getUsername();

}
```
###### \java\seedu\address\login\LoginManager.java
``` java
/**
 * Manages login to a specific Pigeons driver profile and customised storage.
 */
public class LoginManager extends ComponentManager implements Login {
    private static final Logger logger = LogsCenter.getLogger(LoginManager.class);
    private static final boolean CHECK_SUCCESS = true;
    private UserPassStorage userPassStorage;
    private StorageManager storage;
    private UserPass userpass;

    public LoginManager(StorageManager storage) {
        this.storage = storage;
        this.userPassStorage = storage.getUserPassStorage();
    }

    /**
     * Returns a boolean to signify login success and throws exceptions when it fails
     */
    public boolean checkLoginDetails(UserPass userpass) throws InvalidUsernameException, InvalidPasswordException {
        logger.info("----------------[USER/PASS CHECK][" + userpass.getUsername() + "]");
        this.userpass = userpass;
        if (!checkUsername()) {
            throw new InvalidUsernameException();
        } else if   (!checkPassword()) {
            throw new InvalidPasswordException();
        }
        return CHECK_SUCCESS;
    }

    /**
     * Returns a boolean to verify username is valid
     */
    public boolean checkUsername() {
        String username = userpass.getUsername();
        if (username.equals("") || username.isEmpty()) {
            return false;
        }
        return userPassStorage.containsKey(username);
    }

    /**
     * Returns a boolean to verify password is valid
     */
    public boolean checkPassword() {
        String username = userpass.getUsername();
        String passwordInput = userpass.getPassword();
        String passwordExpected = userPassStorage.get(username);
        return passwordInput.equals(passwordExpected);
    }

    /**
     * Stores new username and password into storage
     */
    public void storeUserPass(UserPass userpass) throws UsernameTakenException {
        if (userPassStorage.containsKey(userpass.getUsername())) {
            throw new UsernameTakenException();
        }
        userPassStorage.put(userpass);
        try {
            userPassStorage.saveUserPassMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        storage.setUserPassStorage(userPassStorage);
    }

    /**
     * Posts new event to signify login success
     */
    public void accessPermitted() {
        EventsCenter.getInstance().post(new LoginAccessGrantedEvent());
    }

    public String getUsername() {
        return userpass.getUsername();
    }

}
```
###### \java\seedu\address\login\UsernameTakenException.java
``` java
/**
 * Exception disallowing creation of username that already exists in storage
 */
public class UsernameTakenException extends Exception {
}
```
###### \java\seedu\address\login\UserPass.java
``` java
/**
 * Represents a profile's username and password
 */
public class UserPass {

    private String username;
    private String password;

    public UserPass(String username, String password) {
        this.username = username;
        this.password = hash(password.trim());
    }

    /**
     * Returns a String containing the SHA-256 encrypted form of input password String
     */
    public static String hash(String password) {
        byte[] encodedPassword = new byte[0];
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            encodedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return bytesToHex(encodedPassword);

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Utility function returning hexadecimal string from hashed password in byte array
     */
    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
```
###### \java\seedu\address\login\UserPassStorage.java
``` java
/**
 * Storage for UserPass
 */
public interface UserPassStorage {
    /**
     * Returns the file path of the UserPass data file.
     */
    String getUserPassFilePath();

    /**
     * Returns UserPass data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<HashMap> readUserPassMap() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.login.UserPass} to the storage.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPassMap() throws IOException;

    /**
     * Provide similar functionality as "put" in underlying HashMap to insert new UserPass to storage
     */
    void put(UserPass userPass);

    /**
     * Provide similar functionality as "containsKey" in underlying HashMap to insert new UserPass to storage
     */
    boolean containsKey(String username);

    /**
     * Provide similar functionality as "get" in underlying HashMap to insert new UserPass to storage
     */
    String get(String username);

}
```
###### \java\seedu\address\MainApp.java
``` java
    /**
     * Reinitialises components to match previous state of specific user profile
     */
    private void reInit(Login login) {
        String profile = login.getUsername();
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(profile + config.getUserPrefsFilePath());
        userPrefs = initPrefs(userPrefsStorage);
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(profile + userPrefs.getAddressBookFilePath());
        storage = new StorageManager(addressBookStorage, userPrefsStorage, userPassStorage);
        model = initModelManager(storage, userPrefs);
        logic = new LogicManager(model);
        ui = new UiManager(logic, config, userPrefs, login);
    }

```
###### \java\seedu\address\MainApp.java
``` java
    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting Login to Pigeons AddressBook " + MainApp.VERSION);
        ui.startLogin(primaryStage);
    }

    /**
     * Starts Application after login success
     */
    public void startApp(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        reInit(login);
        readWelcomeMessage();
        ui.start(primaryStage);
    }

```
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    public void handleExitLoginRequestEvent(ExitLoginRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.exit();
        System.exit(0);
    }

    @Subscribe
    public void handleAccessGrantedEvent(LoginAccessGrantedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ui.stopLogin();
        this.startApp(new Stage());
    }

```
###### \java\seedu\address\storage\JsonUserPassStorage.java
``` java
/**
 * A class to access UserPass stored in the hard disk as a json file
 */
public class JsonUserPassStorage implements UserPassStorage {

    private String filePath;
    private HashMap userPassHashmap;

    public JsonUserPassStorage(String filePath) {
        this.filePath = filePath;
        try {
            userPassHashmap = readUserPassMap().get();
        } catch (DataConversionException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException | NoSuchElementException e) {
            userPassHashmap = new HashMap<>();
        }
    }

    @Override
    public String getUserPassFilePath() {
        return filePath;
    }

    @Override
    public Optional<HashMap> readUserPassMap() throws DataConversionException, IOException {
        return readUserPassMap(filePath);
    }

    /**
     * Similar to {@link #readUserPassMap()}
     * @param userpassFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<HashMap> readUserPassMap(String userpassFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(userpassFilePath, HashMap.class);
    }

    @Override
    public void saveUserPassMap() throws IOException {
        JsonUtil.saveJsonFile(userPassHashmap, filePath);
    }

    @Override
    public void put(UserPass userPass) {
        userPassHashmap.put(userPass.getUsername(), userPass.getPassword());
    }

    @Override
    public boolean containsKey(String username) {
        return userPassHashmap.containsKey(username);
    }

    @Override
    public String get(String username) {
        return userPassHashmap.get(username).toString();
    }

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    public StorageManager(AddressBookStorage addressBookStorage,
                          UserPrefsStorage userPrefsStorage,
                          UserPassStorage userPassStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.userPassStorage = userPassStorage;
    }

    // ================ UserPass methods ==============================

    public UserPassStorage getUserPassStorage() {
        return userPassStorage;
    }
    public void setUserPassStorage(UserPassStorage userPassStorage) {
        this.userPassStorage = userPassStorage;
    }

```
###### \java\seedu\address\ui\LoginPane.java
``` java
/**
 * A ui for the login screen
 */
public class LoginPane extends UiPart<Region> {

    private static final String FXML = "LoginPane.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private Login login;
    private boolean isAccessPermitted = false;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button createButton;

    @FXML
    private Button exitButton;

    @FXML
    private Text loginStatus;

    public LoginPane(Login login) {
        super(FXML);
        this.login = login;
        usernameTextField.requestFocus();
    }

    @FXML
    private void checkLoginDetails() {
        if (checkLoginDetails(login)) {
            login.accessPermitted();
        }
    }

    /**
     * Returns a boolean for checking login details
     */
    private boolean checkLoginDetails(Login login) {
        try {
            isAccessPermitted = login.checkLoginDetails(
                    new UserPass(usernameTextField.getText(),
                            passwordField.getText()));
        } catch (Exception e) {
            loginStatus.setText("Login Failed.");
        }
        if (isAccessPermitted) {
            loginStatus.setText("Login Successful.");
        }
        return isAccessPermitted;
    }

    @FXML
    private void createNewAccount() {
        createNewAccount(login);
    }

    /**
     * Stores new profile's UserPass into UserPassStorage
     */
    private void createNewAccount(Login login) {
        try {
            login.storeUserPass(
                    new UserPass(
                            usernameTextField.getText(),
                            passwordField.getText()));
        } catch (Exception e) {
            loginStatus.setText("Username is taken.");
        }
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress (KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            keyEvent.consume();
            if (loginButton.isFocused() || passwordField.isFocused()) {
                loginButton.fire();
            } else if (createButton.isFocused()) {
                createButton.fire();
            } else if (exitButton.isFocused()) {
                exitButton.fire();
            }
        }
    }

    @FXML
    private void closeApplication() {
        EventsCenter.getInstance().post(new ExitLoginRequestEvent());
    }

    public boolean isAccessPermitted() {
        return isAccessPermitted;
    }
}
```
###### \java\seedu\address\ui\LoginWindow.java
``` java
/**
 * The Login Window. Provides the layout for user login before accessing the main program.
 */
public class LoginWindow extends UiPart<Stage> {

    private static final String FXML = "LoginWindow.fxml";
    private Stage primaryStage;
    private Login login;
    private LoginPane loginPane;

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private AnchorPane loginPanePlaceholder;

    public LoginWindow(Stage primaryStage, Login login) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.login = login;

        registerAsAnEventHandler(this);
    }

    void fillPane() {
        loginPane = new LoginPane(login);
        loginPanePlaceholder.getChildren().add(loginPane.getRoot());
    }

    boolean checkAccess() {
        return loginPane.isAccessPermitted();
    }

    void show() {
        primaryStage.show();
    }

    void hide() {
        primaryStage.hide();
    }

}
```
###### \java\seedu\address\ui\UiManager.java
``` java
    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, config, prefs, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stop() {
        try {
            prefs.updateLastUsedGuiSetting(mainWindow.getCurrentGuiSetting());
            mainWindow.hide();
            mainWindow.releaseResources();
        } catch (NullPointerException e) {
            logger.info("Illegal exit occurred. Please click proper exit button in the future.");
            throw new NullPointerException();
        }
    }

    @Override
    public void startLogin(Stage primaryStage) {
        logger.info("Starting Login...");
        this.primaryStage = primaryStage;

        try {
            primaryStage.setHeight(600);
            primaryStage.setWidth(400);
            loginWindow = new LoginWindow(primaryStage, login);
            loginWindow.show();
            loginWindow.fillPane();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    @Override
    public void stopLogin() {
        loginWindow.hide();
    }

```
###### \resources\view\BrowserPanel.fxml
``` fxml

<StackPane stylesheets="@DarkTheme.css" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
  <WebView fx:id="browser" prefHeight="455.0" prefWidth="758.0">
      <StackPane.margin>
         <Insets top="28.0" />
      </StackPane.margin></WebView>
  <Text fill="#ffffff" fontSmoothingType="LCD" strokeType="OUTSIDE" strokeWidth="0.0" text="ADDITIONAL INFORMATION" textOrigin="CENTER" translateY="0.5" wrappingWidth="198.40008544921875" StackPane.alignment="TOP_LEFT">
    <font>
      <Font name="ADAM.CGPRO" size="14.0" />
    </font>
    <StackPane.margin>
      <Insets left="5.0" top="5.0" />
    </StackPane.margin>
  </Text>
   <Text fx:id="additionalInfo" fill="#ffffff" strokeType="OUTSIDE" strokeWidth="0.0" text="+ Additional Information" wrappingWidth="547.2000732421875" StackPane.alignment="TOP_RIGHT">
      <StackPane.margin>
         <Insets left="20.0" top="5.0" />
      </StackPane.margin>
      <font>
         <Font name="ADAM.CGPRO" size="14.0" />
      </font>
   </Text>
</StackPane>
```
###### \resources\view\CommandBox.fxml
``` fxml
<StackPane opacity="0.91" prefHeight="26.0" prefWidth="377.0" styleClass="anchor-pane" stylesheets="@DarkTheme.css" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
  <TextField fx:id="commandTextField" onAction="#handleCommandInputChanged" onKeyPressed="#handleKeyPress" prefHeight="23.0" prefWidth="215.0" promptText="Enter command here...">
      <StackPane.margin>
         <Insets left="145.0" />
      </StackPane.margin>
      <font>
         <Font name="ADAM.CGPRO" size="12.0" />
      </font></TextField>
   <Text fill="#ffffff" fontSmoothingType="LCD" strokeType="OUTSIDE" strokeWidth="0.0" text="COMMAND LINE " textAlignment="LEFT" textOrigin="CENTER" translateY="0.5" wrappingWidth="120.0" StackPane.alignment="CENTER_LEFT">
      <font>
         <Font name="ADAM.CGPRO" size="14.0" />
      </font>
      <StackPane.margin>
         <Insets left="5.0" />
      </StackPane.margin>
   </Text>
</StackPane>
```
###### \resources\view\LoginPane.fxml
``` fxml
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.PasswordField?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.Pane?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>

<AnchorPane fx:id="loginPanePlaceholder" maxHeight="600.0" maxWidth="400.0" minHeight="600.0" minWidth="400.0" prefHeight="600.0" prefWidth="400.0" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <Pane blendMode="DARKEN" cache="true" depthTest="ENABLE" maxHeight="600.0" maxWidth="400.0" minHeight="600.0" minWidth="400.0" pickOnBounds="false" prefHeight="600.0" prefWidth="400.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
            <children>
                <TextField fx:id="usernameTextField" layoutX="161.0" layoutY="271.0" promptText="Enter Username Here">
               <font>
                  <Font name="ADAM.CGPRO" size="12.0" />
               </font></TextField>
                <PasswordField fx:id="passwordField" layoutX="161.0" layoutY="306.0" onKeyPressed="#handleKeyPress" promptText="ENTER PASSWORD HERE">
               <font>
                  <Font name="ADAM.CGPRO" size="12.0" />
               </font></PasswordField>
                <Button fx:id="loginButton" layoutX="137.0" layoutY="379.0" mnemonicParsing="false" onAction="#checkLoginDetails" onKeyPressed="#handleKeyPress" prefHeight="25.0" prefWidth="127.0" text="Login">
               <font>
                  <Font name="ADAM.CGPRO" size="12.0" />
               </font></Button>
                <Button fx:id="createButton" layoutX="137.0" layoutY="417.0" mnemonicParsing="false" onAction="#createNewAccount" onKeyPressed="#handleKeyPress" prefHeight="25.0" prefWidth="127.0" text="Create Account">
               <font>
                  <Font name="ADAM.CGPRO" size="12.0" />
               </font></Button>
                <Button fx:id="exitButton" layoutX="355.0" layoutY="20.0" mnemonicParsing="false" onAction="#closeApplication" onKeyPressed="#handleKeyPress" text="X">
               <font>
                  <Font name="ADAM.CGPRO" size="12.0" />
               </font></Button>
                <ImageView blendMode="SOFT_LIGHT" cacheHint="QUALITY" depthTest="ENABLE" fitHeight="225.0" fitWidth="251.0" layoutX="85.0" layoutY="43.0" preserveRatio="true">
                    <image>
                        <Image url="@PigeonsLogoDigital.png" />
                    </image>
                </ImageView>
                <Text layoutX="76.0" layoutY="286.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Username: ">
               <font>
                  <Font name="ADAM.CGPRO" size="12.0" />
               </font></Text>
                <Text layoutX="76.0" layoutY="322.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Password:">
               <font>
                  <Font name="ADAM.CGPRO" size="12.0" />
               </font></Text>
            <Text fx:id="loginStatus" boundsType="VISUAL" layoutX="1.0" strokeType="OUTSIDE" strokeWidth="0.0" text="Login Status: " textOrigin="CENTER" translateX="18.0" translateY="10.0" wrappingWidth="126.99999809265137" x="137.0" y="464.0">
               <font>
                  <Font name="ADAM.CGPRO" size="12.0" />
               </font></Text>
            </children>
        </Pane>
    </children>
</AnchorPane>
```
###### \resources\view\LoginWindow.fxml
``` fxml
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.Scene?>

<fx:root type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1"
         minWidth="450" minHeight="600">
    <scene>
        <Scene>
            <AnchorPane fx:id="loginPanePlaceholder">
            </AnchorPane>
        </Scene>
    </scene>
</fx:root>
```
###### \resources\view\PersonListPanel.fxml
``` fxml
<VBox stylesheets="@DarkTheme.css" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <Text fill="#ffffff" fontSmoothingType="LCD" strokeType="OUTSIDE" strokeWidth="0.0" text="ADDRESSEES" textOrigin="CENTER" translateY="1.0">
      <font>
         <Font name="ADAM.CGPRO" size="14.0" />
      </font>
      <VBox.margin>
         <Insets bottom="5.0" left="5.0" top="5.0" />
      </VBox.margin>
   </Text>
  <ListView fx:id="personListView" VBox.vgrow="ALWAYS" />
   <opaqueInsets>
      <Insets />
   </opaqueInsets>
</VBox>
```
###### \resources\view\ResultDisplay.fxml
``` fxml

<StackPane fx:id="placeHolder" minHeight="100.0" minWidth="150.0" styleClass="pane-with-border" stylesheets="@DarkTheme.css" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
  <TextArea fx:id="resultDisplay" editable="false" prefHeight="150.0" prefWidth="600.0" promptText="Results will be displayed here." styleClass="result-display" StackPane.alignment="BOTTOM_CENTER">
      <StackPane.margin>
         <Insets />
      </StackPane.margin>
      <padding>
         <Insets left="150.0" />
      </padding></TextArea>
   <Text fill="#ffffff" fontSmoothingType="LCD" strokeType="OUTSIDE" strokeWidth="0.0" text="COMMAND RESULTS" wrappingWidth="150.0" StackPane.alignment="TOP_LEFT">
      <StackPane.margin>
         <Insets left="5" top="12.5" />
      </StackPane.margin>
      <font>
         <Font name="ADAM.CGPRO" size="14.0" />
      </font>
   </Text>
</StackPane>
```
