# limzk1994
###### /java/seedu/address/commons/util/EncryptionUtilTest.java
``` java
public class EncryptionUtilTest {

    private static final File TEMP_FILE = new File(TestUtil.getFilePathInSandboxFolder("tempFileTest.txt"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //test that decryption works
    @Test
    public void encryptDecryptResult() throws Exception {
        TEMP_FILE.createNewFile();
        String dataToWrite = "This is the string to write";
        FileWriter fileWriter = new FileWriter(TEMP_FILE);
        fileWriter.write(dataToWrite);
        fileWriter.close();

        EncryptionUtil.encrypt(TEMP_FILE);
        EncryptionUtil.decrypt(TEMP_FILE);

        Scanner fromFile = new Scanner(TEMP_FILE);
        String dataToRead = fromFile.nextLine();
        fromFile.close();

        assertEquals(dataToWrite, dataToRead);
    }

    // test that encryption works
    @Test
    public void encryptResultNotEqual() throws Exception {
        TEMP_FILE.createNewFile();
        String dataToWrite = "This is the string to write";
        FileWriter fileWriter = new FileWriter(TEMP_FILE);
        fileWriter.write(dataToWrite);
        fileWriter.close();

        EncryptionUtil.encrypt(TEMP_FILE);

        Scanner fromFile = new Scanner(TEMP_FILE);
        String dataToRead = null;
        if (fromFile.hasNext()) {
            dataToRead = fromFile.nextLine();
            fromFile.close();
        }

        assertNotEquals(dataToWrite, dataToRead);
    }
}
```
###### /java/seedu/address/logic/parser/GroupCommandParserTest.java
``` java
public class GroupCommandParserTest {

    private GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsGroupCommand() {
        // no leading and trailing whitespaces
        GroupCommand expectedGroupCommand =
                new GroupCommand(new PersonContainsGroupsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedGroupCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedGroupCommand);
    }
}
```
###### /java/seedu/address/logic/commands/GroupCommandTest.java
``` java
public class GroupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonContainsGroupsPredicate firstPredicate =
                new PersonContainsGroupsPredicate(Collections.singletonList("first"));
        PersonContainsGroupsPredicate secondPredicate =
                new PersonContainsGroupsPredicate(Collections.singletonList("second"));

        GroupCommand findFirstCommand = new GroupCommand(firstPredicate);
        GroupCommand findSecondCommand = new GroupCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        GroupCommand findFirstCommandCopy = new GroupCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noGroupFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        GroupCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code GroupCommand}.
     */
    private GroupCommand prepareCommand(String userInput) {
        GroupCommand command =
                new GroupCommand(new PersonContainsGroupsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(GroupCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### /java/seedu/address/model/UniqueGroupListTest.java
``` java
public class UniqueGroupListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueGroupList uniqueGroupList = new UniqueGroupList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueGroupList.asObservableList().remove(0);
    }
}
```
