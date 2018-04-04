# dominickenn
###### /java/seedu/organizer/logic/commands/AddQuestionAnswerCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for AddQuestionAnswerCommand.
 */
public class AddQuestionAnswerCommandTest {

    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    private String username = "admin";
    private String password = "admin";
    private String question = "are cats cool?";
    private String answer = "of course!";

    @Before
    public void setUp() {
        try {
            model.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_success() throws Exception {
        UserWithQuestionAnswer editedUser = new UserWithQuestionAnswer(username, password, question, answer);
        AddQuestionAnswerCommand command = prepareCommand(question, answer);

        String expectedMessage = String.format(
                AddQuestionAnswerCommand.MESSAGE_ADD_QUESTION_ANSWER_SUCCESS, editedUser);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.loginUser(ADMIN_USER);
        expectedModel.addQuestionAnswerToUser(ADMIN_USER, editedUser);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final AddQuestionAnswerCommand standardCommand = prepareCommand(question, answer);

        // same values -> returns true
        AddQuestionAnswerCommand commandWithSameValues = prepareCommand(question, answer);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different question -> returns false
        assertFalse(standardCommand.equals(new AddQuestionAnswerCommand("different", answer)));

        // different answer -> returns false
        assertFalse(standardCommand.equals(new AddQuestionAnswerCommand(question, "different")));
    }

    /**
     * Returns an {@code AddQuestionAnswerCommand} with parameters {@code question} and {@code answer}
     */
    private AddQuestionAnswerCommand prepareCommand(String question, String answer) {
        AddQuestionAnswerCommand command = new AddQuestionAnswerCommand(question, answer);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/organizer/logic/commands/AnswerCommandTest.java
``` java
/**
 * Contains unit tests for AnswerCommand.
 */
public class AnswerCommandTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Model model;
    private AnswerCommand answerCommand;

    @Before
    public void setUp() {
        model = new ModelManager();
        try {
            model.addUser(ADMIN_USER);
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }
        answerCommand = new AnswerCommand("admin", "answer");
    }

    @Test
    public void execute_existingUser_noQuestion() throws Exception {
        assertCommandSuccess(answerCommand,
                String.format(AnswerCommand.MESSAGE_NO_QUESTION, "admin"));
    }

    @Test
    public void execute_existingUser_answer() throws Exception {
        UserWithQuestionAnswer editedUser = new UserWithQuestionAnswer(
                "admin",
                "admin",
                "question?",
                "answer");
        model.addQuestionAnswerToUser(ADMIN_USER, editedUser);
        assertCommandSuccess(answerCommand,
                String.format(AnswerCommand.MESSAGE_SUCCESS, "admin"));
    }

    @Test
    public void execute_nonExistingUser_noSuchUserFound() {
        answerCommand = new AnswerCommand("noSuchUser", "answer");
        assertCommandFailure(answerCommand);
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     *
     * @throws CommandException If an error occurs during command execution.
     */
    protected void assertCommandSuccess(AnswerCommand command, String expectedMessage)
            throws CommandException {
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - Exception is thrown
     */
    protected void assertCommandFailure(AnswerCommand command) {
        answerCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        exception.expect(AssertionError.class);
        command.execute();
    }
}

```
###### /java/seedu/organizer/logic/commands/ForgotPasswordCommandTest.java
``` java

import static junit.framework.TestCase.assertEquals;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.user.UserWithQuestionAnswer;
import seedu.organizer.model.user.exceptions.DuplicateUserException;

/**
 * Contains unit tests for ForgotPasswordCommand.
 */
public class ForgotPasswordCommandTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    private Model model;
    private ForgotPasswordCommand forgotPasswordCommand;

    @Before
    public void setUp() {
        model = new ModelManager();
        try {
            model.addUser(ADMIN_USER);
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }
        forgotPasswordCommand = new ForgotPasswordCommand("admin");
    }

    @Test
    public void execute_existingUser_noQuestion() throws Exception {
        assertCommandSuccess(forgotPasswordCommand,
                String.format(ForgotPasswordCommand.MESSAGE_NO_QUESTION, "admin"));
    }

    @Test
    public void execute_existingUser_question() throws Exception {
        UserWithQuestionAnswer editedUser = new UserWithQuestionAnswer(
                "admin",
                "admin",
                "question?",
                "answer");
        model.addQuestionAnswerToUser(ADMIN_USER, editedUser);
        assertCommandSuccess(forgotPasswordCommand,
                String.format(ForgotPasswordCommand.MESSAGE_SUCCESS, "question?"));
    }

    @Test
    public void execute_nonexistingUser_noSuchUserFound() {
        forgotPasswordCommand = new ForgotPasswordCommand("noSuchUser");
        assertCommandFailure(forgotPasswordCommand);
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     *
     * @throws CommandException If an error occurs during command execution.
     */
    protected void assertCommandSuccess(ForgotPasswordCommand command, String expectedMessage)
            throws CommandException {
        forgotPasswordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - Exception is thrown
     */
    protected void assertCommandFailure(ForgotPasswordCommand command) {
        forgotPasswordCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        exception.expect(AssertionError.class);
        command.execute();
    }
}

```
###### /java/seedu/organizer/logic/commands/LoginCommandTest.java
``` java
public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null);
    }

    @Test
    public void execute_userAcceptedByModel_addSuccessful() throws Exception {
        ModelStubLoginAccepted modelStub = new ModelStubLoginAccepted();
        User validUser = new User("david", "david123");

        CommandResult commandResult = getLoginCommandForUser(validUser, modelStub).execute();

        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);
    }

    @Test
    public void execute_userNotFound_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingUserNotFoundException();
        User validUser = new User("admin", "admin");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_USER_NOT_FOUND);

        getLoginCommandForUser(validUser, modelStub).execute();
    }

    @Test
    public void equals() {
        User alice = new User("alice", "alice123");
        User bob = new User("bob", "bob123");
        LoginCommand loginAliceCommand = new LoginCommand(alice);
        LoginCommand loginBobCommand = new LoginCommand(bob);

        // same object -> returns true
        assertTrue(loginAliceCommand.equals(loginAliceCommand));

        // same values -> returns true
        LoginCommand loginAliceCommandCopy = new LoginCommand(alice);
        assertTrue(loginAliceCommand.equals(loginAliceCommandCopy));

        // different types -> returns false
        assertFalse(loginAliceCommand.equals(1));

        // null -> returns false
        assertFalse(loginAliceCommand.equals(null));

        // different task -> returns false
        assertFalse(loginAliceCommand.equals(loginBobCommand));
    }

    /**
     * Generates a new LoginCommand with the given user.
     */
    private LoginCommand getLoginCommandForUser(User user, Model model) {
        LoginCommand command = new LoginCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addUser(User user) throws DuplicateUserException {
            fail("This method should not be called");
        }

        @Override
        public void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
            fail("This method should not be called");
        }

        @Override
        public void logout() {
            fail("This method should not be called");
        }

        @Override
        public void deleteCurrentUserTasks() {
            fail("This method should not be called");
        }

        @Override
        public void addQuestionAnswerToUser(User toRemove, UserWithQuestionAnswer toAdd) {
            fail("This method should not be called");
        }

        @Override
        public User getUserByUsername(String username) throws UserNotFoundException {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void addTask(Task task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyOrganizer newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTask(Task target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(Task target, Task editedTask)
                throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a UserNotFoundException when trying to login.
     */
    private class ModelStubThrowingUserNotFoundException extends ModelStub {
        @Override
        public void loginUser(User user) throws UserNotFoundException {
            throw new UserNotFoundException();
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

    /**
     * A Model stub that always accept login request.
     */
    private class ModelStubLoginAccepted extends ModelStub {
        final ArrayList<User> users = new ArrayList<>();

        @Override
        public void loginUser(User user) throws UserNotFoundException {
            requireNonNull(user);
            users.add(user);
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }
}
```
###### /java/seedu/organizer/logic/commands/LogoutCommandTest.java
``` java
public class LogoutCommandTest {

    private Model model = new ModelManager();
    private User user = new User("admin", "admin");

    @Before
    public void setUp() throws Exception {
        model.addUser(user);
    }

    @Test
    public void execute() throws Exception {
        //Current user should be null
        assertEquals(getCurrentlyLoggedInUser(), null);

        //Current user should be admin
        model.loginUser(user);
        assertEquals(getCurrentlyLoggedInUser(), user);

        //Current user should be null;
        model.logout();
        assertEquals(getCurrentlyLoggedInUser(), null);
    }
}
```
###### /java/seedu/organizer/logic/commands/SignUpCommandIntegrationTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code SignUpCommand}.
 */
public class SignUpCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    }

    @Test
    public void execute_newUser_success() throws Exception {
        User validUser = new User("david", "david123");

        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.addUser(validUser);

        assertCommandSuccess(prepareCommand(validUser, model), model,
                String.format(SignUpCommand.MESSAGE_SUCCESS, validUser), expectedModel);
    }

    @Test
    public void execute_duplicateUser_throwsCommandException() {
        User userInList = model.getOrganizer().getUserList().get(0);
        assertCommandFailure(prepareCommand(userInList, model), model, SignUpCommand.MESSAGE_DUPLICATE_USER);
    }

    /**
     * Generates a new {@code SignUpCommand} which upon execution, adds {@code user} into the {@code model}.
     */
    private SignUpCommand prepareCommand(User user, Model model) {
        SignUpCommand command = new SignUpCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}

```
###### /java/seedu/organizer/logic/commands/SignUpCommandTest.java
``` java
public class SignUpCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SignUpCommand(null);
    }

    @Test
    public void execute_userAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();
        User validUser = new User("david", "david123");

        CommandResult commandResult = getSignUpCommandForUser(validUser, modelStub).execute();

        assertEquals(String.format(SignUpCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validUser), modelStub.usersAdded);
    }

