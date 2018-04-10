# ng95junwei
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void deleteTemplate(String purpose) {
            fail("This method should not be called.");
        }
        @Override
        public ObservableList<Template> getFilteredTemplateList() {
            fail("This method should not be called.");
            return null;
        }
        @Override
        public void updateFilteredTemplateList(Predicate<Template> predicate) {
            fail("This method should not be called.");
        }
        @Override
        public ObservableList<Template> getAllTemplates() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Template selectTemplate(String search) {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void addTemplate(Template template) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddTemplateCommandTest.java
``` java
public class AddTemplateCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddTemplateCommand(null);
    }

    @Test
    public void execute_successful_addTemplate() throws Exception {
        Model modelStub = new ModelManager();
        Template validTemplate = new TemplateBuilder().build();
        int countBefore = modelStub.getFilteredTemplateList().size();
        CommandResult result = generateAddTemplateCommand(validTemplate, modelStub).execute();
        assertEquals(
                String.format(AddTemplateCommand.MESSAGE_SUCCESS, validTemplate), result.feedbackToUser
        );
        assertEquals(countBefore + 1, modelStub.getFilteredTemplateList().size());
    }

    @Test
    public void execute_duplicateTemplate_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager();
        Template validTemplate = new TemplateBuilder().build();

        thrown.expect(CommandException.class);

        generateAddTemplateCommand(validTemplate, modelStub).execute();
        generateAddTemplateCommand(validTemplate, modelStub).execute();
    }

    private AddTemplateCommand generateAddTemplateCommand(Template template, Model model) {
        AddTemplateCommand command = new AddTemplateCommand(template);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteTemplateCommandTest.java
``` java

public class DeleteTemplateCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Template toAdd = new TemplateBuilder().build();

    @Test
    public void execute_existingTemplate_success() throws Exception {
        model.addTemplate(toAdd);
        String userInput = toAdd.getPurpose();
        String expectedMessage = String.format(DeleteTemplateCommand.MESSAGE_DELETE_TEMPLATE_SUCCESS,
                toAdd.getPurpose());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteTemplateCommand command = generateDeleteCommand(userInput, model);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_nonexistingTemplate_failure() throws Exception {
        String userInput = "Test";
        String expectedMessage = Messages.MESSAGE_TEMPLATE_NOT_FOUND;

        DeleteTemplateCommand command = generateDeleteCommand(userInput, model);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteTemplateCommand command1 = new DeleteTemplateCommand("test");
        DeleteTemplateCommand command2 = new DeleteTemplateCommand("test");
        DeleteTemplateCommand command3 = new DeleteTemplateCommand("different");

        //same object - true
        assertTrue(command1.equals(command1));

        //same value - true
        assertTrue(command1.equals(command2));

        //different type - false
        assertFalse(command1.equals(1));

        //different values - false
        assertFalse(command1.equals(command3));

        //null - false
        assertFalse(command1.equals(null));
    }

    /**
     * helper function to return a command with data already set
     * @param userInput
     * @param model
     * @return
     */
    public DeleteTemplateCommand generateDeleteCommand(String userInput, Model model) {
        DeleteTemplateCommand command = new DeleteTemplateCommand(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        return command;
    }

}
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java

public class EmailCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NameContainsKeywordsPredicate firstPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("first"));
        NameContainsKeywordsPredicate secondPredicate =
                new NameContainsKeywordsPredicate(Collections.singletonList("second"));

        EmailCommand emailFirstCommand = new EmailCommand(firstPredicate, "test");
        EmailCommand emailSecondCommand = new EmailCommand(secondPredicate, "test");

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = new EmailCommand(firstPredicate, "test");
        assertTrue(emailFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_EMAIL_SENT, 0);
        EmailCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private EmailCommand prepareCommand(String userInput) {
        EmailCommand command =
                new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))),
                        "test");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<Person>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(EmailCommand command, String expectedMessage, List<Person> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_addTemplate() throws Exception {
        List<String> userInput = Arrays.asList(AddTemplateCommand.COMMAND_WORD,
                PURPOSE_DESC, MESSAGE_DESC, SUBJECT_DESC);
        Template templateToAdd = new TemplateBuilder().withPurpose(VALID_PURPOSE).withSubject(VALID_SUBJECT)
                .withMessage(VALID_MESSAGE).build();
        AddTemplateCommand command = new AddTemplateCommand(templateToAdd);
        AddTemplateCommand parsedCommand = (AddTemplateCommand) parser.parseCommand(userInput.stream()
                .collect(Collectors.joining(" ")));
        assertEquals(command, parsedCommand);
    }

    @Test
    public void parseCommand_addTemplateAlias() throws Exception {
        List<String> userInput = Arrays.asList(AddTemplateCommand.COMMAND_ALIAS,
                PURPOSE_DESC, MESSAGE_DESC, SUBJECT_DESC);
        Template templateToAdd = new TemplateBuilder().withPurpose(VALID_PURPOSE).withSubject(VALID_SUBJECT)
                .withMessage(VALID_MESSAGE).build();
        AddTemplateCommand command = new AddTemplateCommand(templateToAdd);
        AddTemplateCommand parsedCommand = (AddTemplateCommand) parser.parseCommand(userInput.stream()
                .collect(Collectors.joining(" ")));
        assertEquals(command, parsedCommand);
    }

    @Test
    public void parseCommand_deleteTemplate() throws Exception {
        DeleteTemplateCommand command = new DeleteTemplateCommand("test");
        DeleteTemplateCommand parsedCommand =
                (DeleteTemplateCommand) parser.parseCommand("deletetemplate test");
        assertEquals(command, parsedCommand);
    }

    @Test
    public void parseCommand_deleteTemplateAlias() throws Exception {
        DeleteTemplateCommand command = new DeleteTemplateCommand("test");
        DeleteTemplateCommand parsedCommand =
                (DeleteTemplateCommand) parser.parseCommand("dt test");
        assertEquals(command, parsedCommand);
    }

    @Test
    public void parseCommand_email() throws Exception {
        List<String> keywords = Arrays.asList("foo", "test");
        String[] nameKeywordArray = new String[]{ "foo" };
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywordArray)),
                "test"), command);
    }

    @Test
    public void parseCommand_emailAlias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "test");
        String[] nameKeywordArray = new String[]{ "foo" };
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new EmailCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywordArray)),
                "test"), command);
    }

