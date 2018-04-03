# yeggasd
###### \java\guitests\guihandles\PasswordBoxHandle.java
``` java
/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class PasswordBoxHandle extends NodeHandle<TextField> {
    public static final String PASSWORD_WINDOW_TITLE = "Password";

    public static final String PASSWORD_INPUT_FIELD_ID = "#passwordTextField";

    public PasswordBoxHandle(TextField passwordBoxNode) {
        super(passwordBoxNode);
    }

    /**
     * Returns the text in the command box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean run(String password) {
        click();
        guiRobot.interact(() -> getRootNode().setText(password));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);

        return !getStyleClass().contains(PasswordBox.ERROR_STYLE_CLASS);
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
```
###### \java\seedu\address\commons\util\SecurityUtilTest.java
``` java
public class SecurityUtilTest {
    private static final File TEST_DATA_FILE = new File("./src/test/data/sandbox/temp");
    private static final String TEST_DATA = "<xml>";
    private static final String TEST_PASSWORD =  "test";
    private static final String WRONG_PASSWORD = "wrong";


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void decrypt_noPassword_success() throws Exception {

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.decrypt(TEST_DATA_FILE);

        Scanner reader = new Scanner(TEST_DATA_FILE);
        String read = reader.nextLine();
        reader.close();

        assertEquals(TEST_DATA, read);
    }

    @Test
    public void encryptDecrypt_customisedPassword_success() throws Exception {
        byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encrypt(TEST_DATA_FILE, hashedPassword);
        SecurityUtil.decrypt(TEST_DATA_FILE, hashedPassword);

        Scanner reader = new Scanner(TEST_DATA_FILE);
        String read = reader.nextLine();
        reader.close();

        assertEquals(TEST_DATA, read);
    }

    @Test
    public void encryptDecrypt_wrongPassword_throwsWrongPasswordException() throws Exception {

        byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);
        byte[] hashedWrong = SecurityUtil.hashPassword(WRONG_PASSWORD);

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encrypt(TEST_DATA_FILE, hashedPassword);
        thrown.expect(WrongPasswordException.class);
        SecurityUtil.decrypt(TEST_DATA_FILE, hashedWrong);
    }

    @Test
    public void encrypt_wrongPasswordLength_throwsAssertionError() throws Exception {

        byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();
        hashedPassword = Arrays.copyOf(hashedPassword, 13);

        thrown.expect(AssertionError.class);
        SecurityUtil.encrypt(TEST_DATA_FILE, hashedPassword);
    }

    @Test
    public void decrypt_wrongPasswordLength_throwsAssertionError() throws Exception {

        byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();
        hashedPassword = Arrays.copyOf(hashedPassword, 13);

        thrown.expect(AssertionError.class);
        SecurityUtil.decrypt(TEST_DATA_FILE, hashedPassword);
    }
}
```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
    @Test
    public void execute_encryptedAddressBook_success() throws Exception {
        String encryptedFile = TEST_DATA_FOLDER + "encryptedAliceBensonAddressBook.xml";

        ImportCommand importCommand = prepareCommand(encryptedFile, model, TEST_PASSWORD);
        importCommand.executeUndoableCommand();
        SecurityUtil.encrypt(encryptedFile, TEST_PASSWORD);
        assertEquals(model.getAddressBook(), addressBookWithAliceAndBenson);
    }

    @Test
    public void execute_wrongPasswordEncryptedAddressBook_throwsCommandException() throws Exception {
        String encryptedFile = TEST_DATA_FOLDER + "encryptedAliceBensonAddressBook.xml";
        ImportCommand importCommand = prepareCommand(encryptedFile, model, TEST_PASSWORD + "1");
        thrown.expect(CommandException.class);
        importCommand.executeUndoableCommand();
    }
