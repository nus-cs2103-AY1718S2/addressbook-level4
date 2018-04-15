# wenhao53
###### /java/seedu/address/logic/parser/ClassificationCommandParserTest.java
``` java
public class ClassificationCommandParserTest {

    private ClassificationCommandParser parser = new ClassificationCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ClassificationCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsClassifcationCommand() {
        // no leading and trailing whitespaces
        ClassificationCommand expectedClassificationCommand =
                new ClassificationCommand(new NameContainsClassificationPredicate(
                        Arrays.asList("underweight", "overweight")));
        assertParseSuccess(parser, "underweight overweight", expectedClassificationCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n underweight \n \t overweight  \t", expectedClassificationCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "notAValidKeyword", String.format(MESSAGE_INVALID_KEYWORD,
                ClassificationCommand.MESSAGE_USAGE));
    }



}
```
###### /java/seedu/address/logic/commands/ClassificationCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code ClassificationCommand}.
 */
public class ClassificationCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsClassificationPredicate firstPredicate =
                new NameContainsClassificationPredicate(Collections.singletonList("first"));
        NameContainsClassificationPredicate secondPredicate =
                new NameContainsClassificationPredicate(Collections.singletonList("second"));

        ClassificationCommand classificationFirstCommand = new ClassificationCommand(firstPredicate);
        ClassificationCommand classificationSecondCommand = new ClassificationCommand(secondPredicate);

        // same object -> returns true
        assertTrue(classificationFirstCommand.equals(classificationFirstCommand));

        // same values -> returns true
        ClassificationCommand classificationFirstCommandCopy = new ClassificationCommand(firstPredicate);
        assertTrue(classificationFirstCommand.equals(classificationFirstCommandCopy));

        // different types -> returns false
        assertFalse(classificationFirstCommand.equals(1));

        // null -> returns false
        assertFalse(classificationFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(classificationFirstCommand.equals(classificationSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        ClassificationCommand command = prepareCommand(" ");
        assertCommandFailure(command, expectedMessage);
    }

    @Test
    public void execute_incorrectKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_INVALID_KEYWORD, MESSAGE_USAGE);
        ClassificationCommand command = prepareCommand("notValid");
        assertCommandFailure(command, expectedMessage);
    }

    @Test
    public void execute_acceptableClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ClassificationCommand command = prepareCommand("acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE));
    }

    @Test
    public void execute_obeseClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ClassificationCommand command = prepareCommand("obese");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    @Test
    public void execute_overweightClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ClassificationCommand command = prepareCommand("overweight");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(GEORGE));
    }

    @Test
    public void execute_underweightClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        ClassificationCommand command = prepareCommand("underweight");
        assertCommandSuccess(command, expectedMessage, Collections.singletonList(FIONA));
    }

    @Test
    public void execute_overweightAndUnderweightClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        ClassificationCommand command = prepareCommand("underweight overweight");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIONA, GEORGE));
    }

    @Test
    public void execute_underweightAndAcceptableClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        ClassificationCommand command = prepareCommand("underweight acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE, FIONA));
    }

    @Test
    public void execute_underweightAndObeseClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ClassificationCommand command = prepareCommand("underweight obese");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, FIONA));
    }

    @Test
    public void execute_acceptableAndOverweightClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        ClassificationCommand command = prepareCommand("overweight acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, ELLE, GEORGE));
    }

    @Test
    public void execute_acceptableAndObeseClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        ClassificationCommand command = prepareCommand("obese acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
    }

    @Test
    public void execute_overweightAndObeseClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        ClassificationCommand command = prepareCommand("obese overweight");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, GEORGE));
    }

    @Test
    public void execute_allClassification_personFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        ClassificationCommand command = prepareCommand("underweight overweight obese acceptable");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    /**
     * Parses {@code userInput} into a {@code ClassificationCommand}.
     */
    private ClassificationCommand prepareCommand(String userInput) {
        ClassificationCommand command = new ClassificationCommand(
                new NameContainsClassificationPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ClassificationCommand command,
                                      String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    /**
     * Asserts that {@code command} is not successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - there is no {@code FilteredList<Person>} displayed<br>
     *     - the model remains the same after executing the {@code command}
     */
    private void assertCommandFailure(ClassificationCommand command, String expectedMessage) {
        CommandResult commandResult = command.execute();
        assertNotEquals(expectedMessage, commandResult.feedbackToUser);
    }
}
```
###### /java/seedu/address/model/person/HeightTest.java
``` java
public class HeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Height(null));
    }

    @Test
    public void constructor_invalidHeight_throwsIllegalArgumentException() {
        String invalidHeight = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Height(invalidHeight));
    }

    @Test
    public void isValidHeight() {
        // null height
        Assert.assertThrows(NullPointerException.class, () -> Height.isValidHeight(null));

        // invalid height
        assertFalse(Height.isValidHeight("")); // empty string
        assertFalse(Height.isValidHeight(" ")); // spaces only
        assertFalse(Height.isValidHeight("height")); // non-numeric
        assertFalse(Height.isValidHeight("9p.2")); // alphabets within digits
        assertFalse(Height.isValidHeight("9 3")); // spaces within digits
        assertFalse(Height.isValidHeight("3.14")); //  value less than 10.0
        assertFalse(Height.isValidHeight("510.0")); // value more than 400.0
        assertFalse(Height.isValidHeight("-1.0")); // negative value

        // valid height numbers
        assertTrue(Height.isValidHeight("91.1")); // exactly 3 numbers
        assertTrue(Height.isValidHeight("95"));
        assertTrue(Height.isValidHeight("205")); // tall height
        assertTrue(Height.isValidHeight("10.23")); // very short height
    }
}
```