    @Test
    public void execute_duplicateUser_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateUserException();
        User validUser = new User("admin", "admin");

        thrown.expect(CommandException.class);
        thrown.expectMessage(SignUpCommand.MESSAGE_DUPLICATE_USER);

        getSignUpCommandForUser(validUser, modelStub).execute();
    }

    @Test
    public void equals() {
        User alice = new User("alice", "alice123");
        User bob = new User("bob", "bob123");
        SignUpCommand signUpAliceCommand = new SignUpCommand(alice);
        SignUpCommand signUpBobCommand = new SignUpCommand(bob);

        // same object -> returns true
        assertTrue(signUpAliceCommand.equals(signUpAliceCommand));

        // same values -> returns true
        SignUpCommand addAliceCommandCopy = new SignUpCommand(alice);
        assertTrue(signUpAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(signUpAliceCommand.equals(1));

        // null -> returns false
        assertFalse(signUpAliceCommand.equals(null));

        // different task -> returns false
        assertFalse(signUpAliceCommand.equals(signUpBobCommand));
    }

    /**
     * Generates a new SignUpCommand with the given user.
     */
    private SignUpCommand getSignUpCommandForUser(User user, Model model) {
        SignUpCommand command = new SignUpCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addUser(User user) throws DuplicateUserException {
            fail("This method should not be called");
        }

        @Override
        public void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
            fail("This method should not be called");
        }

        @Override
        public void logout() {
            fail("This method should not be called");
        }

        @Override
        public void deleteCurrentUserTasks() {
            fail("This method should not be called");
        }

        @Override
        public void addQuestionAnswerToUser(User toRemove, UserWithQuestionAnswer toAdd) {
            fail("This method should not be called");
        }

        @Override
        public User getUserByUsername(String username) throws UserNotFoundException {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void addTask(Task task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyOrganizer newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteTask(Task target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(Task target, Task editedTask)
                throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateUserException when trying to add a user.
     */
    private class ModelStubThrowingDuplicateUserException extends ModelStub {
        @Override
        public void addUser(User user) throws DuplicateUserException {
            throw new DuplicateUserException();
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

    /**
     * A Model stub that always accept the user being added.
     */
    private class ModelStubAcceptingUserAdded extends ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void addUser(User user) throws DuplicateUserException {
            requireNonNull(user);
            usersAdded.add(user);
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

}
```
###### /java/seedu/organizer/logic/parser/AddCommandParserTest.java
``` java
        // no priority
        Task expectedNoPriorityTask = new TaskBuilder().withName(VALID_NAME_EXAM)
                .withPriority(Priority.LOWEST_PRIORITY_LEVEL).withDeadline(VALID_DEADLINE_EXAM)
                .withDescription(VALID_DESCRIPTION_EXAM).withTags(VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, NAME_DESC_EXAM + DEADLINE_DESC_EXAM + DESCRIPTION_DESC_EXAM + TAG_DESC_HUSBAND,
                new AddCommand(expectedNoPriorityTask));
```
###### /java/seedu/organizer/logic/parser/AnswerCommandParserTest.java
``` java
public class AnswerCommandParserTest {
    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedUsername = "admin";
        String expectedAnswer = "answer";
        assertParseSuccess(parser, " u/admin a/answer", new AnswerCommand(expectedUsername, expectedAnswer));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, " admin a/answer", expectedMessage);

        // missing answer prefix
        assertParseFailure(parser, " u/admin answer", expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, " admin answer", expectedMessage);
    }
}
```
###### /java/seedu/organizer/logic/parser/ForgotPasswordCommandParserTest.java
``` java
public class ForgotPasswordCommandParserTest {
    private ForgotPasswordCommandParser parser = new ForgotPasswordCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String expectedUsername = "admin";

        assertParseSuccess(parser, " u/admin", new ForgotPasswordCommand(expectedUsername));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ForgotPasswordCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, " admin", expectedMessage);
    }
}
```
###### /java/seedu/organizer/logic/parser/LoginCommandParserTest.java
``` java
public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new User("bob", "bob");

        assertParseSuccess(parser, " u/bob p/bob", new LoginCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, " bob p/b0b", expectedMessage);

        // missing password prefix
        assertParseFailure(parser, " u/bob b0b", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " bob b0b", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, " u/b@b p/bob", User.MESSAGE_USER_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, " u/bob p/b@b", User.MESSAGE_USER_CONSTRAINTS);
    }
}
```
###### /java/seedu/organizer/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseUsermame_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUsername((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUsername((Optional<String>) null));
    }

    @Test
    public void parseUsername_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUsername(INVALID_USERNAME));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parseUsername(Optional.of(INVALID_USERNAME)));
    }

    @Test
    public void parseUsername_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseUsername(Optional.empty()).isPresent());
    }

    @Test
    public void parseUsername_validValueWithoutWhitespace_returnsUsername() throws Exception {
        String expectedUsername = VALID_USERNAME;
        assertEquals(expectedUsername, ParserUtil.parseUsername(VALID_USERNAME));
        assertEquals(Optional.of(expectedUsername), ParserUtil.parseUsername(Optional.of(VALID_USERNAME)));
    }

    @Test
    public void parsePassword_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePassword((String) null));
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePassword((Optional<String>) null));
    }

    @Test
    public void parsePassword_invalidValue_throwsIllegalValueException() {
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePassword(INVALID_PASSWORD));
        Assert.assertThrows(IllegalValueException.class, () -> ParserUtil.parsePassword(Optional.of(INVALID_PASSWORD)));
    }

    @Test
    public void parsePassword_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePassword(Optional.empty()).isPresent());
    }

    @Test
    public void parsePassword_validValueWithoutWhitespace_returnsPassword() throws Exception {
        String expectedPassword = VALID_PASSWORD;
        assertEquals(expectedPassword, ParserUtil.parsePassword(VALID_PASSWORD));
        assertEquals(Optional.of(expectedPassword), ParserUtil.parsePassword(Optional.of(VALID_PASSWORD)));
    }
```
###### /java/seedu/organizer/logic/parser/SignUpCommandParserTest.java
``` java
public class SignUpCommandParserTest {
    private SignUpCommandParser parser = new SignUpCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new User("bob", "bob");

        assertParseSuccess(parser, " u/bob p/bob", new SignUpCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, " bob p/b0b", expectedMessage);

        // missing password prefix
        assertParseFailure(parser, " u/bob b0b", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " bob b0b", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, " u/b@b p/bob", User.MESSAGE_USER_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, " u/bob p/b@b", User.MESSAGE_USER_CONSTRAINTS);
    }
}
```
###### /java/seedu/organizer/model/OrganizerTest.java
``` java
    @Test
    public void addTask_alwaysSorted() throws DuplicateTaskException {
        Organizer addRevision = new OrganizerBuilder().withTask(STUDY)
                .withTask(EXAM).build();
        addRevision.addTask(REVISION);
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(STUDY)
                .withTask(REVISION).withTask(EXAM).build();
        assertEquals(expectedOrganizer, addRevision);
    }

    @Test
    public void editTask_alwaysSorted() throws DuplicateTaskException, TaskNotFoundException {
        Organizer editExam = new OrganizerBuilder().withTask(STUDY)
                .withTask(REVISION).withTask(EXAM).build();
        editExam.updateTask(EXAM, SPRINGCLEAN);
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(STUDY)
                .withTask(REVISION).withTask(SPRINGCLEAN).build();
        assertEquals(expectedOrganizer, editExam);
    }
```
###### /java/seedu/organizer/model/task/DateAddedTest.java
``` java
public class DateAddedTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new DateAdded(null));
    }

    @Test
    public void constructor_invalidDateAdded_throwsIllegalArgumentException() {
        String invalidDateAdded = "2018";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDateAdded));
    }

    @Test
    public void isValidDateAdded() {
        // null deadline
        Assert.assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // blank dateadded
        assertTrue(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" ")); // spaces only

        // missing parts
        assertFalse(Deadline.isValidDeadline("2018-02")); // missing date
        assertFalse(Deadline.isValidDeadline("12-02")); // missing year
        assertFalse(Deadline.isValidDeadline("2019")); // missing month and date
        assertFalse(Deadline.isValidDeadline("12")); // missing year and date

        // invalid parts
        assertFalse(Deadline.isValidDeadline("17-12-12")); // invalid year
        assertFalse(Deadline.isValidDeadline("2019-20-09")); // invalid month
        assertFalse(Deadline.isValidDeadline("2016-02-40")); // invalid date
        assertFalse(Deadline.isValidDeadline("2017-2-09")); // single numbered months should be declared '0x'
        assertFalse(Deadline.isValidDeadline("2017-02-9")); // single numbered dates should be declared '0x'
        assertFalse(Deadline.isValidDeadline("12-30-2017")); // wrong format of MM-DD-YYYY
        assertFalse(Deadline.isValidDeadline("30-12-2017")); // wrong format of DD-MM-YYYY
        assertFalse(Deadline.isValidDeadline(" 2017-08-09")); // leading space
        assertFalse(Deadline.isValidDeadline("2017-08-09 ")); // trailing space
        assertFalse(Deadline.isValidDeadline("2017/09/09")); // wrong symbol

        // valid dateadded
        assertTrue(Deadline.isValidDeadline("2018-03-11"));
        assertTrue(Deadline.isValidDeadline("2017-02-31"));  // dates that have already passed
        assertTrue(Deadline.isValidDeadline("3000-03-23"));   // dates in the far future
    }

    @Test
    public void hashCode_equals() {
        DateAdded testDateAdded = new DateAdded("2018-09-09");
        LocalDate testDateAddedValue = LocalDate.parse("2018-09-09");
        assertEquals(testDateAdded.hashCode(), testDateAddedValue.hashCode());
    }
}
```
###### /java/seedu/organizer/model/task/TaskByUserPredicateTest.java
``` java
public class TaskByUserPredicateTest {

    @Test
    public void equals() {
        User firstPredicate = new User("bob", "bob");
        User secondPredicate = new User("mary", "mary");

        TaskByUserPredicate firstUserPredicate = new TaskByUserPredicate(firstPredicate);
        TaskByUserPredicate secondUserPredicate = new TaskByUserPredicate(secondPredicate);

        // same object -> returns true
        assertTrue(firstUserPredicate.equals(firstUserPredicate));

        // same values -> returns true
        TaskByUserPredicate firstUserPredicateCopy = new TaskByUserPredicate(firstPredicate);
        assertTrue(firstUserPredicate.equals(firstUserPredicateCopy));

        // different types -> returns false
        assertFalse(firstUserPredicate.equals(1));

        // null -> returns false
        assertFalse(firstUserPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstUserPredicate.equals(secondUserPredicate));
    }

    @Test
    public void test_taskContainsUser_returnsTrue() {
        TaskByUserPredicate predicate = new TaskByUserPredicate(new User("admin", "admin"));
        assertTrue(predicate.test(new TaskBuilder().build()));
    }

    @Test
    public void test_taskDoesNotContainUser_returnsFalse() {
        TaskByUserPredicate predicate = new TaskByUserPredicate(new User("doesntexist", "doesntexist"));
        assertFalse(predicate.test(new TaskBuilder().build()));
    }
}

```
###### /java/seedu/organizer/model/task/TaskCreatedContainsDateAdded.java
``` java
/**\
 * Tests whether a DateAdded is automatically created upon Task creation
 */
public class TaskCreatedContainsDateAdded {

    @Test
    public void createTaskContainsDateAdded() {
        Task task = new Task(new Name(VALID_NAME_EXAM), new Priority(VALID_PRIORITY_EXAM),
                new Deadline(VALID_DEADLINE_EXAM), new Description(VALID_DESCRIPTION_EXAM),
                new HashSet<Tag>());
        assertNotNull(task.getDateAdded());
    }

}
```
###### /java/seedu/organizer/model/UniqueTaskListTest.java
``` java
    @Test
    public void priorityAutoUpdateTest() throws DuplicateTaskException {
        UniqueTaskList uniqueTaskList = new UniqueTaskList();
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        LocalDate currentDate = LocalDate.now();

        //CurrentDate equals to AddedDate
        Task taskCurrentDateEqualsToAddedDate = new TaskBuilder().withDeadline("2999-01-01")
                                            .withDateAdded(currentDate.toString()).build();
        Task expectedTaskCurrentDateEqualsToAddedDate = new TaskBuilder().withDeadline("2999-01-01")
                                                    .withDateAdded(currentDate.toString())
                                                    .withPriority(TaskBuilder.DEFAULT_PRIORITY).build();
        uniqueTaskList.add(taskCurrentDateEqualsToAddedDate);
        expectedUniqueTaskList.add(expectedTaskCurrentDateEqualsToAddedDate);
        assertEquals(uniqueTaskList, expectedUniqueTaskList);

        uniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList = new UniqueTaskList();

        //CurrentDate before Deadline
        Task taskCurrentDateBeforeDeadline = new TaskBuilder().withDeadline("2035-01-01")
                .withDateAdded("1900-01-01").build();
        Task expectedTaskCurrentDateBeforeDeadline = new TaskBuilder().withDeadline("2035-01-01")
                .withDateAdded("1900-01-01")
                .withPriority("8").build();
        uniqueTaskList.add(taskCurrentDateBeforeDeadline);
        expectedUniqueTaskList.add(expectedTaskCurrentDateBeforeDeadline);
        assertEquals(uniqueTaskList, expectedUniqueTaskList);

        uniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList = new UniqueTaskList();

        //CurrentDate after Deadline
        Task taskCurrentDateAfterDeadline = new TaskBuilder().withDeadline("1999-01-01")
                .withDateAdded("1950-01-01").build();
        Task expectedTaskCurrentDateAfterDeadline = new TaskBuilder().withDeadline("1999-01-01")
                .withDateAdded("1950-01-01")
                .withPriority(HIGHEST_SETTABLE_PRIORITY_LEVEL).build();
        uniqueTaskList.add(taskCurrentDateAfterDeadline);
        expectedUniqueTaskList.add(expectedTaskCurrentDateAfterDeadline);
        assertEquals(uniqueTaskList, expectedUniqueTaskList);
    }
```
###### /java/seedu/organizer/model/UniqueUserListTest.java
``` java
public class UniqueUserListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueUserList uniqueUserList = new UniqueUserList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueUserList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/organizer/model/user/UserTest.java
``` java
public class UserTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new User(null, null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        String validPassword = "validPass";
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(invalidUsername, validPassword));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String validUsername = "validUsername";
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(validUsername, invalidPassword));
    }

    @Test
    public void isValidUsername() {
        Assert.assertThrows(NullPointerException.class, () -> User.isValidUsername(null));
    }

    @Test
    public void isValidPassword() {
        Assert.assertThrows(NullPointerException.class, () -> User.isValidPassword(null));
    }

}
```
###### /java/seedu/organizer/model/user/UserWithQuestionAnswerTest.java
``` java
public class UserWithQuestionAnswerTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new UserWithQuestionAnswer(null, null));
        Assert.assertThrows(NullPointerException.class, () -> new UserWithQuestionAnswer(null, null, null, null));
    }

    @Test
    public void constructor_invalidUsername_throwsIllegalArgumentException() {
        String invalidUsername = "";
        String validPassword = "validPass";
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new UserWithQuestionAnswer(invalidUsername, validPassword));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String validUsername = "validUsername";
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new UserWithQuestionAnswer(validUsername, invalidPassword));
    }

    @Test
    public void constructor_invalidQuestion_throwsIllegalArgumentException() {
        String validUsername = "validUsername";
        String validPassword = "validPassword";
        String invalidQuestion = "";
        String validAnswer = "valid answer";
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new UserWithQuestionAnswer(validUsername, validPassword, invalidQuestion, validAnswer));
    }

    @Test
    public void constructor_invalidAnswer_throwsIllegalArgumentException() {
        String validUsername = "validUsername";
        String validPassword = "validPassword";
        String validQuestion = "valid question";
        String invalidAnswer = "";
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new UserWithQuestionAnswer(validUsername, validPassword, validQuestion, invalidAnswer));
    }

    @Test
    public void isValidUsername() {
        Assert.assertThrows(NullPointerException.class, () ->
                UserWithQuestionAnswer.isValidUsername(null));
    }

    @Test
    public void isValidPassword() {
        Assert.assertThrows(NullPointerException.class, () -> UserWithQuestionAnswer.isValidPassword(null));
    }

    @Test
    public void isValidQuestion() {
        Assert.assertThrows(NullPointerException.class, () -> UserWithQuestionAnswer.isValidQuestion(null));
    }

    @Test
    public void isValidAnswer() {
        Assert.assertThrows(NullPointerException.class, () -> UserWithQuestionAnswer.isValidAnswer(null));
    }

}