```
###### \java\seedu\address\logic\parser\AddTemplateCommandParserTest.java
``` java

public class AddTemplateCommandParserTest {
    private AddTemplateCommandParser parser = new AddTemplateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Template expectedTemplate = new TemplateBuilder()
                .withPurpose(VALID_PURPOSE)
                .withSubject(VALID_SUBJECT)
                .withMessage(VALID_MESSAGE)
                .build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PURPOSE_DESC + SUBJECT_DESC + MESSAGE_DESC,
                new AddTemplateCommand(expectedTemplate));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTemplateCommand.MESSAGE_USAGE);

        // missing purpose prefix
        assertParseFailure(parser, SUBJECT_DESC + MESSAGE_DESC,
                expectedMessage);

        // missing subject prefix
        assertParseFailure(parser, PURPOSE_DESC + MESSAGE_DESC,
                expectedMessage);

        // missing message prefix
        assertParseFailure(parser, PURPOSE_DESC + SUBJECT_DESC,
                expectedMessage);
    }
}

```
###### \java\seedu\address\logic\parser\DeleteTemplateCommandParserTest.java
``` java

public class DeleteTemplateCommandParserTest {
    private DeleteTemplateCommandParser parser = new DeleteTemplateCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String purpose = "test";
        assertParseSuccess(parser,  PREAMBLE_WHITESPACE + purpose,
                new DeleteTemplateCommand(purpose));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTemplateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_tooManyFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTemplateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "this and that", expectedMessage);
    }
}
```
###### \java\seedu\address\model\email\TemplateTest.java
``` java

public class TemplateTest {
    private final String purpose = "purpose";
    private final String title = "title";
    private final String message = "message";
    private final Template template = new Template(purpose, title, message);

    @Test
    public void isEqual_sameTemplate_success() {
        assertTrue(new Template(purpose, title, message).equals(template));
    }

    @Test
    public void isEqual_compareNull_failure() {
        assertFalse(new Template(purpose, title, message).equals(null));
    }

    @Test
    public void getters_validAppointment_success() {
        assertTrue(template.getPurpose().equals(purpose));
        assertTrue(template.getTitle().equals(title));
        assertTrue(template.getMessage().equals(message));
    }
    @Test
    public void toString_validTemplate_success() {
        assertTrue(template.toString().equals("Purpose: " + purpose + ", Subject: " + title + ", Message: "
                + message));
    }

}

```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getTemplateList_return_observableList() {
        ModelManager modelManager = new ModelManager();
        assertThat(modelManager.getAllTemplates(), instanceOf(ObservableList.class));
    }
```
###### \java\seedu\address\model\UniqueTemplateListTest.java
``` java

public class UniqueTemplateListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTemplateList uniqueTemplateList = new UniqueTemplateList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTemplateList.asObservableList().remove(0);
    }

    @Test
    public void addDuplicate_throwsDuplicateTemplateException() throws DuplicateTemplateException {
        Template template = new Template("test", "test", "test");
        UniqueTemplateList uniqueTemplateList = new UniqueTemplateList();
        thrown.expect(DuplicateTemplateException.class);
        uniqueTemplateList.add(template);
        uniqueTemplateList.add(template);
    }

    @Test
    public void asObservableList_modifyList_throwsTemplateNotFoundException() throws TemplateNotFoundException {
        UniqueTemplateList uniqueTemplateList = new UniqueTemplateList();
        thrown.expect(TemplateNotFoundException.class);
        uniqueTemplateList.remove("test");
    }

}

```
###### \java\seedu\address\testutil\TemplateBuilder.java
``` java

/**
 * Utility class to help with building default templates for testing
 */
public class TemplateBuilder {
    public static final String DEFAULT_PURPOSE = "Purpose";
    public static final String DEFAULT_SUBJECT = "Default Subject";
    public static final String DEFAULT_MESSAGE = "Default Message";

    private String purpose;
    private String subject;
    private String message;

    /**
     * Constructor with default purpose, subject message
     */
    public TemplateBuilder() {
        this.purpose = DEFAULT_PURPOSE;
        this.subject = DEFAULT_SUBJECT;
        this.message = DEFAULT_MESSAGE;
    }

    /**
     * mutates the purpose of the builder
     * @param purpose
     * @return original object to be chained
     */
    public TemplateBuilder withPurpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    /**
     * mutates the subject of the builder
     * @param subject
     * @return original object to be chained
     */
    public TemplateBuilder withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * mutates the message of the builder
     * @param message
     * @return original object to be chained
     */
    public TemplateBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * builds template from builder
     * @return template with attributes of builder
     */
    public Template build() {
        return new Template(purpose, subject, message);
    }
}
```
