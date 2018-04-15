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
###### \java\guitests\guihandles\PersonDetailsCardHandle.java
``` java
/**
 * Provides a handle to a person details card in the main window.
 */
public class PersonDetailsCardHandle extends NodeHandle<Node> {
    public static final String PERSON_DETAILS_CARD_PLACEHOLDER = "#personDetailsCard";

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String TIMETABLE_FIELD_ID = "#timeTable";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final List<Label> tagLabels;
    private final TableView timeTable;

    public PersonDetailsCardHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
        this.timeTable = getChildNode(TIMETABLE_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public TableView getTimeTable() {
        return timeTable;
    }

    public List<String> getTagStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such tag."));
    }
}

```
###### \java\seedu\address\commons\util\SecurityUtilTest.java
``` java
public class SecurityUtilTest {
    private static final File TEST_DATA_FILE = new File("./src/test/data/sandbox/temp");
    private static final File VALID_DATA_FILE = new File(
            "./src/test/data/XmlAddressBookStorageTest/validAddressBook.xml");
    private static final String TEST_DATA = "<xml>";
    private static final String TEST_PASSWORD =  "test";
    private static final String WRONG_PASSWORD = "wrong";
    private static final byte[] hashedPassword = SecurityUtil.hashPassword(TEST_PASSWORD);
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
    public void decrypt_fileProcessorPlainText_success() throws Exception {
        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.decrypt(TEST_DATA_FILE, hashedPassword);

        Scanner reader = new Scanner(TEST_DATA_FILE);
        String read = reader.nextLine();
        reader.close();

        assertEquals(TEST_DATA, read);
    }

    @Test
    public void encryptDecrypt_customisedPassword_success() throws Exception {

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
    public void decrypt_withPassword_throwsWrongPasswordException() throws Exception {
        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encrypt(TEST_DATA_FILE, hashedPassword);
        thrown.expect(WrongPasswordException.class);
        SecurityUtil.decrypt(TEST_DATA_FILE);
    }

    @Test
    public void encryptDecrypt_wrongPassword_throwsWrongPasswordException() throws Exception {

        SecurityUtil.encrypt(VALID_DATA_FILE, hashedPassword);
        thrown.expect(WrongPasswordException.class);
        SecurityUtil.decryptFile(VALID_DATA_FILE, new Password(WRONG_PASSWORD));
    }

    @Test
    public void encryptDecrypt_wrongPasswordBadPadding_throwsWrongPasswordException() throws Exception {
        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();

        SecurityUtil.encryptFile(TEST_DATA_FILE, new Password(TEST_PASSWORD));
        thrown.expect(WrongPasswordException.class);
        SecurityUtil.decryptFile(TEST_DATA_FILE, new Password(WRONG_PASSWORD));
    }

    @Test
    public void encryptDecryptFile_wrongPassword_throwsWrongPasswordException() throws Exception {
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

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();
        byte[] truncatedHashedPassword = Arrays.copyOf(hashedPassword, 13);

        thrown.expect(AssertionError.class);
        SecurityUtil.encrypt(TEST_DATA_FILE, truncatedHashedPassword);
    }

    @Test
    public void decrypt_wrongPasswordLength_throwsAssertionError() throws Exception {

        FileWriter writer = new FileWriter(TEST_DATA_FILE);
        writer.write(TEST_DATA);
        writer.close();
        byte[] truncatedHashedPassword = Arrays.copyOf(hashedPassword, 13);

        thrown.expect(AssertionError.class);
        SecurityUtil.decrypt(TEST_DATA_FILE, truncatedHashedPassword);
    }

    @Test
    public void decryptEncrypt_null_throwsNullPointerExceptionError() throws Exception {

        Assert.assertThrows(NullPointerException.class, () -> SecurityUtil.encrypt(null, hashedPassword));

        Assert.assertThrows(NullPointerException.class, () -> SecurityUtil.encrypt(TEST_DATA_FILE, null));

        Assert.assertThrows(NullPointerException.class, () -> SecurityUtil.decrypt(null));

        Assert.assertThrows(NullPointerException.class, () -> SecurityUtil.decrypt(null, hashedPassword));
        Assert.assertThrows(NullPointerException.class, () -> SecurityUtil.decrypt(TEST_DATA_FILE, null));

        Assert.assertThrows(NullPointerException.class, () -> SecurityUtil.hashPassword(null));

        Assert.assertThrows(NullPointerException.class, () -> SecurityUtil.decryptFile(TEST_DATA_FILE, null));
        Assert.assertThrows(NullPointerException.class, () -> SecurityUtil.decryptFile(null, new Password("asd")));

        Assert.assertThrows(NullPointerException.class, () ->  SecurityUtil.encryptFile(null, new Password("asd")));

    }

    @After
    public void reset() throws Exception {
        SecurityUtil.decrypt(VALID_DATA_FILE, hashedPassword);
    }
}
```
###### \java\seedu\address\commons\util\StringUtilTest.java
``` java
    //---------------- Tests for isOddEven --------------------------------------

    @Test
    public void isOddEven() {

        // EP: empty strings
        assertFalse(StringUtil.isOddEven("")); // Boundary value
        assertFalse(StringUtil.isOddEven("  "));

        // EP: odd with white space
        assertFalse(StringUtil.isOddEven(" odd ")); // Leading/trailing spaces
        assertFalse(StringUtil.isOddEven("od d"));  // Spaces in the middle

        // EP: even with white space
        assertFalse(StringUtil.isOddEven(" even ")); // Leading/trailing spaces
        assertFalse(StringUtil.isOddEven("ev en"));  // Spaces in the middle

        // EP: multiple words
        assertFalse(StringUtil.isOddEven("odd even"));
        assertFalse(StringUtil.isOddEven("even asd"));
        assertFalse(StringUtil.isOddEven("odd dsa"));

        // EP: valid odd or even, should return true
        assertTrue(StringUtil.isOddEven("odd"));
        assertTrue(StringUtil.isOddEven("even"));

        //EP: valid odd or even with different upper and lower case, should return true
        assertTrue(StringUtil.isOddEven("OdD"));
        assertTrue(StringUtil.isOddEven("EvEn"));
    }

    //---------------- Tests for getOddEven --------------------------------------

    /*
     * Equivalence Partitions: null, empty String, one word, multiple words, odd, even
     * and with different upper and lower case
     */

    @Test
    public void getOddEven_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.getOddEven(null);
    }

    @Test
    public void getOddEven_invalidStringGiven_nullReturned() {
        assertTrue(StringUtil.getOddEven("") == null);
        assertTrue(StringUtil.getOddEven("word") == null);
        assertTrue(StringUtil.getOddEven("words words") == null);
        assertTrue(StringUtil.getOddEven("odd odd") == null);
    }

    @Test
    public void getOddEven_validStringGiven_correctResult() {
        assertEquals(StringUtil.getOddEven("even"), Index.fromZeroBased(0));
        assertEquals(StringUtil.getOddEven("odd"), Index.fromZeroBased(1));
        assertEquals(StringUtil.getOddEven("eVeN"), Index.fromZeroBased(0));
        assertEquals(StringUtil.getOddEven("oDd"), Index.fromZeroBased(1));

    }

    //---------------- Tests for isDay --------------------------------------

    @Test
    public void isDay() {

        // EP: empty strings
        assertFalse(StringUtil.isDay("")); // Boundary value
        assertFalse(StringUtil.isDay("  "));

        // EP: days with white space
        assertFalse(StringUtil.isDay(" monday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("mon day"));  // Spaces in the middle

        assertFalse(StringUtil.isDay(" tuesday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("tues day"));  // Spaces in the middle

        assertFalse(StringUtil.isDay(" wednesday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("wed day"));  // Spaces in the middle

        assertFalse(StringUtil.isDay(" thursday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("thurs day"));  // Spaces in the middle

        assertFalse(StringUtil.isDay(" friday ")); // Leading/trailing spaces
        assertFalse(StringUtil.isDay("fri day"));  // Spaces in the middle

        // EP: multiple words
        assertFalse(StringUtil.isDay("friday monday"));
        assertFalse(StringUtil.isDay("monday asd"));
        assertFalse(StringUtil.isDay("asd dsa"));

        // EP: valid days, should return true
        assertTrue(StringUtil.isDay("monday"));
        assertTrue(StringUtil.isDay("tuesday"));
        assertTrue(StringUtil.isDay("wednesday"));
        assertTrue(StringUtil.isDay("thursday"));
        assertTrue(StringUtil.isDay("friday"));


        //EP: valid odd or even with different upper and lower case,  should return true
        assertTrue(StringUtil.isDay("MoNdaY"));
        assertTrue(StringUtil.isDay("TueSday"));
        assertTrue(StringUtil.isDay("weDNesDAy"));
        assertTrue(StringUtil.isDay("THURSDAY"));
        assertTrue(StringUtil.isDay("FriDAY"));
    }

    //---------------- Tests for capitalize --------------------------------------

    /*
     * Equivalence Partitions: null, empty String, one letter, one word and one word with multiple cases
     */

    @Test
    public void capitalize_nullGiven_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        StringUtil.capitalize(null);
    }

    @Test
    public void capitalize_emptyStringGiven_throwsIndexOutOfBoundException() {
        thrown.expect(IndexOutOfBoundsException.class);
        StringUtil.capitalize("");
    }

    @Test
    public void capitalize_validStringGiven_correctResult() {
        assertEquals(StringUtil.capitalize("e"), "E");
        assertEquals(StringUtil.capitalize("even"), "Even");
        assertEquals(StringUtil.capitalize("eVeN"), "Even");
    }
}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_PASSWORD = "test";
    public static final byte[] VALID_PASSWORD_HASH;
    public static final String MIXED_CASE_PASSWORD_COMMAND_WORD = "EnCrYpT";
    public static final String MIXED_CASE_REMOVEPASSWORD_COMMAND_WORD = "DeCrYpT";
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
###### \java\seedu\address\logic\commands\SelectCommandTest.java
``` java
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, ODD);
        assertExecutionSuccess(INDEX_FIRST_PERSON, EVEN);
        assertExecutionSuccess(INDEX_THIRD_PERSON, EVEN);
        assertExecutionSuccess(lastPersonIndex, EVEN);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, ODD, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON, ODD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, ODD, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON, ODD);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON, ODD);
        SelectCommand selectThirdCommand = new SelectCommand(INDEX_FIRST_PERSON, EVEN);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON, ODD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));

        //different odd/even -> returns false
        assertFalse(selectFirstCommand.equals(selectThirdCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String oddEven) {
        SelectCommand selectCommand = prepareCommand(index, oddEven);

        try {
            CommandResult commandResult = selectCommand.execute();
            assertEquals(String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased(), oddEven),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String oddEven, String expectedMessage) {
        SelectCommand selectCommand = prepareCommand(index, oddEven);

        try {
            selectCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private SelectCommand prepareCommand(Index index, String oddEven) {
        SelectCommand selectCommand = new SelectCommand(index, oddEven);
        selectCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_password() throws Exception {
        String password = PasswordCommand.COMMAND_WORD + " test";
        String[] input = parser.extractCommandArgs(password);
        PasswordCommand command = (PasswordCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new PasswordCommand("test"), command);
    }

    @Test
    public void parseCommand_nopassword() throws Exception {
        String removePassword = RemovePasswordCommand.COMMAND_WORD + " 3";
        assertTrue(parser.parseCommand(RemovePasswordCommand.COMMAND_WORD, EMPTY_ARG) instanceof RemovePasswordCommand);
        String[] input = parser.extractCommandArgs(removePassword);
        assertTrue(parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX])
                instanceof RemovePasswordCommand);
    }

    @Test
    public void parseCommand_import() throws Exception {
        String importFile = ImportCommand.COMMAND_WORD + " /data/addressbook.xml test";
        String[] input = parser.extractCommandArgs(importFile);
        ImportCommand command = (ImportCommand) parser.parseCommand(input[COMMAND_INDEX], input[ARG_INDEX]);
        assertEquals(new ImportCommand("/data/addressbook.xml", "test"), command);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    private static final String VALID_ODD = "odd";
    private static final String VALID_EVEN = "even";
    private static final String INVALID_ODDEVEN = "ord";
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseOddEven_validOddEven() throws Exception {
        assertEquals(VALID_ODD, ParserUtil.parseOddEven(VALID_ODD));
        assertEquals(VALID_EVEN, ParserUtil.parseOddEven(VALID_EVEN));

        //with trailing and leading spaces
        assertEquals(VALID_ODD, ParserUtil.parseOddEven(" " + VALID_ODD + " "));
        assertEquals(VALID_EVEN, ParserUtil.parseOddEven(" " + VALID_EVEN + " "));
    }

    @Test
    public void  parseOddEven_nullGive_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseOddEven(null));
    }

    @Test
    public void  parseOddEven_invalidOddEven_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseOddEven(INVALID_ODDEVEN));
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
    public void readAddressBookWithPassword_invalidAndValidPersonAddressBook_throwsDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBookWithPassword_wrongPassword_throwsWrongPasswordException() throws Exception {
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
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @After
    public void reset() throws Exception {
        String filePath = "TempEncryptedAddressBook.xml";
        File file = new File(TEST_DATA_FOLDER + filePath);
        SecurityUtil.decrypt(file, SecurityUtil.hashPassword("wrongPassword"));
    }
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
###### \java\seedu\address\testutil\TypicalOddEven.java
``` java
/**
 * A utility class containing Odd or Even String to be used in tests.
 */
public class TypicalOddEven {
    public static final String ODD = "Odd";
    public static final String EVEN = "Even";
    public static final Index EVEN_INDEX = Index.fromZeroBased(0);
    public static final Index ODD_INDEX = Index.fromZeroBased(1);
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

        PasswordBox passwordBox = new PasswordBox(storageManager, model);
        passwordBoxHandle = new PasswordBoxHandle(getChildNode(passwordBox.getRoot(),
                PasswordBoxHandle.PASSWORD_INPUT_FIELD_ID));
        uiPartRule.setUiPart(passwordBox);

        defaultStyleOfPasswordBox = new ArrayList<>(passwordBoxHandle.getStyleClass());

        errorStyleOfPasswordBox = new ArrayList<>(defaultStyleOfPasswordBox);
        errorStyleOfPasswordBox.add(PasswordBox.ERROR_STYLE_CLASS);
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
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDetailsDisplaysPerson(Person expectedPerson, PersonDetailsCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
        assertTrue(actualCard.getTimeTable() != null);
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
###### \java\systemtests\PasswordCommandSystemTest.java
``` java
/**
 * A system test class for the Password Command, which contains interaction with other UI components.
 */
public class PasswordCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void password() {
        /* ----------------------------------- Perform valid password operations  ----------------------------------- */

        /* Case: set password with no leading or trailing password -> password set */
        String command = PasswordCommand.COMMAND_WORD + " " + VALID_PASSWORD;
        assertCommandSuccess(command, VALID_PASSWORD);

        /* Case: set password with no leading or trailing password -> password set */
        command = "   " + PasswordCommand.COMMAND_WORD + "   " + VALID_PASSWORD + "   ";
        assertCommandSuccess(command, VALID_PASSWORD);

        /* Case: two parameters ->  password set as the whole string */
        command = "   " + PasswordCommand.COMMAND_WORD + "  " + VALID_PASSWORD + "  " + VALID_PASSWORD;
        assertCommandSuccess(command, VALID_PASSWORD + "  " + VALID_PASSWORD);

        /* Case: undo previous command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid password operations ---------------------------------- */

        /* Case: no parameters -> rejected */
        assertCommandFailure(PasswordCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, INVALID_PASSWORD, MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_PASSWORD_COMMAND_WORD + " " + VALID_PASSWORD, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code PasswordCommand}.<br>
     * 4. {@code PersonListPanel} remain unchanged.<br>
     * 5. {@code Model} and {@code Storage} is updated with password and encrypted accordingly.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String password) {
        Model expectedModel = getModel();
        byte[] hashedPassword = SecurityUtil.hashPassword(password);
        expectedModel.updatePassword(hashedPassword);
        String expectedResultMessage = String.format(MESSAGE_SUCCESS);
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\RemovePasswordCommandSystemTest.java
``` java
/**
 * A system test class for the RemovePassword Command, which contains interaction with other UI components.
 */
public class RemovePasswordCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void removePassword() {
        /* ----------------------------------- Perform valid password operations  ----------------------------------- */

        /* Case: set password and remove with no leading or trailing space in command -> no password change */
        String passwordCommand = PasswordCommand.COMMAND_WORD + " " + VALID_PASSWORD;
        String removeCommand = RemovePasswordCommand.COMMAND_WORD;
        assertCommandSuccess(passwordCommand, removeCommand);

        /* Case: set password and remove with trailing space in command -> no password change */
        passwordCommand = PasswordCommand.COMMAND_WORD + " " + VALID_PASSWORD;
        removeCommand = RemovePasswordCommand.COMMAND_WORD + " ";
        assertCommandSuccess(passwordCommand, removeCommand);

        /* Case: set password twice and remove -> no password change */
        passwordCommand = PasswordCommand.COMMAND_WORD + " " + VALID_PASSWORD;
        removeCommand = RemovePasswordCommand.COMMAND_WORD;
        getModel().updatePassword(VALID_PASSWORD_HASH);
        executeCommand(passwordCommand);
        assertCommandSuccess(passwordCommand, removeCommand);

        /* Case: undo previous command -> rejected */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* ----------------------------------- Perform invalid password operations ---------------------------------- */

        /* Case: mixed case command word -> rejected */
        assertCommandFailure(MIXED_CASE_REMOVEPASSWORD_COMMAND_WORD + " " + VALID_PASSWORD, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@RemovePasswordCommand}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 4. {@code PersonListPanel} remain unchanged.<br>
     * 6. Status bar remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String passwordCommand, String removeCommand) {
        Model expectedModel = getModel();
        expectedModel.updatePassword(VALID_PASSWORD_HASH);
        expectedModel.updatePassword(null);
        String expectedResultMessage = String.format(MESSAGE_SUCCESS);
        executeCommand(passwordCommand);
        executeCommand(removeCommand);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