```
###### \java\seedu\address\logic\commands\PasswordCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code PasswordCommand}.
 */
public class PasswordCommandTest {
    private static final String TEST_PASSWORD = "test";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        PasswordCommand passwordCommand = prepareCommand(TEST_PASSWORD + "1");

        String expectedMessage = String.format(PasswordCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePassword(SecurityUtil.hashPassword(TEST_PASSWORD + "1"));

        assertCommandSuccess(passwordCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        PasswordCommand passwordFirstCommand = prepareCommand(TEST_PASSWORD);
        PasswordCommand passwordSecondCommand = prepareCommand(TEST_PASSWORD + "1");

        // same object -> returns true
        assertTrue(passwordFirstCommand.equals(passwordFirstCommand));

        // same values -> returns true
        PasswordCommand passwordFirstCommandCopy = prepareCommand(TEST_PASSWORD);
        assertTrue(passwordFirstCommand.equals(passwordFirstCommandCopy));

        // different types -> returns false
        assertFalse(passwordFirstCommand.equals(1));

        // null -> returns false
        assertFalse(passwordFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(passwordFirstCommand.equals(passwordSecondCommand));
    }

    /**
     * Returns a {@code PasswordCommand} with the parameter {@code password}.
     */
    private PasswordCommand prepareCommand(String password) {
        PasswordCommand passwordCommand = new PasswordCommand(password);
        passwordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return passwordCommand;
    }
}
```
###### \java\seedu\address\logic\commands\RemovePasswordCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model and Password) and unit tests
 * for {@code RemovePasswordCommand}.
 */
public class RemovePasswordCommandTest {
    private static final byte[] TEST_PASSWORD = (SecurityUtil.hashPassword("test"));
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Before
    public void setUp() {
        model.updatePassword(TEST_PASSWORD);
        model.updatePassword(TEST_PASSWORD);
    }
    @Test
    public void execute_removePassword_success() throws Exception {
        RemovePasswordCommand removepasswordCommand = prepareCommand();
        String expectedMessage = String.format(RemovePasswordCommand.MESSAGE_SUCCESS);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        expectedModel.updatePassword(null);
        assertCommandSuccess(removepasswordCommand, model, expectedMessage, expectedModel);
        assertEquals(model.getAddressBook().getPassword(), new Password(null, TEST_PASSWORD));

        expectedModel.updatePassword(null);
        assertCommandSuccess(removepasswordCommand, model, expectedMessage, expectedModel);
        assertEquals(model.getAddressBook().getPassword(), new Password(null, null));
    }

