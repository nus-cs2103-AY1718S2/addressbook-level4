# KevinCJH
###### \java\guitests\guihandles\EmailPanelHandle.java
``` java
/**
 * A handle to the {@code EmailPanel} in the GUI.
 */
public class EmailPanelHandle extends NodeHandle<Node> {

    private static final String RECIPIENT_ID = "#toTxtField";
    private static final String BODY_ID = "#bodyTxtField";

    private final TextField to;
    private final HTMLEditor body;

    public EmailPanelHandle(Node emailPanelNode) {
        super(emailPanelNode);

        this.to = getChildNode(RECIPIENT_ID);
        this.body = getChildNode(BODY_ID);
    }

    public String getRecipient() {
        return to.getText();
    }

    public String getBody() {
        return body.getHtmlText().replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ");
    }

}
```
###### \java\guitests\guihandles\PersonCardHandle.java
``` java
    public List<String> getSkillStyleClasses(String tag) {
        return tagLabels
                .stream()
                .filter(label -> label.getText().equals(tag))
                .map(Label::getStyleClass)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such skill."));
    }
}
```
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
public class EmailCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Account());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST, VALID_EMAIL_SUBJECT);
        assertExecutionSuccess(INDEX_THIRD, VALID_EMAIL_SUBJECT);
        assertExecutionSuccess(lastPersonIndex, VALID_EMAIL_SUBJECT);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, VALID_EMAIL_SUBJECT, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST);

        assertExecutionSuccess(INDEX_FIRST, VALID_EMAIL_SUBJECT);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST);

        Index outOfBoundsIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, VALID_EMAIL_SUBJECT, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes a {@code EmailCommand} with the given {@code index} and check that email address is shown correctly.
     */
    private void assertExecutionSuccess(Index index, String emailSubject) {
        EmailCommand emailCommand = prepareCommand(index, emailSubject);

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEmail = lastShownList.get(index.getZeroBased());

        try {
            CommandResult commandResult = emailCommand.execute();
            assertEquals(String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS, personToEmail.getEmail()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToPersonListRequestEvent lastEvent =
                (JumpToPersonListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));

    }

    /**
     * Executes a {@code EmailCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String emailSubject, String expectedMessage) {
        EmailCommand emailCommand = prepareCommand(index, emailSubject);

        try {
            emailCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    @Test
    public void equals() {
        EmailCommand emailFirstCommand = new EmailCommand(INDEX_FIRST, VALID_EMAIL_SUBJECT);
        EmailCommand emailSecondCommand = new EmailCommand(INDEX_SECOND, VALID_EMAIL_SUBJECT);

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = new EmailCommand(INDEX_FIRST, VALID_EMAIL_SUBJECT);
        assertTrue(emailFirstCommand.equals(emailFirstCommandCopy));

        // different types -> returns false
        assertFalse(emailFirstCommand.equals(1));

        // null -> returns false
        assertFalse(emailFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(emailFirstCommand.equals(emailSecondCommand));
    }

    /**
     * Returns a {@code EmailCommand} with parameters {@code index}.
     */
    private EmailCommand prepareCommand(Index index, String emailSubject) {
        EmailCommand emailCommand = new EmailCommand(index, emailSubject);
        emailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return emailCommand;
    }
}
```
###### \java\seedu\address\logic\GmailClientTest.java
``` java
public class GmailClientTest {

    @Test
    public void equals() {
        GmailClient client1 = GmailClient.getInstance();
        GmailClient client2 = GmailClient.getInstance();

        assertTrue(client1.equals(client2));
    }

}
```
###### \java\seedu\address\logic\GoogleAuthenticationTest.java
``` java

public class GoogleAuthenticationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private GoogleAuthentication googleAuthentication = new GoogleAuthentication();

    /**
     * Checks if Login url generated is valid
     */
    @Test
    public void execute_authenticate_url() {
        assertTrue(googleAuthentication.getAuthenticationUrl().contains(
                "https://accounts.google.com/o/oauth2/auth?client_id"));
    }

    /**
     * Exception should be thrown as no token is generated.
     */
    @Test
    public void execute_invalidToken() throws Exception {
        thrown.expect(GoogleAuthenticationException.class);
        googleAuthentication.getToken();
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_email() throws Exception {
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new EmailCommand(INDEX_FIRST, ""), command);
    }
```
###### \java\seedu\address\logic\parser\EmailCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the EmailCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the EmailCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        assertParseSuccess(parser, "1", new EmailCommand(INDEX_FIRST, ""));
        assertParseSuccess(parser, "1 " + VALID_EMAIL_SUBJECT_DESC, new EmailCommand(INDEX_FIRST, VALID_EMAIL_SUBJECT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 " + INVALID_EMAIL_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindNameCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " n/Alice Bob", expectedFindNameCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice \n \t Bob  \t", expectedFindNameCommand);

        // no leading and trailing whitespaces
        FindCommand expectedFindTagCommand =
                new FindCommand(new PersonSkillContainsKeywordsPredicate(Arrays.asList("developer", "accountant")));
        assertParseSuccess(parser, " s/developer accountant", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n s/developer \n \t accountant  \t", expectedFindTagCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, " n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " s/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " n/ s/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " s/ n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\ui\EmailPanelTest.java
``` java
public class EmailPanelTest extends GuiUnitTest {

    private EmailPanelHandle emailPanelHandle;

    @Before
    public void setUp() {
        EmailPanel emailPanel = new EmailPanel();
        uiPartRule.setUiPart(emailPanel);

        emailPanelHandle = new EmailPanelHandle(emailPanel.getRoot());
    }

    @Test
    public void display() {

        // new result received
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        postNow(new PersonPanelSelectionChangedEvent(personCard));
        guiRobot.pauseForHuman();

        assertEquals(person.getEmail().value, emailPanelHandle.getRecipient());
        assertEquals(" Dear " + person.getName().fullName + ", ", emailPanelHandle.getBody());
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
        expectedCard.getSkills().forEach(tag ->
            assertEquals(expectedCard.getSkillStyleClasses(tag), actualCard.getSkillStyleClasses(tag)));
```
