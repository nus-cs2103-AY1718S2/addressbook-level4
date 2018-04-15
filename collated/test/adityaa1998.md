# adityaa1998
###### \java\seedu\progresschecker\logic\commands\CreateIssueCommandIntegrationTest.java
``` java
public class CreateIssueCommandIntegrationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalProgressChecker(), new UserPrefs());
        GitDetails validDetails = new GitDetailsBuilder().build();
        model.loginGithub(validDetails);
    }

    @Test
    public void execute_newIssue_success() throws Exception {

        Issue validIssue = new IssueBuilder().build();
        CommandResult commandResult = prepareCommand(validIssue, model).execute();

        /**
         * The model cannot be tested because if the model is tested,
         * There is just one model instead of two : an expected model and a model
         * The reason for the same is because if createIssue command is executed twice, there will be 2 issues online
         * Thus, the success message is comapred with the feedback to the user
         * success message is only posted after an issue is created on git
         */
        assertEquals (CreateIssueCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_authenticationError_throwsCommandException() throws Exception {
        model.logoutGithub();

        Issue validIssue = new IssueBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateIssueCommand.MESSAGE_FAILURE);

        prepareCommand(validIssue, model).execute();

    }

    /**
     * Generates a new {@code CreateIssueCommadn} which upon execution, adds {@code issue} into the {@code model}.
     */
    private CreateIssueCommand prepareCommand(Issue issue, Model model) {
        CreateIssueCommand command = new CreateIssueCommand(issue);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\progresschecker\logic\commands\CreateIssueCommandTest.java
``` java
public class CreateIssueCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullIssue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateIssueCommand(null);
    }

    @Test
    public void equals() {
        Issue one = new IssueBuilder().withTitle("one").build();
        Issue two = new IssueBuilder().withTitle("two").build();
        CreateIssueCommand createOneIssue = new CreateIssueCommand(one);
        CreateIssueCommand createTwoIssue = new CreateIssueCommand(two);

        // same object -> returns true
        assertTrue(createOneIssue.equals(createOneIssue));

        // same values -> returns true
        CreateIssueCommand createOneIssueCopy = new CreateIssueCommand(one);
        assertTrue(createOneIssue.equals(createOneIssue));

        // different types -> returns false
        assertFalse(createOneIssue.equals(1));

        // null -> returns false
        assertFalse(createOneIssue.equals(null));

        // different person -> returns false
        assertFalse(createOneIssue.equals(createTwoIssue));
    }

    @Test
    public void execute_authenticationError_throwsCommandException() throws Exception {
        ModelStub modelStub = new CreateIssueCommandTest.ModelStubCommandExceptionException();
        Issue validIssue = new IssueBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateIssueCommand.MESSAGE_FAILURE);

        getCreateIssueCommandForIssue(validIssue, modelStub).execute();
    }

    /**
     * Generates a new CreateIssueCommand with the details of the given issue.
     */
    private CreateIssueCommand getCreateIssueCommandForIssue(Issue issue, ModelStub model) {
        CreateIssueCommand command = new CreateIssueCommand(issue);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void execute_issueAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIssueAdded modelStub = new ModelStubAcceptingIssueAdded();
        Issue validIssue = new IssueBuilder().build();

        CommandResult commandResult = getCreateIssueCommandForIssue(validIssue, modelStub).execute();

        assertEquals(CreateIssueCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validIssue), modelStub.issueAdded);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void loginGithub(GitDetails gitdetails) throws IOException, CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void logoutGithub() throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void createIssueOnGitHub(Issue issue) throws IOException, CommandException {
            fail("This method should not be called. ");
        }

        @Override
        public void closeIssueOnGithub(Index index) throws IOException {
            fail("This method should not be called");
        }

        @Override
        public void listIssues(String state) throws IOException, CommandException, IllegalValueException {
            fail("This method should not be called");
        }

        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void reopenIssueOnGithub(Index index) throws IOException, CommandException {
            fail("This method should not be called");
        }

        @Override
        public void sort() {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyProgressChecker newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyProgressChecker getProgressChecker() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(Person target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void uploadPhoto(Person target, String path)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateIssue(Index index, Issue editedIssue) throws IOException {
            fail("This method should not be called");
        }

        @Override
        public void addPhoto(PhotoPath photoPath) throws DuplicatePhotoException {
            fail("This method should not be called.");
        }

        @Override
        public void updateExercise(Exercise target, Exercise editedExercise) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Exercise> getFilteredExerciseList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Issue> getFilteredIssueList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredIssueList(Predicate<Issue> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a CommandException when trying to create a new issue.
     */
    private class ModelStubCommandExceptionException extends ModelStub {
        @Override
        public void createIssueOnGitHub(Issue issue) throws IOException, CommandException {
            throw new CommandException("");
        }

    }

    /**
     * A Model stub that always accept the issue being added.
     */
    private class ModelStubAcceptingIssueAdded extends ModelStub {
        final ArrayList<Issue> issueAdded = new ArrayList<>();

        @Override
        public void createIssueOnGitHub(Issue issue) throws IOException, CommandException {
            requireNonNull(issue);
            issueAdded.add(issue);
        }

        @Override
        public ReadOnlyProgressChecker getProgressChecker() {
            return new ProgressChecker();
        }
    }

}
```
###### \java\seedu\progresschecker\logic\parser\ReopenIssueCommandParserTest.java
``` java
public class ReopenIssueCommandParserTest {

    private ReopenIssueCommandParser parser = new ReopenIssueCommandParser();

    @Test
    public void parse_validArgs_returnsReopenIssueCommand() {
        assertParseSuccess(parser, "1", new ReopenIssueCommand(INDEX_ISSUE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ReopenIssueCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\progresschecker\model\credentials\PasscodeTest.java
``` java
public class PasscodeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Passcode(null));
    }

    @Test
    public void constructor_invalidPasscode_throwsIllegalArgumentException() {
        String invalidPasscode = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Passcode(invalidPasscode));
    }

    @Test
    public void isValidPasscode() {
        // null passcode
        Assert.assertThrows(NullPointerException.class, () -> Passcode.isValidPasscode(null));

        // invalid passcode
        assertFalse(Passcode.isValidPasscode("")); // empty string
        assertFalse(Passcode.isValidPasscode(" ")); // spaces only
        assertFalse(Passcode.isValidPasscode("^")); // only non-alphanumeric characters
        assertFalse(Passcode.isValidPasscode("ads12")); // only lowercase and numbers with less than 7 characters
        assertFalse(Passcode.isValidPasscode("cajacxvccxk")); // alphabets only
        assertFalse(Passcode.isValidPasscode("12345")); // numbers only
        assertFalse(Passcode.isValidPasscode("ADDD1232")); // capital letter and numbers only
        assertFalse(Passcode.isValidPasscode("git*")); // contains characters with less than 7 characters


        // valid passcode
        assertTrue(Passcode.isValidPasscode("adityaathe2nd")); // alphanumeric characters with numerals
        assertTrue(Passcode.isValidPasscode("giTHub/repo-4")); // with capital letters
        assertTrue(Passcode.isValidPasscode("github passcode1")); // with letters and numerals

    }
}
```
###### \java\seedu\progresschecker\model\credentials\RepositoryTest.java
``` java
public class RepositoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Repository(null));
    }

    @Test
    public void constructor_invalidRepository_throwsIllegalArgumentException() {
        String invalidRepo = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Repository(invalidRepo));
    }

    @Test
    public void isValidRepository() {
        // null repo
        Assert.assertThrows(NullPointerException.class, () -> Repository.isValidRepository(null));

        // invalid repo
        assertFalse(Repository.isValidRepository("")); // empty string
        assertFalse(Repository.isValidRepository(" ")); // spaces only
        assertFalse(Repository.isValidRepository("^")); // only non-alphanumeric characters
        assertFalse(Repository.isValidRepository("ca jacxvccxk")); // alphabets only with spaces
        assertFalse(Repository.isValidRepository("adityaa the 2nd")); // alphanumeric characters with spaces


        // valid repo
        assertTrue(Repository.isValidRepository("12345")); // numbers only
        assertTrue(Repository.isValidRepository("github/repo-4")); // with capital letters
        assertTrue(Repository.isValidRepository("git*")); // contains non-alphanumeric characters

    }
}
```
###### \java\seedu\progresschecker\model\credentials\UsernameTest.java
``` java
public class UsernameTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Username(null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Username(invalidUsername));
    }

    @Test
    public void isValidUsername() {
        // null repo
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid repo
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only
        assertFalse(Username.isValidUsername("^")); // only non-alphanumeric characters
        assertFalse(Username.isValidUsername("ca jacxvccxk")); // alphabets only with spaces
        assertFalse(Username.isValidUsername("git hub1212#")); // alphanumeric characters with spaces


        // valid repo
        assertTrue(Username.isValidUsername("12345")); // numbers only
        assertTrue(Username.isValidUsername("github-repo-4")); // with capital letters
        assertTrue(Username.isValidUsername("git_hub")); // contains non-alphanumeric characters

    }
}
```
###### \java\seedu\progresschecker\model\issue\AssigneesTest.java
``` java
public class AssigneesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Assignees(null));
    }

    @Test
    public void constructor_invalidAssigneeName_throwsIllegalArgumentException() {
        String invalidAssigneeName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Assignees(invalidAssigneeName));
    }

    @Test
    public void isValidAssigneeName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Assignees.isValidAssignee(null));
    }
}
```
###### \java\seedu\progresschecker\model\issue\BodyTest.java
``` java
public class BodyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Body(null));
    }

    @Test
    public void isValidBody() {
        // null name
        assertFalse(Body.isValidBody(null));

        // valid name
        assertTrue(Body.isValidBody("peter jack")); // alphabets only
        assertTrue(Body.isValidBody("12345")); // numbers only
        assertTrue(Body.isValidBody("peter the 2nd")); // alphanumeric characters
        assertTrue(Body.isValidBody("Capital Tan")); // with capital letters
        assertTrue(Body.isValidBody("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Body.isValidBody("^")); // only non-alphanumeric characters
        assertTrue(Body.isValidBody("peter*")); // contains non-alphanumeric characters
    }
}
```
###### \java\seedu\progresschecker\model\issue\LabelsTest.java
``` java
public class LabelsTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Labels(null));
    }

    @Test
    public void constructor_invalidLabelName_throwsIllegalArgumentException() {
        String invalidLabelName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Labels(invalidLabelName));
    }

    @Test
    public void isValidLabelName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Labels.isValidLabel(null));

        // valid name
        assertTrue(Labels.isValidLabel("peter jack")); // alphabets only
        assertTrue(Labels.isValidLabel("12345")); // numbers only
        assertTrue(Labels.isValidLabel("peter the 2nd")); // alphanumeric characters
        assertTrue(Labels.isValidLabel("Capital Tan")); // with capital letters
        assertTrue(Labels.isValidLabel("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Labels.isValidLabel("peter*")); // contains non-alphanumeric characters
        assertTrue(Labels.isValidLabel("^")); // only non-alphanumeric characters
    }
}
```
###### \java\seedu\progresschecker\model\issue\MilestoneTest.java
``` java
public class MilestoneTest {

}
```
###### \java\seedu\progresschecker\model\issue\TitleTest.java
``` java
public class TitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Title(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Title.isValidTitle(null));

        // invalid name
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only

        // valid name
        assertTrue(Title.isValidTitle("peter jack")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("peter the 2nd")); // alphanumeric characters
        assertTrue(Title.isValidTitle("Capital Tan")); // with capital letters
        assertTrue(Title.isValidTitle("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Title.isValidTitle("peter*")); // contains non-alphanumeric characters
        assertTrue(Title.isValidTitle("^")); // only non-alphanumeric characters

    }
}
```
###### \java\seedu\progresschecker\testutil\GitDetailsBuilder.java
``` java
/**
 * A utility class to help with building GitDetails objects.
 */
public class GitDetailsBuilder {

    public static final String DEFAULT_REPO = "AdityaA1998/CS2103TESTING";
    public static final String DEFAULT_USERNAME = "anminkang";
    public static final String DEFAULT_PASSCDE = "aditya2018";

    private Repository repository;
    private Username username;
    private Passcode passcode;

    public GitDetailsBuilder() {
        repository = new Repository(DEFAULT_REPO);
        username = new Username(DEFAULT_USERNAME);
        passcode = new Passcode(DEFAULT_PASSCDE);
    }

    /**
     * Initializes the GitDetailsBuilder with the data of {@code detailsToCopy}.
     */
    public GitDetailsBuilder (GitDetails detailsToCopy) {
        repository = detailsToCopy.getRepository();
        passcode = detailsToCopy.getPasscode();
        username = detailsToCopy.getUsername();
    }

    /**
     * Sets the {@code Repository} of the {@code GitDetails} that we are building.
     */
    public GitDetailsBuilder withRepository(String repository) {
        this.repository = new Repository(repository);
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code GitDetails} that we are building.
     */
    public GitDetailsBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Passcode} of the {@code GitDetails} that we are building.
     */
    public GitDetailsBuilder withPasscode(String passcode) {
        this.passcode = new Passcode(passcode);
        return this;
    }

    public GitDetails build() {
        return new GitDetails(username, passcode, repository);
    }
}
```
###### \java\seedu\progresschecker\testutil\IssueBuilder.java
``` java
/**
 * A utility class to help with building Issue objects.
 */
public class IssueBuilder {

    public static final String DEFAULT_TITLE = "CS2103 software engneering";
    public static final String DEFAULT_ASSIGNEE = "anminkang";
    public static final String DEFAULT_BODY = "This an issue created for testing purposes";
    public static final String DEFAULT_MIILESTONE = "v1.1";
    public static final String DEFAULT_LABELS = "testing";

    private Title title;
    private List<Assignees> assignees;
    private Body body;
    private Milestone milestone;
    private List<Labels> labels;

    public IssueBuilder() {
        title = new Title(DEFAULT_TITLE);
        assignees = SampleDataUtil.getAssigneeList(DEFAULT_ASSIGNEE);
        body = new Body(DEFAULT_BODY);
        milestone = new Milestone(DEFAULT_MIILESTONE);
        labels = SampleDataUtil.getLabelsList(DEFAULT_LABELS);
    }

    /**
     * Initializes the IssueBuilder with the data of {@code issueToCopy}.
     */
    public IssueBuilder (Issue issueToCopy) {
        title = issueToCopy.getTitle();
        assignees = new ArrayList<>(issueToCopy.getAssignees());
        body = issueToCopy.getBody();
        milestone = issueToCopy.getMilestone();
        labels = new ArrayList<>(issueToCopy.getLabelsList());
    }

    /**
     * Sets the {@code Title} of the {@code Issue} that we are building.
     */
    public IssueBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code assignees} into a {@code List<Assignee>} and set it to the {@code Issues} that we are building.
     */
    public IssueBuilder withAssignees(String... assignees) {
        this.assignees = SampleDataUtil.getAssigneeList(assignees);
        return this;
    }

    /**
     * Sets the {@code Body} of the {@code Issue} that we are building.
     */
    public IssueBuilder withBody(String body) {
        this.body = new Body(body);
        return this;
    }

    /**
     * Sets the {@code Milestone} of the {@code Issue} that we are building.
     */
    public IssueBuilder withMilestone(String milestone) {
        this.milestone = new Milestone(milestone);
        return this;
    }

    /**
     * Parses the {@code labels} into a {@code List<Labels>} and set it to the {@code Issues} that we are building.
     */
    public IssueBuilder withLabels(String... labels) {
        this.labels = SampleDataUtil.getLabelsList(labels);
        return this;
    }

    public Issue build() {
        return new Issue(title, assignees, milestone, body, labels);
    }
}
```
###### \java\seedu\progresschecker\testutil\TypicalIssue.java
``` java
/**
 * A utility class containing a list of {@code Issue} objects to be used in tests.
 */
public class TypicalIssue {

    public static final Issue TEST_ONE = new IssueBuilder().withTitle("Test one")
            .withAssignees("anminkang").withBody("Test 1 body")
            .withMilestone("v1.1").withLabels("test1").build();
    public static final Issue TEST_TWO = new IssueBuilder().withTitle("Test two")
            .withAssignees("adityaa1998").withBody("Test 2 body")
            .withMilestone("v1.2").withLabels("test2").build();
    public static final Issue TEST_THREE = new IssueBuilder().withTitle("Test three")
            .withAssignees("kush1509").withBody("Test 3 body")
            .withMilestone("v1.3").withLabels("test3").build();
    public static final Issue TEST_FOUR = new IssueBuilder().withTitle("Test four")
            .withBody("Test 4 body")
            .withMilestone("v1.3").withLabels("test4").build();
    public static final Issue TEST_FIVE = new IssueBuilder().withTitle("Test five")
            .withAssignees("anminkang")
            .withMilestone("v1.3").withLabels("test5").build();
    public static final Issue TEST_SIX = new IssueBuilder().withTitle("Test six")
            .withAssignees("anminkang").withBody("Test 6 body")
            .withLabels("test6").build();
    public static final Issue TEST_SEVEN = new IssueBuilder().withTitle("Test seven")
            .withAssignees("anminkang").withBody("Test 7 body")
            .withMilestone("v1.3").build();

    //Manually added - Issue's details found in {@code CommandTestUtil}
    public static final Issue ISSUE_ONE = new IssueBuilder().withTitle(VALID_TITLE_ONE)
            .withAssignees(VALID_ASSIGNEE_ANMIN)
            .withBody(VALID_BODY_ONE).withMilestone(VALID_MILESTONE_ONE)
            .withLabels(VALID_LABEL_TASK).build();
    public static final Issue ISSUE_TWO = new IssueBuilder().withTitle(VALID_TITLE_TWO)
            .withAssignees(VALID_ASSIGNEE_BOB)
            .withBody(VALID_BODY_TWO).withMilestone(VALID_MILESTONE_TWO)
            .withLabels(VALID_LABEL_STORY).build();
}
```
###### \java\seedu\progresschecker\ui\CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_tab() {
        // add command
        commandBoxHandle.setInput(COMMAND_ADD_INCOMPLETE);
        assertInputHistory(KeyCode.TAB, COMMAND_ADD_COMPLETE);

        // edit command
        commandBoxHandle.setInput(COMMAND_EDIT_INCOMPLETE);
        assertInputHistory(KeyCode.TAB, COMMAND_EDIT_COMPLETE);

        // invalid command
        commandBoxHandle.setInput(COMMAND_THAT_FAILS);
        assertInputHistory(KeyCode.TAB, COMMAND_THAT_FAILS);
    }
```
###### \java\seedu\progresschecker\ui\testutil\EventsCollectorRule.java
``` java
        /**
         * Returns the second last event collected
         */
        public BaseEvent getSecondLast() {
            if (events.isEmpty()) {
                return null;
            }

            return events.get(events.size() - 2);
        }
```
###### \java\systemtests\CreateIssueCommandSystemTest.java
``` java
public class CreateIssueCommandSystemTest extends ProgressCheckerSystemTest {
    private final String gitlogin = "gl r/AdityaA1998/CS2103TESTING gu/anminkang pc/aditya2018";
    private final String gitlogout = "gitlogout";

    @Before
    public void setUpCreateIssue() throws Exception {

        Model model = getModel();
        GitDetails validDetails = new GitDetailsBuilder().build();
        model.loginGithub(validDetails);
    }

    @Test
    public void add() throws Exception {

        /* ------------------------------- Perform create issue operations ----------------------------------- */

        Issue toCreate = ISSUE_ONE;

        String command = "   " + CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandSuccess(command, toCreate);

        /* Case: create a issue, missing assignee -> created */
        assertCommandSuccess(TEST_FOUR);

        /* Case: create a issue, missing body -> created */
        assertCommandSuccess(TEST_FIVE);

        /* Case: create a issue, missing milestone -> created */
        assertCommandSuccess(TEST_SIX);

        /* Case: create a issue, missing labels -> created */
        assertCommandSuccess(TEST_SEVEN);

        /* ------------------------------- Perform invalid create issue operations ------------------------------- */

        /* Case: Github not authenticated -> rejected */
        command = CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandFailureWithoutAuthentication(command, CreateIssueCommand.MESSAGE_FAILURE);

        /* Case: missing title -> rejected */
        command = CreateIssueCommand.COMMAND_WORD + "  "
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateIssueCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "+issues " + IssueUtil.getCreateIssueCommand(toCreate);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid title -> rejected */
        command = "   " + CreateIssueCommand.COMMAND_WORD + "  " + INVALID_TITLE_DESC
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandFailure(command, Title.MESSAGE_TITLE_CONSTRAINTS);

        /* Case: invalid assignee -> rejected */
        command = "   " + CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
                + "  " + INVALID_ASSIGNEE_DESC + " "
                + MILESTONE_DESC_ONE + "   "
                + BODY_DESC_ONE + "   "
                + LABEL_DEC_TASK + "   ";
        assertCommandFailure(command, Assignees.MESSAGE_ASSIGNEES_CONSTRAINTS);

        /* Case: invalid milestone -> rejected */
        //command = CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
        //+ "  " + ASSIGNEE_DESC_ANMIN + " "
        //+ INVALID_MILESTONE_DESC + "   "
        //+ BODY_DESC_ONE + "   "
        //+ LABEL_DEC_TASK + "   ";

        /* Case: invalid labels -> rejected */
        command = CreateIssueCommand.COMMAND_WORD + "  " + TITLE_DESC_ONE
                + "  " + ASSIGNEE_DESC_ANMIN + " "
                + MILESTONE_DESC_ONE + "   "
                + INVALID_BODY_DESC + "   "
                + INVALID_LABEL_DESC + "   ";
        assertCommandFailure(command, Labels.MESSAGE_LABEL_CONSTRAINTS);

    }

    /**
     * Executes the {@code CreateIssueCommand} that adds {@code toCreate} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code CreateIssueCommand} with the details of
     * {@code toCreate}.<br>
     * 4. {@code Model}, {@code Storage} and {@code issueListPanel} equal to the corresponding components in
     * the current model added with {@code toCreate}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(Issue toCreate) throws Exception {
        assertCommandSuccess(IssueUtil.getCreateIssueCommand(toCreate), toCreate);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(issue)}. Executes {@code command}
     * instead.
     */
    private void assertCommandSuccess(String command, Issue toCreate) throws Exception {
        Model expectedModel = getModel();
        GitDetails validDetails = new GitDetailsBuilder().build();
        expectedModel.loginGithub(validDetails);
        try {
            expectedModel.createIssueOnGitHub(toCreate);
        } catch (IOException | CommandException e) {
            throw new IllegalArgumentException("Check authentication or parameters");
        }
        String expectedResultMessage = CreateIssueCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Issue)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code issueListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(gitlogin);
        executeCommand(command);
        assertApplicationDisplaysExpectedForIssue("", expectedResultMessage, expectedModel);
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
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(gitlogin);
        executeCommand(command);
        assertApplicationDisplaysExpectedForIssue(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see ProgressCheckerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailureWithoutAuthentication(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(gitlogout);
        executeCommand(command);
        assertApplicationDisplaysExpectedForIssue(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\ProgressCheckerSystemTest.java
``` java
    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the model and storage contains the same person objects as {@code expectedModel}
     * and the person list panel displays the persons in the model correctly.
     */
    protected void assertApplicationDisplaysExpectedForIssue(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
    }
```
