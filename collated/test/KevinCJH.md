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
###### \java\seedu\address\logic\commands\EmailCommandTest.java
``` java
public class EmailCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes a {@code EmailCommand} with the given {@code index} and check that email address is shown correctly.
     */
    private void assertExecutionSuccess(Index index) {
        EmailCommand emailCommand = prepareCommand(index);

        List<Person> lastShownList = model.getFilteredPersonList();
        Person personToEmail = lastShownList.get(index.getZeroBased());

        try {
            CommandResult commandResult = emailCommand.execute();
            assertEquals(String.format(EmailCommand.MESSAGE_EMAIL_PERSON_SUCCESS, personToEmail.getEmail()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));

    }

    /**
     * Executes a {@code EmailCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        EmailCommand emailCommand = prepareCommand(index);

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
        EmailCommand emailFirstCommand = new EmailCommand(INDEX_FIRST_PERSON);
        EmailCommand emailSecondCommand = new EmailCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(emailFirstCommand.equals(emailFirstCommand));

        // same values -> returns true
        EmailCommand emailFirstCommandCopy = new EmailCommand(INDEX_FIRST_PERSON);
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
    private EmailCommand prepareCommand(Index index) {
        EmailCommand emailCommand = new EmailCommand(index);
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
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_email() throws Exception {
        EmailCommand command = (EmailCommand) parser.parseCommand(
                EmailCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new EmailCommand(INDEX_FIRST_PERSON), command);
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
        assertParseSuccess(parser, "1", new EmailCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
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
                new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList("developer", "accountant")));
        assertParseSuccess(parser, " t/developer accountant", expectedFindTagCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n t/developer \n \t accountant  \t", expectedFindTagCommand);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, " n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " n/ t/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        assertParseFailure(parser, " t/ n/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\model\tag\TagContainsKeywordsPredicateTest.java
``` java
public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("developer"));
        assertTrue(predicate.test(new PersonBuilder().withTags("developer", "geek").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("developer", "geek"));
        assertTrue(predicate.test(new PersonBuilder().withTags("developer", "geek").build()));

        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("developer", "accountant"));
        assertTrue(predicate.test(new PersonBuilder().withTags("accountant", "manager").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("deVeloper", "acCounTant"));
        assertTrue(predicate.test(new PersonBuilder().withTags("developer", "accountant").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("developer").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("projectmanager"));
        assertFalse(predicate.test(new PersonBuilder().withTags("developer", "designer").build()));

        // Keywords match name, phone, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(
                Arrays.asList("Alice", "12345", "alice@company.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withTags("developer").withPhone("12345")
                .withEmail("alice@company.com").withAddress("Main Street").withName("Alice").build()));
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
        expectedCard.getTags().forEach(tag ->
            assertEquals(expectedCard.getTagStyleClasses(tag), actualCard.getTagStyleClasses(tag)));
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
        assertTagsEquals(expectedPerson, actualCard);
    }

    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code PersonCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     *
     * @see PersonCard#getTagColorStyleFor(String)
     */
    private static String getTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "classmates":
            return "brown";

        case "owesMoney":
            return "green";

        case "colleagues":
            return "purple";

        case "neighbours":
            return "yellow";

        case "family":
            return "lightgreen";

        case "friend":
            return "grey";

        case "friends":
            return "pink";

        case "husband":
            return "red";

        default:
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedPerson} with the correct
     * color.
     */
    private static void assertTagsEquals(Person expectedPerson, PersonCardHandle actualCard) {
        List<String> expectedTags = expectedPerson.getTags().stream()
                .map(tag -> tag.tagName).collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag -> assertEquals(
                Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)), actualCard.getTagStyleClasses(tag)));
    }
```