    /**
     * Returns a {@code RemovePasswordCommand}.
     */
    private RemovePasswordCommand prepareCommand() {
        RemovePasswordCommand removepasswordCommand = new RemovePasswordCommand();
        removepasswordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return removepasswordCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_password() throws Exception {
        PasswordCommand command = (PasswordCommand) parser.parseCommand(
                PasswordCommand.COMMAND_WORD + " test");
        assertEquals(new PasswordCommand("test"), command);
    }

    @Test
    public void parseCommand_nopassword() throws Exception {
        assertTrue(parser.parseCommand(RemovePasswordCommand.COMMAND_WORD) instanceof RemovePasswordCommand);
        assertTrue(parser.parseCommand(RemovePasswordCommand.COMMAND_WORD + " 3")
                instanceof RemovePasswordCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        ImportCommand command = (ImportCommand) parser.parseCommand(
                ImportCommand.COMMAND_WORD + " /data/addressbook.xml test");
        assertEquals(new ImportCommand("/data/addressbook.xml", "test"), command);
    }
```
###### \java\seedu\address\logic\parser\PasswordCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the PasswordCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the PasswordCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class PasswordCommandParserTest {

    private PasswordCommandParser parser = new PasswordCommandParser();

    @Test
    public void parse_validArgs_returnsParseCommand() {
        assertParseSuccess(parser, "1", new PasswordCommand("1"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PasswordCommand.INVALID_PASSWORD, PasswordCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\RemovePasswordCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RemovePasswordCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemovePasswordCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemovePasswordCommandParserTest {

    private RemovePasswordCommandParser parser = new RemovePasswordCommandParser();

    @Test
    public void parse_validArgs_returnsParseCommand() {
        Command command = parser.parse("");
        assertTrue(command instanceof RemovePasswordCommand);
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void createdWithPassword_passwordChanged_passwordChanged() throws Exception {
        AddressBook addressBookUpdatedPassword = new AddressBook("new");
        Password expectedPassword = new Password("new");
        assertEquals(expectedPassword, addressBookUpdatedPassword.getPassword());
    }

    @Test
    public void updatePasswordWithClass_passwordChanged_passwordUpdated() throws Exception {
        AddressBook addressBookUpdatedPassword = new AddressBookBuilder().withPerson(BOB).withPassword("test").build();
        addressBookUpdatedPassword.updatePassword(new Password("new"));
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(BOB).withPassword("new").build();
        assertEquals(expectedAddressBook, addressBookUpdatedPassword);
    }

    @Test
    public void updatePasswordWithBytes_passwordChanged_passwordUpdated() throws Exception {
        AddressBook addressBookUpdatedPassword = new AddressBookBuilder().withPerson(BOB).withPassword("test").build();
        addressBookUpdatedPassword.updatePassword(SecurityUtil.hashPassword("new"));
        addressBookUpdatedPassword.updatePassword(SecurityUtil.hashPassword("new"));
        AddressBook expectedAddressBook = new AddressBookBuilder().withPerson(BOB).withPassword("new").build();
        assertEquals(expectedAddressBook, addressBookUpdatedPassword);
    }
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
        @Override
        public Password getPassword() {
            return password;
        }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void addressBookEncryptedReadSaveWithPassword() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        Password testPassword = new Password(TEST_PASSWORD);
        original.updatePassword(testPassword);
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(testPassword).get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void addressBookEncryptedReadSaveWithFilePath() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        Password testPassword = new Password(TEST_PASSWORD);
        original.updatePassword(testPassword);
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(storageManager.getAddressBookFilePath(),
                                                                        testPassword).get();
        assertEquals(original, new AddressBook(retrieved));
    }
```
###### \java\seedu\address\storage\XmlAdaptedPasswordTest.java
``` java
public class XmlAdaptedPasswordTest {

    @Test
    public void toModelType_validPassword_returnsPassword() throws Exception {
        Password pass = new Password(hash("test"), hash("test"));
        XmlAdaptedPassword password = new XmlAdaptedPassword(pass);
        assertEquals(pass, password.toModelType());
    }

    private byte[] hash(String password) {
        return SecurityUtil.hashPassword(password);
    }
}
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath, Password password)
                                                                            throws Exception {
        return new XmlAddressBookStorage(filePath).readAddressBook(addToTestDataPathIfNotNull(filePath), password);
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void readAddressBookWithPassword_invalidAndValidPersonAddressBook_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBookWithPassword_wrongPassword_throwWrongPasswordException() throws Exception {
        String filePath = "TempEncryptedAddressBook.xml";
        File file = new File(TEST_DATA_FOLDER + filePath);
        SecurityUtil.encrypt(file, SecurityUtil.hashPassword("wrongPassword"));
        thrown.expect(WrongPasswordException.class);
        readAddressBook(filePath, new Password("test"));
    }
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void readAndSaveEncryptedAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        original.updatePassword(new Password("test"));
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBook(filePath, new Password("test")).get();
        assertEquals(original, new AddressBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        readBack = xmlAddressBookStorage.readAddressBook(filePath, new Password("test")).get();
        assertEquals(original, new AddressBook(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlAddressBookStorage.saveAddressBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readAddressBook(new Password("test")).get(); //file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_changedPassword_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        AddressBook original = getTypicalAddressBook();
        original.updatePassword(new Password("test"));
        original.updatePassword(SecurityUtil.hashPassword("new"));
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
        xmlAddressBookStorage.saveAddressBook(original, filePath);

        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBook(filePath, new Password(
                                        SecurityUtil.hashPassword("new"),
                                        SecurityUtil.hashPassword(TEST_PASSWORD))).get();
        assertEquals(original, new AddressBook(readBack));
    }
```
###### \java\seedu\address\testutil\AddressBookBuilder.java
``` java
    /**
     * Parses {@code password} into a {@code Password} and updates the {@code AddressBook} 's password
     * that we are building.
     */
    public AddressBookBuilder withPassword(String password) {
        addressBook.updatePassword(new Password(password));
        return this;
    }
```
###### \java\seedu\address\ui\PasswordBoxTest.java
``` java
public class PasswordBoxTest extends GuiUnitTest {
    private static final String CORRECT_PASSWORD = "test";
    private static final String WRONG_PASSWORD = "wrong";
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/PasswordBoxTest/");

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private ArrayList<String> defaultStyleOfPasswordBox;
    private ArrayList<String> errorStyleOfPasswordBox;
    private PasswordBoxHandle passwordBoxHandle;


    @Before
    public void setUp() throws Exception {

        Storage storageManager = setUpStorage();
        Model model = new ModelManager(storageManager.readAddressBook(new Password(CORRECT_PASSWORD)).get());

        PasswordBox commandBox = new PasswordBox(storageManager, model);
        passwordBoxHandle = new PasswordBoxHandle(getChildNode(commandBox.getRoot(),
                PasswordBoxHandle.PASSWORD_INPUT_FIELD_ID));
        uiPartRule.setUiPart(commandBox);

        defaultStyleOfPasswordBox = new ArrayList<>(passwordBoxHandle.getStyleClass());

        errorStyleOfPasswordBox = new ArrayList<>(defaultStyleOfPasswordBox);
        errorStyleOfPasswordBox.add(CommandBox.ERROR_STYLE_CLASS);
    }

    private String getTestFilePath(String fileName) {
        return TEST_DATA_FOLDER + fileName;
    }
    private Storage setUpStorage() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTestFilePath(
                                                                            "encryptedAddressBook.xml"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTestFilePath("prefs"));
        ReadOnlyJsonVenueInformation venueInformationStorage = new ReadOnlyJsonVenueInformation("vi");
        return new StorageManager(addressBookStorage, userPrefsStorage, venueInformationStorage);
    }

    @Test
    public void passwordBox_startingWithWrongPassword() {
        assertBehaviorForWrongPassword();
    }

    @Test
    public void passwordBox_startingWithCorrectPassword() {
        assertBehaviorForCorrectPassword();
    }

    @Test
    public void passwordBox_handleKeyPress() {
        passwordBoxHandle.run(WRONG_PASSWORD);
        assertEquals(errorStyleOfPasswordBox, passwordBoxHandle.getStyleClass());
        guiRobot.push(KeyCode.ESCAPE);
        assertEquals(errorStyleOfPasswordBox, passwordBoxHandle.getStyleClass());

        guiRobot.push(KeyCode.A);
        assertEquals(defaultStyleOfPasswordBox, passwordBoxHandle.getStyleClass());
    }

    /**
     * Use a wrong password, then verifies that <br>
     *      - the text remains resets <br>
     *      - the command box's style is the same as {@code errorStyleOfCommandBox}.
     */
    private void assertBehaviorForWrongPassword() {
        passwordBoxHandle.run(WRONG_PASSWORD);
        assertEquals("", passwordBoxHandle.getInput());
        assertEquals(errorStyleOfPasswordBox, passwordBoxHandle.getStyleClass());
    }

    /**
     * Enters the correct password, then verifies that <br>
     *      - the text is cleared <br>
     *      - the event {@code PasswordCorrectEvent} is raised.
     */
    private void assertBehaviorForCorrectPassword() {
        passwordBoxHandle.run(CORRECT_PASSWORD);
        assertEquals("", passwordBoxHandle.getInput());
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof PasswordCorrectEvent);
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEqual(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag ->
                assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, PersonCard.getColorStyleFor(tag)),
                        actualCard.getTagStyleClasses(tag)));
    }
```
