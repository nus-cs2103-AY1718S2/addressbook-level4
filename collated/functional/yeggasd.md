# yeggasd
###### \java\seedu\address\commons\events\ui\PasswordCorrectEvent.java
``` java
/**
 * Indicates a request for App start for Correct Password
 */
public class PasswordCorrectEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\PasswordWrongEvent.java
``` java

/**
 * Indicates a request to show Wrong Passowrd Dialog
 */
public class PasswordWrongEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\TimeTableEvent.java
``` java
/**
 * Represents a call for the TimeTable to be displayed
 */
public class TimeTableEvent extends BaseEvent {

    private final ObservableList<ArrayList<String>> timetable;

    public TimeTableEvent(ObservableList<ArrayList<String>> timetable) {
        this.timetable = timetable;
    }

    public ObservableList<ArrayList<String>> getTimeTable() {
        return timetable;
    }

    @Override
    public String toString() {
        return "TimeTableEvent";
    }
}
```
###### \java\seedu\address\commons\exceptions\WrongPasswordException.java
``` java
/**
 * Represents an error during decryption
 */
public class WrongPasswordException extends Exception {
    public WrongPasswordException(String message) {
        super(message);
    }
}

```
###### \java\seedu\address\commons\util\SecurityUtil.java
``` java
/**
 * Contains utility methods used for encrypting and decrypting files for Storage
 */
public class SecurityUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String XML = "xml";

    /**
     * Encrypts the given file using AES key created by the hash of the password.
     *
     * @param file Points to a valid file containing data
     * @param password Used to decrypt file
     * @throws IOException if file cannot be opened
     * @throws WrongPasswordException if password used is wrong
     */
    public static void encrypt(String file, String password)throws IOException, WrongPasswordException {
        requireNonNull(file);
        requireNonNull(password);
        byte[] hashedPassword = hashPassword(password);
        encrypt(new File(file), hashedPassword);
    }

    /**
     * Encrypts the given file using AES key created by hashed password.
     *
     * @param file Points to a valid file containing data
     * @param password Used to decrypt file
     * @throws IOException if file cannot be opened
     * @throws WrongPasswordException if password used is wrong
     */
    public static void encrypt(File file, byte[] password)throws IOException, WrongPasswordException {
        requireNonNull(file);
        requireNonNull(password);
        try {
            logger.info("Encrypting...");
            Key secretAesKey = createKey(password);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretAesKey);
            processFile(cipher, file);
            logger.info("Encrypted");
        } catch (InvalidKeyException ike) {
            logger.warning("ERROR: Wrong key length " + StringUtil.getDetails(ike));
            throw new AssertionError("Wrong key length");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            logger.severe("ERROR: Cannot find AES or padding in library.");
            throw new AssertionError("Cannot find AES or padding");
        }
    }

    /**
     * Test to see if the given file is plaintext using the by checking if it is a xml file.
     *
     * @param file Points to a valid file containing data
     * @throws IOException thrown if cannot open file
     * @throws WrongPasswordException if password used is wrong
     */
    public static void decrypt(File file)throws IOException, WrongPasswordException {
        requireNonNull(file);
        if (!checkXmlPlainText(file)) {
            throw new WrongPasswordException("File Encrypted!");
        }
    }

    /**
     * Decrypts the given file using AES key created by hashed password.
     *
     * @param file Points to a file to be decrypted
     * @param password Used to decrypt file
     * @throws IOException if file cannot be opened
     * @throws WrongPasswordException if password used is wrong
     */
    public static void decrypt(File file, byte[] password) throws IOException, WrongPasswordException {
        requireNonNull(file);
        requireNonNull(password);
        try {
            logger.info("Decrypting...");
            Key secretAesKey = createKey(password);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretAesKey);
            processFile(cipher, file);
            logger.info("Decrypted");
        } catch (InvalidKeyException ike) {
            logger.warning("ERROR: Wrong key length " + StringUtil.getDetails(ike));
            throw new AssertionError("Wrong key length");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            logger.severe("ERROR: Cannot find AES or padding in library.");
            throw new AssertionError("Cannot find AES or padding");
        }
    }

    /**
     * Decrypts or encrypts at the file using cipher passed.
     *
     * @param cipher Encrypts or Decrypts file given mode
     * @param file Points to a valid file containing data
     * @throws IOException if cannot open file
     * @throws WrongPasswordException if password used is wrong
     */
    private static void processFile(Cipher cipher, File file) throws IOException, WrongPasswordException {
        requireNonNull(file);
        requireNonNull(cipher);
        byte[] inputBytes = null;
        try {

            FileInputStream inputStream = new FileInputStream(file);
            inputBytes = new byte[(int) file.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(outputBytes);
            checkXmlPlainText(outputBytes);
            inputStream.close();
            outputStream.close();

        } catch (BadPaddingException e) {
            handleBadPaddingException(inputBytes, e);
        } catch (IllegalBlockSizeException e) {
            logger.info("Warning: Text already in plain text.");
        }
    }

    /**
     * Hashes the password provided to meet the required length for AES.
     * @param password to be hashed.
     * @return byte[] of the hashed password.
     */
    public static byte[] hashPassword(String password) {
        requireNonNull(password);
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            try {
                md.update(password.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.severe("UTF-8 not supported, using default but may not be able to decrypt in other computer.");
                md.update(password.getBytes());
            }
            byte[] hashedPassword = md.digest();
            return Arrays.copyOf(hashedPassword, 16);
        } catch (NoSuchAlgorithmException nsae) {
            throw new AssertionError("SHA-1 should exist");
        }
    }

    /**
     * Decrypt the given file with the current and if it fails the previous password.
     * if password is null, will not try to decrypt.
     * @param file Points to the file to be decrypted.
     * @param password Used to decrypt file.
     * @throws IOException if file cannot be opened.
     * @throws WrongPasswordException if password used is wrong.
     */
    public static void decryptFile (File file, Password password) throws IOException, WrongPasswordException {
        requireNonNull(file);
        requireNonNull(password);
        if (password.getPassword() != null) {
            try {
                decrypt(file, password.getPassword());
            } catch (WrongPasswordException e) {
                logger.info("Current Password don't work, trying previous password.");
                decrypt(file, password.getPrevPassword());
            }
        }
    }

    /**
     * Encrypt the given file with the current.
     * if password is null, will not try to encrypt.
     * @param file Points to the file to be decrypted.
     * @param password Used to decrypt file.
     * @throws IOException if file cannot be opened.
     * @throws WrongPasswordException if password used is wrong.
     */
    public static void encryptFile (File file, Password password) throws IOException, WrongPasswordException {
        requireNonNull(file);
        if (password != null && password.getPassword() != null) {
            encrypt(file, password.getPassword());
        }
    }
    /**
     * Generates a key.
     */
    private static Key createKey(byte[] password) {
        return new SecretKeySpec(password, "AES");
    }

    /**
     * Handles {@code BadPaddingException} by determining whether it is plain text or other case.
     * @param inputBytes Input data that caused this.
     * @param e Contains the exception details to throw with WrongPasswordException.
     * @throws WrongPasswordException if it is wrong password.
     */
    private static void handleBadPaddingException(byte[] inputBytes, BadPaddingException e)
                                                                            throws WrongPasswordException {
        if (!checkXmlPlainText(inputBytes)) {
            logger.severe("ERROR: Wrong PASSWORD length used ");
            throw new WrongPasswordException("Wrong PASSWORD.");

        } else {
            logger.info("Warning: Text already in plain text.");
        }
    }

    /**
     * Checks whether it is plain text by checking whether it is in the range of characters commonly used for the
     *  the whole data.
     * @param data Contains the file data.
     * @return true if it is highly likely to be plain text.
     */
    private static boolean checkXmlPlainText(byte[] data) {
        requireNonNull(data);
        String string = new String(data);
        return string.contains(XML);
    }

    /**
     * Checks whether it is plain text by checking whether it is in the range of characters commonly used for the
     *  the whole data.
     * @param file Points to file path.
     * @return true if it is highly likely to be plain text.
     */
    private static boolean checkXmlPlainText(File file) throws IOException {
        requireNonNull(file);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] inputBytes = new byte[(int) file.length()];
        inputStream.read(inputBytes);
        inputStream.close();
        return checkXmlPlainText(inputBytes);
    }
}
```
###### \java\seedu\address\commons\util\StringUtil.java
``` java
    /**
     * Returns true if {@code s} represents odd or even
     ** Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isOddEven(String s) {
        requireNonNull(s);
        return s.equalsIgnoreCase("even") || s.equalsIgnoreCase("odd");
    }

    /**
     * @param s The string to be checked
     * @return 0 if string is even
     *         1 if string is odd
     *         null otherwise.
     */
    public static Index getOddEven(String s) {
        requireNonNull(s);
        if (s.equalsIgnoreCase("even")) {
            return Index.fromZeroBased(0);
        } else if (s.equalsIgnoreCase("odd")) {
            return Index.fromZeroBased(1);
        } else {
            return null;
        }
    }

    /**
     * Returns true if (@code text) represents a day in the week
     * e.g. Monday
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isDay(String s) {
        requireNonNull(s);
        return s.equalsIgnoreCase("Monday") || s.equalsIgnoreCase("Tuesday")
                || s.equalsIgnoreCase("Wednesday") || s.equalsIgnoreCase("Thursday")
                || s.equalsIgnoreCase("Friday") || s.equalsIgnoreCase("Saturday")
                || s.equalsIgnoreCase("Sunday");
    }

    /**
     * Capitalizes the given (@code s)
     * @param s String to be capitalized
     * @return Capitalized String
     * @throws NullPointerException if {@code s} is null.
     */
    public static String capitalize(String s) {
        requireNonNull(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
```
###### \java\seedu\address\logic\commands\PasswordCommand.java
``` java
/**
 * Adds a password to the address book.
 */
public class PasswordCommand extends Command {

    public static final String COMMAND_WORD = "encrypt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Encrypts the data with the password provided. "
            + "Parameters: encrypt PASSWORD"
            + "Example: " + COMMAND_WORD + "test";
    public static final String INVALID_PASSWORD = "Password cannot be blank!";

    public static final String MESSAGE_SUCCESS = "Password updated.";

    private String password;
    /**
     * Creates an PasswordCommand to add the specified password
     */
    public PasswordCommand(String password) {
        requireNonNull(password);
        this.password = password;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        byte[] hashedPassword = SecurityUtil.hashPassword(password);
        model.updatePassword(hashedPassword);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PasswordCommand // instanceof handles nulls
                && password.equals(((PasswordCommand) other).password));
    }
}
```
###### \java\seedu\address\logic\commands\RemovePasswordCommand.java
``` java
/**
 * Removes password from the address book.
 */
public class RemovePasswordCommand extends Command {

    public static final String COMMAND_WORD = "decrypt";
    public static final String MESSAGE_SUCCESS = "Password removed and data decrypted.";
    @Override
    public CommandResult execute() {
        requireNonNull(model);
        model.updatePassword(null);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a (@code String oddEven)
     * Leading and trailing whitespaces will be trimmed.
     * @throws IllegalValueException if not odd or even
     */
    public static String parseOddEven(String oddEven) throws IllegalValueException {
        requireNonNull(oddEven);
        String trimmedOddEven = oddEven.trim();
        if (!StringUtil.isOddEven(trimmedOddEven)) {
            throw new IllegalValueException(MESSAGE_NOT_ODDEVEN);
        }
        return trimmedOddEven;
    }
```
###### \java\seedu\address\logic\parser\PasswordCommandParser.java
``` java
/**
 * Parses input arguments and creates a new PasswordCommand object
 */
public class PasswordCommandParser implements Parser<PasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PasswordCommand
     * and returns an PasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PasswordCommand parse(String arguments) throws ParseException {
        String args = arguments.trim();
        if ("".equals(args)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PasswordCommand.INVALID_PASSWORD,
                            PasswordCommand.MESSAGE_USAGE));
        }
        return new PasswordCommand(args);
    }
}
```
###### \java\seedu\address\logic\parser\RemovePasswordCommandParser.java
``` java
/**
 * Parses input arguments and creates a new RemovePasswordCommand object
 */
public class RemovePasswordCommandParser implements Parser<RemovePasswordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePasswordCommand
     * and returns an RemovePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePasswordCommand parse(String arguments) {
        return new RemovePasswordCommand();
    }
}
```
###### \java\seedu\address\logic\parser\SelectCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            String[] argsArray = trimmedArgs.split("\\s+");
            if (argsArray.length != 2) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
            }

            Index index = ParserUtil.parseIndex(argsArray[0]);
            if (!StringUtil.isOddEven(argsArray[1])) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
            }
            String oddEven = argsArray[1];
            return new SelectCommand(index, oddEven);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);

        checkPasswordChanged();

        ui.start(primaryStage); (
                (UiManager) ui).openBirthdayNotification();
    }

    /**
     * Checks whether password is changed, if so make UI as {@code PasswordUiManager} instead
     * using polymorphism.
     */
    private void checkPasswordChanged() {
        if (passwordChanged) {
            ui = new PasswordUiManager(storage, model, ui);
        }
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public AddressBook() {
        password = new Password();
    }

    public AddressBook(String password) {
        this.password = new Password(password);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    @Override
    public Password getPassword() {
        return password;
    }

    /**
     * Updates the password of this {@code AddressBook}.
     * @param newPassword  will be the new password.
     */
    public void updatePassword (byte[] newPassword) {
        password.updatePassword(newPassword);
    }

    /**
     * Updates the password of this {@code AddressBook}.
     * @param newPassword  will be the new password.
     */
    public void updatePassword (Password newPassword) {
        password.updatePassword(newPassword);
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Updates the password with the given password.
     */
    void updatePassword(byte[] password);
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void updatePassword(byte[] password) {
        addressBook.updatePassword(password);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\Password.java
``` java
/**
 * Represents the password of the address book
 * Guarantees: current and previous password are present.
 */
public class Password {
    private byte[] currentPassword;
    private byte[] prevPassword;

    public Password() {
    }

    public Password(String password) {
        currentPassword = SecurityUtil.hashPassword(password);
        prevPassword = currentPassword;
    }

    public Password(byte[] password, byte[] prevPassword) {
        currentPassword = password;
        this.prevPassword = prevPassword;
    }

    public byte[] getPassword() {
        return currentPassword;
    }

    /**
     * Getter for previous password.
     * @return prevPassword.
     */
    public byte[] getPrevPassword() {
        return prevPassword;
    }

    /**
     * Updates the password.
     * @param password is the password to be used. Cannot be null.
     */
    public void updatePassword(Password password) {
        requireNonNull(password);

        prevPassword = password.getPrevPassword();
        currentPassword = password.getPassword();
    }

    /**
     * Similar to {@link #updatePassword(Password)}.
     * @param password is the password to be used.
     */
    public void updatePassword(byte[] password) {
        prevPassword = currentPassword;
        currentPassword = password;
    }

    @Override
    public String toString() {
        if (currentPassword != null) {
            return new String(currentPassword);
        }
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && Arrays.equals(this.currentPassword, (((Password) other).currentPassword))
                && Arrays.equals(this.prevPassword, (((Password) other).prevPassword))); // state check
    }

    @Override
    public int hashCode() {
        return currentPassword.hashCode();
    }
}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns the hashed password
     */
    Password getPassword();
```
###### \java\seedu\address\storage\AddressBookStorage.java
``` java
    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(Password password) throws DataConversionException,
                                                                            IOException, WrongPasswordException;
```
###### \java\seedu\address\storage\Storage.java
``` java
    @Override
    Optional<ReadOnlyAddressBook> readAddressBook(Password password)
            throws DataConversionException, IOException, WrongPasswordException;
```
###### \java\seedu\address\storage\XmlAdaptedPassword.java
``` java
/**
 * JAXB-friendly version of the Password.
 */
public class XmlAdaptedPassword {

    @XmlElement
    private byte[] currPassword;

    @XmlElement
    private byte[] prevPassword;

    /**
     * Constructs an XmlAdaptedPassword.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPassword() {}

    /**
     * Constructs an {@code XmlAdaptedPassword} with the given password.
     */
    public XmlAdaptedPassword(Password password) {
        this.currPassword = password.getPassword();
        this.prevPassword = password.getPrevPassword();
    }

    /**
     * Converts this jaxb-friendly adapted password object into the model's Password object.
     *
     */
    public Password toModelType() {
        return new Password(currPassword, prevPassword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPassword)) {
            return false;
        }

        XmlAdaptedPassword otherPassword = (XmlAdaptedPassword) other;
        return Arrays.equals(currPassword, otherPassword.currPassword)
                && Arrays.equals(prevPassword, otherPassword.prevPassword);
    }
}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Password password) throws DataConversionException, IOException,
            WrongPasswordException {
        return readAddressBook(filePath, password);
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath, Password password)
            throws DataConversionException, IOException, WrongPasswordException {
        requireNonNull(filePath);
        requireNonNull(password);

        File addressBookFile = new File(filePath);

        if (!addressBookFile.exists()) {
            logger.info("AddressBook file "  + addressBookFile + " not found");
            return Optional.empty();
        }
        File file = new File(filePath);
        SecurityUtil.decryptFile(file, password);
        XmlSerializableAddressBook xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(file);
        SecurityUtil.encryptFile(file, password);

        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + addressBookFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        hideAllPanel();

        Person person = event.getNewSelection().person;
        int oddEvenIndex = event.getOddEvenIndex();

        personDetailsCard.update(person, oddEvenIndex);
        userDetailsPlaceholder.toFront();
    }

    /**
     * Hides all the panels
     */
    private void hideAllPanel() {
        userDetailsPlaceholder.toBack();
        venuePlaceholder.toBack();
        mapsPlaceholder.toBack();
        birthdayPlaceholder.toBack();
        timetableUnionPlaceholder.toBack();
        aliasListPlaceholder.toBack();
    }
```
###### \java\seedu\address\ui\PasswordBox.java
``` java
/**
 * The UI component that is responsible for receiving user password inputs.
 */
public class PasswordBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "PasswordBox.fxml";

    private final Logger logger = LogsCenter.getLogger(PasswordBox.class);
    private ListElementPointer historySnapshot;
    private final Storage storage;
    private final Model model;

    @FXML
    private PasswordField passwordTextField;

    public PasswordBox(Storage storage, Model model) {
        super(FXML);

        this.storage = storage;
        this.model = model;

        // calls #setStyleToDefault() whenever there is a change to the text of the Passowrd box.
        passwordTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handlePasswordInputChanged() {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        String input = passwordTextField.getText();
        try {
            passwordTextField.setText("");
            addressBookOptional = storage.readAddressBook(new Password(input));
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            model.resetData(initialData);
            raise(new PasswordCorrectEvent());
        } catch (WrongPasswordException e) {
            logger.warning("Wrong password used. Trying again.");
            raise(new PasswordWrongEvent());
            setStyleToIndicateCommandFailure();
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }
    }

    /**
     * Sets the password box style to use the default style.
     */
    private void setStyleToDefault() {
        passwordTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the password box style to indicate a wrong password.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = passwordTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
```
###### \java\seedu\address\ui\PasswordUiManager.java
``` java
/**
 * The manager of the Password UI component.
 */
public class PasswordUiManager extends ComponentManager implements Ui {
    public static final String WRONG_PASSWORD_ERROR_DIALOG_STAGE_TITLE = "Password Wrong Error";
    public static final String WRONG_PASSWORD_ERROR_DIALOG_HEADER_MESSAGE = "Wrong Password used";
    public static final String WRONG_PASSWORD_ERROR_DIALOG_CONTENT_MESSAGE = "Try Again";
    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";
    private static final double MAX_WINDOW_SIZE = Double.MAX_VALUE;

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private Storage storage;
    private Model model;
    private Ui ui;

    private PasswordWindow pw;
    private Stage primaryStage;

    public PasswordUiManager(Storage storage, Model model, Ui ui) {
        super();
        this.storage = storage;
        this.model = model;
        this.ui = ui;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");
        this.primaryStage = primaryStage;
        try {
            pw = new PasswordWindow(primaryStage, model, storage);
            pw.show();
            pw.fillInnerParts();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private void showPasswordWrongAlertAndWait(String description, String details) {
        showAlertDialogAndWait(AlertType.ERROR, WRONG_PASSWORD_ERROR_DIALOG_STAGE_TITLE, description, details);
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(primaryStage, type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        pw.hide();
        pw.releaseResources();
    }
    @Subscribe
    private void handlePasswordCorrectEvent(PasswordCorrectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        primaryStage.setResizable(true);
        primaryStage.setMaxHeight(MAX_WINDOW_SIZE);
        ui.start(primaryStage); (
                (UiManager) ui).openBirthdayNotification();
    }

    @Subscribe
    private void handlePasswordWrongEvent(PasswordWrongEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        showPasswordWrongAlertAndWait(WRONG_PASSWORD_ERROR_DIALOG_HEADER_MESSAGE,
                WRONG_PASSWORD_ERROR_DIALOG_CONTENT_MESSAGE);
    }

}
```
###### \java\seedu\address\ui\PasswordWindow.java
``` java
/**
 * The Password Window. Provides the basic application layout containing
 * space where other JavaFX elements can be placed.
 */
public class PasswordWindow extends UiPart<Stage> {
    private static final String PASSWORDBOX_TITLE = "Key In Password";
    private static final String FXML = "PasswordWindow.fxml";

    private final Storage storage;
    private final Model model;

    private Stage primaryStage;

    @FXML
    private StackPane passwordBoxPlaceholder;

    public PasswordWindow(Stage primaryStage, Model model, Storage storage) {
        super(FXML, primaryStage);
        // Set dependencies
        this.primaryStage = primaryStage;
        this.storage = storage;
        this.model = model;

        setTitle(PASSWORDBOX_TITLE);
        registerAsAnEventHandler(this);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        PasswordBox passwordBox = new PasswordBox(storage, model);
        passwordBoxPlaceholder.getChildren().add(passwordBox.getRoot());
    }

    void hide() {
        primaryStage.hide();
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    void releaseResources() {
    }
}
```
###### \java\seedu\address\ui\PersonDetailsCard.java
``` java
/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonDetailsCard extends UiPart<Region> {

    private static final String FXML = "PersonDetailsCard.fxml";
    private static final String[] TAG_COLOR_STYLES = { "teal", "cyan", "purple", "indigo", "lightgreen", "bluegrey",
                                                         "amber", "yellow"};
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;
    private TimeTablePanel timeTablePanel;
    private ArrayList<Label> tagLabels = new ArrayList<>();
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label birthday;
    @FXML
    private FlowPane tags;
    @FXML
    private StackPane timetablePlaceholder;

    public PersonDetailsCard() {
        super(FXML);
        person = null;
    }


    public PersonDetailsCard(Person person, int oddEvenIndex) {
        super(FXML);
        this.person = person;
        timeTablePanel = new TimeTablePanel();
        timetablePlaceholder.getChildren().add(timeTablePanel.getRoot());
        update(person, oddEvenIndex);
    }

    /**
     * Updates the {@code PersonDetailsCard} for the new person selected
     * @param person the Person that is currently selected
     */
    public void update(Person person, int oddEvenIndex) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        birthday.setText(person.getBirthday().value);
        initializeTags(person);
        initializeTimetable(person, oddEvenIndex);
    }

    /**
     * Initializes the tag labels for {@code person}.
     */
    private void initializeTags(Person person) {
        int i = 0;
        for (Tag tag : person.getTags()) {
            if (tagLabels.size() >  i) {
                Label tagLabel = tagLabels.get(i);
                tagLabel.setVisible(true);
                tagLabel.getStyleClass().remove(getColorStyleFor(tagLabel.getText()));
                tagLabels.get(i).setText(tag.tagName);
                tagLabels.get(i).getStyleClass().add(getColorStyleFor(tag.tagName));
            } else {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.getStyleClass().add(getColorStyleFor(tag.tagName));

                tagLabels.add(tagLabel);
                tags.getChildren().add(tagLabel);
            }
            i++;
        }
        for (; i < tagLabels.size(); i++) {
            tagLabels.get(i).setVisible(false);
        }
    }

    /**
     * Initializes the timetable for {@code person}.
     */
    private void initializeTimetable(Person person, int oddEvenIndex) {
        Timetable timeTable = person.getTimetable();
        ArrayList<ArrayList<String>> personTimeTable = timeTable.getTimetable().get(oddEvenIndex);
        ObservableList<ArrayList<String>> timeTableList = FXCollections.observableArrayList(personTimeTable);
        timeTablePanel = new TimeTablePanel(timeTableList);
        timetablePlaceholder.getChildren().add(timeTablePanel.getRoot());
        timeTablePanel.setStyle();
    }

    /**
     * @param tagName
     * @return colorStyle for {@code tagName}'s label.
     */
    public static String getColorStyleFor(String tagName) {
        return TAG_COLOR_STYLES[Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length];
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDetailsCard)) {
            return false;
        }

        // state check
        PersonDetailsCard card = (PersonDetailsCard) other;
        return person.equals(card.person);
    }
}
```
###### \java\seedu\address\ui\TimeTablePanel.java
``` java
/**
 * A ui for the info panel that is displayed when the timetable command is called.
 */
public class TimeTablePanel extends UiPart<Region> {
    private static final int MAX_COLUMN_WIDTH = 200;
    private static final int MIN_COLUMN_WIDTH = 75;
    private static final String COLUMNHEADER_STYLE_CLASS = "column-header";
    private static final String TABLECELL_STYLE_CLASS = "table-cell";
    private static final String EMPTY_STYLE_CLASS = "timetable-cell-empty";
    private static final String[] MOD_COLOR_STYLES = { "modteal", "modsandybrown", "modplum", "modyellow",
                                                         "modyellow", "modcyan", "modpink", "modlightblue", "modpurple",
                                                         "modindigo", "modlightgreen", "modorange", "modgoldbrown"};
    private static final String FXML = "TimeTablePanel.fxml";
    private static final HashMap<Integer, String> TAKEN_COLOR = new HashMap<>();
    private static final int MODNAME_LENGTH = 6;

    private ArrayList<TableColumn<ArrayList<String>, String>> columns;
    @FXML
    private TableView timeTable;
    @FXML
    private TableColumn<ArrayList<String>, String> day;
    @FXML
    private TableColumn<ArrayList<String>, String> eightAm;
    @FXML
    private TableColumn<ArrayList<String>, String> nineAm;
    @FXML
    private TableColumn<ArrayList<String>, String> tenAm;
    @FXML
    private TableColumn<ArrayList<String>, String> elevenAm;
    @FXML
    private TableColumn<ArrayList<String>, String> twelvePm;
    @FXML
    private TableColumn<ArrayList<String>, String> onePm;
    @FXML
    private TableColumn<ArrayList<String>, String> twoPm;
    @FXML
    private TableColumn<ArrayList<String>, String> threePm;
    @FXML
    private TableColumn<ArrayList<String>, String> fourPm;
    @FXML
    private TableColumn<ArrayList<String>, String> fivePm;
    @FXML
    private TableColumn<ArrayList<String>, String> sixPm;
    @FXML
    private TableColumn<ArrayList<String>, String> sevenPm;
    @FXML
    private TableColumn<ArrayList<String>, String> eightPm;


    public TimeTablePanel() {
        super(FXML);
        timeTable.setItems(null);
    }

    public TimeTablePanel(ObservableList<ArrayList<String>> schedules) {
        super(FXML);
        TAKEN_COLOR.clear();
        timeTable.setItems(schedules);
        timeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initializeColumns();
        initializeTableColumns();
        day.setMinWidth(100);
        day.setMaxWidth(100);
    }

    /**
     * Initializes columns
     */
    private void initializeColumns() {
        columns = new ArrayList<>();
        columns.add(day);
        columns.add(eightAm);
        columns.add(nineAm);
        columns.add(tenAm);
        columns.add(elevenAm);
        columns.add(twelvePm);
        columns.add(onePm);
        columns.add(twoPm);
        columns.add(threePm);
        columns.add(fourPm);
        columns.add(fivePm);
        columns.add(sixPm);
        columns.add(sevenPm);
        columns.add(eightPm);
    }

    /**
     * Initializes table columns
     */
    private void initializeTableColumns() {
        for (int i = 0; i < columns.size(); i++) {
            final int j = i;
            columns.get(i).setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(j)));
            columns.get(i).impl_setReorderable(false);
            if (j != 0) {
                columns.get(i).setMinWidth(MIN_COLUMN_WIDTH);
                columns.get(i).setMaxWidth(MAX_COLUMN_WIDTH);
            }
            columns.get(i).setSortable(false);
        }
    }

    /**
     * Sets the columns to the style for each value.
     */
    public void setStyle() {
        for (int i = 0; i < columns.size(); i++) {
            TableColumn<ArrayList<String>, String> columnToBeSet = columns.get(i);
            setStyleForColumn(columnToBeSet);
        }
    }

    /**
     * Sets the style of each column.
     * @param columnToBeSet is the column that would be set
     */
    private void setStyleForColumn (TableColumn<ArrayList<String>, String> columnToBeSet) {
        columnToBeSet.setCellFactory(column -> {
            return setStyleForCell();
        });
    }

    /**
     * Sets the style of the cell given the data and return it
     * @return the tablecell with its style set.
     */
    private TableCell<ArrayList<String>, String> setStyleForCell () {
        return new TableCell<ArrayList<String>, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    removeAllStyle(this);
                    if ("".equals(getItem())) {
                        getStyleClass().add(EMPTY_STYLE_CLASS);
                    } else if (StringUtil.isDay(getItem())) {
                        getStyleClass().add(COLUMNHEADER_STYLE_CLASS);
                    } else {
                        getStyleClass().add(getColorStyleFor(getItem()));
                    }
                }
            }
        };
    }

    /**
     * Removes all styles present in cell
     * @param tableCell Cell with its style to be removed
     */
    private static void removeAllStyle(TableCell<ArrayList<String>, String> tableCell) {
        for (String color : MOD_COLOR_STYLES) {
            tableCell.getStyleClass().remove(color);
        }
        tableCell.getStyleClass().remove(EMPTY_STYLE_CLASS);
        tableCell.getStyleClass().remove(TABLECELL_STYLE_CLASS);
    }

    /**
     * Returns a Color Style for the Module based on its hashcode.
     * @param lessonName
     * @return colorStyle for {@code modName}'s label.
     */
    private static String getColorStyleFor(String lessonName) {
        String modName = lessonName.substring(0, MODNAME_LENGTH);
        int colorIndex = Math.abs(modName.hashCode()) % MOD_COLOR_STYLES.length;
        int index = 0;
        //finds the next avaliable index that is not taken.
        while (index < MOD_COLOR_STYLES.length && TAKEN_COLOR.get(colorIndex) != null
                && !TAKEN_COLOR.get(colorIndex).equals(modName)) {
            colorIndex = (colorIndex + 1) % MOD_COLOR_STYLES.length;
            index++;
        }
        TAKEN_COLOR.put(colorIndex, modName);
        return MOD_COLOR_STYLES[colorIndex];
    }
}
```
###### \resources\view\DarkTheme.css
``` css
#passwordTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #383838 #383838 #ffffff #383838;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}
```
###### \resources\view\DarkTheme.css
``` css
.modteal {
    -fx-text-fill: black;
    -fx-background-color: #009688;
}

.modyellow {
    -fx-text-fill: black;
    -fx-background-color: #FFEB3B;
}

.modyellow {
    -fx-text-fill: black;
    -fx-background-color: #FFEB3B;
}

.modplum {
    -fx-text-fill: black;
    -fx-background-color: #DDA0DD;
}

.modsandybrown {
    -fx-text-fill: black;
    -fx-background-color: #F4A460;
}

.modcyan {
    -fx-text-fill: black;
    -fx-background-color: #00BCD4;
}

.modpink {
    -fx-text-fill: black;
    -fx-background-color: #FFB6C1;
}

.modlightblue {
    -fx-text-fill: black;
    -fx-background-color: #ADD8E6;
}

.modpurple{
    -fx-text-fill: white;
    -fx-background-color: #9C27B0;
}
.modindigo {
    -fx-text-fill: white;
    -fx-background-color: #3F51B5;
}
.modlightgreen {
    -fx-text-fill: black;
    -fx-background-color: #8BC34A;
}

.modorange {
    -fx-text-fill: black;
    -fx-background-color: #fab297;
}

.modgoldbrown {
    -fx-text-fill: white;
    -fx-background-color: #7F6600;
}
```
###### \resources\view\PasswordBox.fxml
``` fxml
<StackPane prefHeight="99.0" prefWidth="200.0" styleClass="anchor-pane" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <PasswordField fx:id="passwordTextField" onAction="#handlePasswordInputChanged" onKeyPressed="#handleKeyPress" />
   <BorderPane prefHeight="200.0" prefWidth="200.0">
      <top>
         <Text fill="WHITE" strokeType="OUTSIDE" strokeWidth="0.0" text="Password" BorderPane.alignment="CENTER" />
      </top>
   </BorderPane>
</StackPane>
```
###### \resources\view\PasswordWindow.fxml
``` fxml
<fx:root maxHeight="90.0" minHeight="80.0" minWidth="400.0" resizable="false" type="javafx.stage.Stage" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
  <icons>
    <Image url="@/images/address_book_32.png" />
  </icons>
  <scene>
    <Scene>
      <stylesheets>
        <URL value="@DarkTheme.css" />
        <URL value="@Extensions.css" />
      </stylesheets>
      <VBox>
        <StackPane fx:id="passwordBoxPlaceholder" styleClass="pane-with-border" VBox.vgrow="NEVER">
          <padding>
            <Insets bottom="5" left="10" right="10" top="5" />
          </padding>
        </StackPane>
      </VBox>
    </Scene>
  </scene>
</fx:root>
```
###### \resources\view\PersonDetailsCard.fxml
``` fxml
<VBox fx:id="personDetailsCard" prefHeight="329.0" prefWidth="724.0" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <VBox prefHeight="378.0" prefWidth="372.0">
         <children>
            <HBox prefHeight="174.0" prefWidth="173.0">
               <children>
                  <VBox alignment="BOTTOM_LEFT" prefHeight="200.0" prefWidth="100.0">
                     <children>
                        <HBox alignment="CENTER_RIGHT" prefHeight="154.0" prefWidth="100.0" spacing="5">
                           <children>
                              <FlowPane fx:id="tags" prefHeight="143.0" prefWidth="82.0" rowValignment="TOP" />
                           </children>
                        </HBox>
                     </children>
                  </VBox>
                  <VBox prefHeight="166.0" prefWidth="482.0">
                     <children>
                        <VBox alignment="CENTER" prefHeight="60.0" prefWidth="366.0">
                           <children>
                              <Label fx:id="name" prefHeight="27.0" prefWidth="458.0" styleClass="display_big_label" text="\$first" />
                              <HBox prefHeight="100.0" prefWidth="200.0">
                                 <children>
                                    <HBox prefHeight="8.0" prefWidth="16.0" />
                                 </children>
                              </HBox>
                           </children>
                        </VBox>
                        <HBox prefHeight="100.0" prefWidth="200.0">
                           <children>
                              <VBox alignment="CENTER_LEFT" minHeight="105.0" prefHeight="106.0" prefWidth="210.0">
                                 <padding>
                                    <Insets bottom="5" left="15" right="5" top="5" />
                                 </padding>
                                 <children>
                                    <Label styleClass="display_small_label" text="Phone" />
                                    <Label styleClass="display_small_label" text="Address" />
                                    <Label styleClass="display_small_label" text="Email" />
                                    <Label styleClass="display_small_label" text="Birthday" />
                                 </children>
                              </VBox>
                              <VBox alignment="CENTER_LEFT" minHeight="105" prefHeight="106.0" prefWidth="484.0">
                                 <padding>
                                    <Insets bottom="5" left="15" right="5" top="5" />
                                 </padding>
                                 <children>
                                    <Label fx:id="phone" prefHeight="27.0" prefWidth="347.0" styleClass="display_small_label" text="\$phone" />
                                    <Label fx:id="address" prefHeight="27.0" prefWidth="370.0" styleClass="display_small_label" text="\$address" wrapText="true" />
                                    <Label fx:id="email" prefHeight="27.0" prefWidth="362.0" styleClass="display_small_label" text="\$email" wrapText="true" />
                                    <Label fx:id="birthday" prefHeight="27.0" prefWidth="368.0" styleClass="display_small_label" text="\$birthday" />
                                 </children>
                              </VBox>
                           </children>
                        </HBox>
                     </children>
                  </VBox>
               </children>
            </HBox>
         </children>
      </VBox>
      <StackPane fx:id="timetablePlaceholder" prefHeight="430.0" prefWidth="724.0" VBox.vgrow="ALWAYS" />
   </children>
   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
</VBox>
```
###### \resources\view\TimeTablePanel.fxml
``` fxml

<StackPane fx:id="timetablePlaceholder" styleClass="pane-with-border" xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <TableView fx:id="timeTable" stylesheets="@DarkTheme.css">
         <columns>
             <TableColumn fx:id="day" prefWidth="100.0" text="" />
             <TableColumn fx:id="eightAm" prefWidth="60.0" text="0800" />
             <TableColumn fx:id="nineAm" prefWidth="60.0" text="0900" />
             <TableColumn fx:id="tenAm" prefWidth="60.0" text="1000" />
             <TableColumn fx:id="elevenAm" prefWidth="60.0" text="1100" />
             <TableColumn fx:id="twelvePm" prefWidth="60.0" text="1200" />
             <TableColumn fx:id="onePm" prefWidth="60.0" text="1300" />
             <TableColumn fx:id="twoPm" prefWidth="60.0" text="1400" />
             <TableColumn fx:id="threePm" prefWidth="60.0" text="1500" />
             <TableColumn fx:id="fourPm" prefWidth="60.0" text="1600" />
             <TableColumn fx:id="fivePm" prefWidth="60.0" text="1700" />
             <TableColumn fx:id="sixPm" prefWidth="60.0" text="1800" />
             <TableColumn fx:id="sevenPm" prefWidth="60.0" text="1900" />
             <TableColumn fx:id="eightPm" prefWidth="60.0" text="2000" />
         </columns></TableView>
    </children>
</StackPane>
```