```
###### /java/seedu/organizer/storage/XmlAdaptedUserTest.java
``` java
public class XmlAdaptedUserTest {

    public static final String USERNAME = "Jennifer";
    public static final String PASSWORD = "Jennifer123";

    public static final String OTHER_USERNAME = "bobby";

    @Test
    public void equal_defaultconstructor() {
        XmlAdaptedUser user = new XmlAdaptedUser(USERNAME, PASSWORD);
        XmlAdaptedUser otherUser = new XmlAdaptedUser(USERNAME, PASSWORD);
        assertEquals(user, otherUser);
        assertEquals(user, user);

        XmlAdaptedUser diffUser = new XmlAdaptedUser(OTHER_USERNAME, PASSWORD);
        assertNotEquals(user, diffUser);
    }

    @Test
    public void equal_userconstructor() {
        XmlAdaptedUser user = new XmlAdaptedUser(USERNAME, PASSWORD);
        XmlAdaptedUser otherUser = new XmlAdaptedUser(USERNAME, PASSWORD);
        assertEquals(user, otherUser);
        assertEquals(user, user);
    }

    @Test
    public void toModel_invalidUsername() {
        Assert.assertThrows(
                IllegalValueException.class, () -> new XmlAdaptedUser("", "validpassword").toUserModelType()
        );
    }

    @Test
    public void toModel_invalidPassword() {
        Assert.assertThrows(
                IllegalValueException.class, () -> new XmlAdaptedUser("validusername", "").toUserModelType()
        );
    }
}
```
###### /java/systemtests/ClearCommandSystemTest.java
``` java
    */
/**
 * Executes {@code command} and verifies that the command box displays an empty string, the result display
 * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to an empty model.
 * These verifications are done by
 * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
 * Also verifies that the command box has the default style class and the status bar's sync status changes.
 * Also verifies that the {@code expectedResultMessage} is displayed
 * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
 *//*
    public void assertCommandSuccess(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        expectedModel.deleteCurrentUserTasks();
        assertCommandSuccess(command, expectedResultMessage, expectedModel);
    }
```
