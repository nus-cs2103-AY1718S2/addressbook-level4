# kaisertanqr
###### /java/systemtests/CreateUserCommandSystemTest.java
``` java

public class CreateUserCommandSystemTest extends AddressBookSystemTestWithLogin {


    private File file = new File(TestUtil.getFilePathInSandboxFolder("sampleUsers.xml"));

    @Test
    public void createUser_successful() {
        file.delete();
        Model model = getModel();
        ModelHelper.setUsersList(model, DEFAULT_USER, SLAP);

        /* Case: add a user to an empty userdatabase
         * -> added
         */
        User toAdd = new User(new Username("lana"), new Password("pass"));
        String command = CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "lana  " + PREFIX_PASSWORD
                + "pass";
        assertCommandSuccess(command, toAdd);
        file.delete();
    }

    @Test
    public void createUser_multipleSameUser_failure() {
        file.delete();
        Model model = getModel();
        ModelHelper.setUsersList(model, DEFAULT_USER, SLAP);

        /* Case: add a user to an empty userdatabase
         * -> added
         */
        User toAdd = new User(new Username("lana"), new Password("pass"));
        String command = CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "lana  " + PREFIX_PASSWORD
                + "pass";
        assertCommandSuccess(command, toAdd);

        /* Case: add a same user to an empty userdatabase
         * -> added
         */
        command = CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "lana  " + PREFIX_PASSWORD
                + "pass";
        assertCommandFailureNoClearCommandBox(command, MESSAGE_DUPLICATE_USER);

        /* Case: add a same user to an empty userdatabase (Upper case characters)
         * -> added
         */
        command = CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "LaNa  " + PREFIX_PASSWORD
                + "pass";
        assertCommandFailureNoClearCommandBox(command, MESSAGE_DUPLICATE_USER);
        file.delete();
    }



    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     */
    private void assertCommandSuccess(String command, User toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addUser(toAdd);
        } catch (DuplicateUserException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(String.format(MESSAGE_SUCCESS, toAdd.getUsername().toString()),
                toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Person)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code PersonListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     * @see CreateUserCommandSystemTest#assertCommandSuccess(String, User)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailureNoClearCommandBox(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
    }

}
```
###### /java/systemtests/ModelHelper.java
``` java
    /**
     * Updates the model to login as a valid user
     */
    public static void loginAs(Model model, Username username, Password password) {
        try {
            model.checkLoginCredentials(username, password);
        } catch (AlreadyLoggedInException e) {
            throw new AssertionError("not possible");
        }
    }

    /**
     * Updates {@code model}'s users list.
     */
    public static void setUsersList(Model model, UniqueUserList uniqueUserList) {
        model.setUsersList(uniqueUserList);
    }

    /**
     * @see ModelHelper#setUsersList(Model, UniqueUserList)
     */
    public static void setUsersList(Model model, User... users) {
        UniqueUserList uniqueUserList = new UniqueUserList();
        for (User user : users) {
            try {
                uniqueUserList.add(user);
            } catch (DuplicateUserException e) {
                throw new AssertionError("not possible");
            }
        }
        setUsersList(model, uniqueUserList);
    }

```
###### /java/systemtests/LoginCommandSystemTest.java
``` java

public class LoginCommandSystemTest extends AddressBookSystemTestWithLogin {

    @Test
    public void login_normal_successful() {
        /*
         * Case: login using the correct credentials
         */
        String command = LoginCommand.COMMAND_WORD + " " + PREFIX_USERNAME + "u " + PREFIX_PASSWORD
                + "p ";
        Model expectedModel = getModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandSuccess(command, expectedModel);

    }

    @Test
    public void login_incorrectFormat_failure() {
        /*
         * Case: login using the correct credentials with trailing whitespace
        */
        String command = "   " + LoginCommand.COMMAND_WORD + " " + PREFIX_USERNAME + "u " + PREFIX_PASSWORD
                + "p ";
        Model expectedModel = getModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandFailure(command, CommandBox.MESSAGE_HAVE_NOT_LOGGED_IN);
    }

    @Test
    public void login_incorrectCredentials_failure() {
        /*
         * Case: login using the incorrect credentials
         */
        String command = LoginCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "l " + PREFIX_PASSWORD
                + "p ";
        Model expectedModel = getModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandFailure(command, MESSAGE_LOGIN_FAILURE);
    }

    @Test
    public void login_multipleAttempts_failure() {
        /*
         * Case: login using the correct credentials
         */
        String command = LoginCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "u " + PREFIX_PASSWORD
                + "p ";
        Model expectedModel = getModel();
        ModelHelper.setUsersList(expectedModel, DEFAULT_USER, SLAP);
        assertCommandSuccess(command, expectedModel);
        /*
         * Case: Login again with the same credentials
         */
        command = LoginCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "u " + PREFIX_PASSWORD
                + "p ";
        assertCommandFailureNoClearCommandBox(command, MESSAGE_LOGIN_ALREADY);

    }



    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = LoginCommand.MESSAGE_LOGIN_SUCCESS;
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertStatusBarUnchanged();
    }
    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code PersonListPanel} remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailureNoClearCommandBox(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertStatusBarUnchanged();
    }


}
```
###### /java/seedu/address/logic/parser/LoginCommandParserTest.java
``` java
public class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        LoginCommand validLoginCommand = new LoginCommand(new Username(VALID_LOGIN_USERNAME),
                new Password(VALID_LOGIN_PASSWORD));

        //successful input
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD, validLoginCommand);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                validLoginCommand);

        // multiple usernames - last username accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                        + LOGIN_VALID_DESC_PASSWORD, validLoginCommand);

        // multiple usernames with valid last username - last username accepted
        assertParseSuccess(parser,  LOGIN_INVALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                        + LOGIN_VALID_DESC_PASSWORD, validLoginCommand);

        // multiple passwords with valid last password - last password accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_INVALID_DESC_PASSWORD
                        + LOGIN_VALID_DESC_PASSWORD, validLoginCommand);

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_LOGIN_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, LOGIN_INVALID_DESC_PASSWORD, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, LOGIN_INVALID_DESC_USERNAME, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid username
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD + "@@",
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD + "@@",
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                String.format(MESSAGE_INVALID_LOGIN_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));

    }


}
```
###### /java/seedu/address/logic/parser/ChangeUserPasswordCommandParserTest.java
``` java
public class ChangeUserPasswordCommandParserTest {

    private ChangeUserPasswordCommandParser parser = new ChangeUserPasswordCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        ChangeUserPasswordCommand validChangeUserCommand =
                new ChangeUserPasswordCommand(new Username(VALID_LOGIN_USERNAME), new Password(VALID_LOGIN_PASSWORD),
                new Password(VALID_LOGIN_PASSWORD + "AA"));

        //successful input
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                        + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                        + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // multiple usernames - last username accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD  + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // multiple usernames with valid last username - last username accepted
        assertParseSuccess(parser,  LOGIN_INVALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD  + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // multiple passwords with valid last password - last password accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_INVALID_DESC_PASSWORD
                + LOGIN_VALID_DESC_PASSWORD + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

        // multiple new passwords with valid last password - last password accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_INVALID_DESC_PASSWORD
                + LOGIN_VALID_DESC_PASSWORD + NEWPASS_INVALID_DESC_PASSWORD
                + NEWPASS_VALID_DESC_PASSWORD, validChangeUserCommand);

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeUserPasswordCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, LOGIN_VALID_DESC_PASSWORD, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME, expectedMessage);

        // missing new password prefix
        assertParseFailure(parser, NEWPASS_VALID_DESC_PASSWORD, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid username
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD
                        + NEWPASS_INVALID_DESC_PASSWORD, Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD + "@@ "
                + NEWPASS_VALID_DESC_PASSWORD, Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                + NEWPASS_VALID_DESC_PASSWORD + "@@", Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD + "@@ "
                        + NEWPASS_INVALID_DESC_PASSWORD, Username.MESSAGE_USERNAME_CONSTRAINTS);

        // three invalid values, only first invalid value reported
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD + "@@ "
                + NEWPASS_INVALID_DESC_PASSWORD + "@@", Username.MESSAGE_USERNAME_CONSTRAINTS);

        // non-empty preamble
        System.out.println(PREAMBLE_NON_EMPTY + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                + NEWPASS_VALID_DESC_PASSWORD);
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD
                        + NEWPASS_VALID_DESC_PASSWORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeUserPasswordCommand.MESSAGE_USAGE));

    }
}
```
###### /java/seedu/address/logic/parser/CreateUserCommandParserTest.java
``` java
public class CreateUserCommandParserTest {

    private CreateUserCommandParser parser = new CreateUserCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        CreateUserCommand validCreateUserCommand = new CreateUserCommand(new User(new Username(VALID_LOGIN_USERNAME),
                new Password(VALID_LOGIN_PASSWORD)));

        //successful input
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD, validCreateUserCommand);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                validCreateUserCommand);

        // multiple usernames - last username accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD, validCreateUserCommand);

        // multiple usernames with valid last username - last username accepted
        assertParseSuccess(parser,  LOGIN_INVALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD, validCreateUserCommand);

        // multiple passwords with valid last password - last password accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_INVALID_DESC_PASSWORD
                + LOGIN_VALID_DESC_PASSWORD, validCreateUserCommand);

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateUserCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, LOGIN_INVALID_DESC_PASSWORD, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, LOGIN_INVALID_DESC_USERNAME, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid username
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD + "@@",
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD + "@@",
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateUserCommand.MESSAGE_USAGE));

    }
}
```
###### /java/seedu/address/logic/parser/DeleteUserCommandParserTest.java
``` java
public class DeleteUserCommandParserTest {

    private DeleteUserCommandParser parser = new DeleteUserCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {

        DeleteUserCommand validDeleteUserCommand = new DeleteUserCommand(new Username(VALID_LOGIN_USERNAME),
                new Password(VALID_LOGIN_PASSWORD));

        //successful input
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                validDeleteUserCommand);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                validDeleteUserCommand);

        // multiple usernames - last username accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD, validDeleteUserCommand);

        // multiple usernames with valid last username - last username accepted
        assertParseSuccess(parser,  LOGIN_INVALID_DESC_USERNAME + LOGIN_VALID_DESC_USERNAME
                + LOGIN_VALID_DESC_PASSWORD, validDeleteUserCommand);

        // multiple passwords with valid last password - last password accepted
        assertParseSuccess(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_INVALID_DESC_PASSWORD
                + LOGIN_VALID_DESC_PASSWORD, validDeleteUserCommand);

    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteUserCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, LOGIN_INVALID_DESC_PASSWORD, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, LOGIN_INVALID_DESC_USERNAME, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid username
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD + "@@",
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, LOGIN_VALID_DESC_USERNAME + "@@ " + LOGIN_VALID_DESC_PASSWORD + "@@",
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + LOGIN_VALID_DESC_USERNAME + LOGIN_VALID_DESC_PASSWORD,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteUserCommand.MESSAGE_USAGE));

    }
}
```
###### /java/seedu/address/logic/commands/LoginCommandTest.java
``` java
public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_loginAcceptedByModel_loginSuccessful() throws Exception {
        ModelStubAcceptingLogin modelStub = new ModelStubAcceptingLogin();

        LoginAttempt loginAttempt = new LoginAttempt("slap", "password");

        CommandResult commandResult = getLoginCommandForLoginAttempt(loginAttempt.getUsername(),
                loginAttempt.getPassword(), modelStub).execute();

        assertEquals(LoginCommand.MESSAGE_LOGIN_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_loginAcceptedByModel_loginFailure() throws Exception {
        ModelStubAcceptingLogin modelStub = new ModelStubAcceptingLogin();
        LoginAttempt invalidloginAttempt = new LoginAttempt("slapsdad", "password");

        CommandResult commandResult = getLoginCommandForLoginAttempt(invalidloginAttempt.getUsername(),
                invalidloginAttempt.getPassword(), modelStub).execute();

        assertEquals(LoginCommand.MESSAGE_LOGIN_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_alreadyLoggedIn_throwsCommandException() throws Exception {
        ModelStubThrowingAlreadyLoggedInException modelStub = new ModelStubThrowingAlreadyLoggedInException();
        LoginAttempt validloginAttempt = new LoginAttempt("slap", "password");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_LOGIN_ALREADY);

        getLoginCommandForLoginAttempt(validloginAttempt.getUsername(),
                validloginAttempt.getPassword(), modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private LoginCommand getLoginCommandForLoginAttempt(Username username, Password password, Model model) {
        LoginCommand command = new LoginCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
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
        public boolean hasLoggedIn() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public User getLoggedInUser() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void setLoginStatus(boolean status) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            fail("This method should not be called.");
            return false;
        };

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public void addUser(User person) throws DuplicateUserException {
            fail("This method should not be called.");
        };

        @Override
        public void deleteUser(User target) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public ReadOnlyAddressBook getUserDatabase() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void addLogToPerson(Person target, Person editedPersonWithNewLog)
                throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            fail("This method should not be called.");
        }
    }


    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingLogin extends ModelStub {

        private boolean loginStatus = false;

        @Override
        public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
            requireNonNull(username);
            requireNonNull(password);
            setLoginStatus(true);
            return true;
        }


        @Override
        public void setLoginStatus(boolean status) {
            this.loginStatus = true;
        }

        @Override
        public boolean hasLoggedIn() {
            return this.loginStatus;
        }


    }


    /**
     * A Model stub that always throw a AlreadyLoggedInException when trying to login.
     */
    private class ModelStubThrowingAlreadyLoggedInException extends ModelStub {
        @Override
        public boolean checkLoginCredentials(Username username, Password password)
                throws AlreadyLoggedInException {
            throw new AlreadyLoggedInException();
        }

    }

    private class LoginAttempt {
        private Username username;
        private Password password;

        public LoginAttempt(String username, String password) {
            this.username = new Username(username);
            this.password = new Password(password);
        }

        public Password getPassword() {
            return password;
        }

        public Username getUsername() {
            return username;
        }
    }

}
```
###### /java/seedu/address/logic/commands/ChangeUserPasswordCommandTest.java
``` java
public class ChangeUserPasswordCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Password newPassword = new Password("hello");

    @Test
    public void execute_changePasswordAcceptedByModel_changePasswordSuccessful() throws Exception {
        ChangeUserPasswordCommandTest.ModelStubAcceptingChangePassword modelStub =
                new ChangeUserPasswordCommandTest.ModelStubAcceptingChangePassword();

        User validUser = createValidUser();

        CommandResult commandResult =
                getChangePasswordCommandForChangePasswordAttempt(validUser, modelStub).execute();

        assertEquals(String.format(ChangeUserPasswordCommand.MESSAGE_SUCCESS, validUser.getUsername().toString()),
                commandResult.feedbackToUser);
    }


    @Test
    public void execute_userNotFound_throwsCommandException() throws Exception {
        ChangeUserPasswordCommandTest.ModelStubThrowingUserNotFoundException modelStub =
                new ChangeUserPasswordCommandTest.ModelStubThrowingUserNotFoundException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(ChangeUserPasswordCommand.MESSAGE_UPDATE_FAILURE);

        getChangePasswordCommandForChangePasswordAttempt(validUser, modelStub).execute();
    }

    @Test
    public void execute_notLoggedOut_throwsCommandException() throws Exception {
        ChangeUserPasswordCommandTest.ModelStubThrowingAlreadyLoggedInException modelStub =
                new ChangeUserPasswordCommandTest.ModelStubThrowingAlreadyLoggedInException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(ChangeUserPasswordCommand.MESSAGE_NOT_LOGGED_OUT);

        getChangePasswordCommandForChangePasswordAttempt(validUser, modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private ChangeUserPasswordCommand getChangePasswordCommandForChangePasswordAttempt(User user, Model model) {

        ChangeUserPasswordCommand command = new ChangeUserPasswordCommand(user.getUsername(),
                user.getPassword(), newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
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
        public boolean hasLoggedIn() {
            return false;
        }

        @Override
        public User getLoggedInUser() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void setLoginStatus(boolean status) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            return true;
        };

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public void addUser(User person) throws DuplicateUserException {
            fail("This method should not be called.");
        };

        @Override
        public void deleteUser(User target) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public ReadOnlyAddressBook getUserDatabase() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void addLogToPerson(Person target, Person editedPersonWithNewLog)
                throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            fail("This method should not be called.");
        }
    }


    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingChangePassword extends ChangeUserPasswordCommandTest.ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void updateUserPassword(User user, User userNewPassword) throws UserNotFoundException {
            usersAdded.add(createValidUser());
            requireAllNonNull(user, userNewPassword);
            usersAdded.set(0, userNewPassword);
        }

    }


    /**
     * A Model stub that always throw a DuplicatePersonException when trying to login.
     */
    private class ModelStubThrowingUserNotFoundException extends ChangeUserPasswordCommandTest.ModelStub {
        @Override
        public void updateUserPassword(User target, User userNewPassword) throws UserNotFoundException {
            throw new UserNotFoundException();
        }

    }

    /**
     * A Model stub that always throw a AlreadyLoggedInException when trying to login.
     */
    private class ModelStubThrowingAlreadyLoggedInException extends ChangeUserPasswordCommandTest.ModelStub {
        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            throw new AlreadyLoggedInException();
        }

    }

    private User createValidUser() {
        return new User(new Username("slap"), new Password("pass"));
    }
}
```
###### /java/seedu/address/logic/commands/DeleteUserCommandTest.java
``` java
public class DeleteUserCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_deleteAcceptedByModel_deleteSuccessful() throws Exception {
        DeleteUserCommandTest.ModelStubAcceptingDeleteUser modelStub =
                new DeleteUserCommandTest.ModelStubAcceptingDeleteUser();

        User validUser = createValidUser();

        CommandResult commandResult =
                getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute();

        assertEquals(String.format(DeleteUserCommand.MESSAGE_SUCCESS, validUser.getUsername().toString()),
                commandResult.feedbackToUser);
    }


    @Test
    public void execute_userNotFound_throwsCommandException() throws Exception {
        DeleteUserCommandTest.ModelStubThrowingUserNotFoundException modelStub =
                new DeleteUserCommandTest.ModelStubThrowingUserNotFoundException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteUserCommand.MESSAGE_DELETE_FAILURE);

        getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute();
    }

    @Test
    public void execute_notLoggedOut_throwsCommandException() throws Exception {
        DeleteUserCommandTest.ModelStubThrowingAlreadyLoggedInException modelStub =
                new DeleteUserCommandTest.ModelStubThrowingAlreadyLoggedInException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(DeleteUserCommand.MESSAGE_NOT_LOGGED_OUT);

        getDeleteUserCommandForDeleteUserAttempt(validUser, modelStub).execute();
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     */
    private DeleteUserCommand getDeleteUserCommandForDeleteUserAttempt(User user, Model model) {

        DeleteUserCommand command = new DeleteUserCommand(user.getUsername(), user.getPassword());
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
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
        public boolean hasLoggedIn() {
            return false;
        }

        @Override
        public User getLoggedInUser() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void setLoginStatus(boolean status) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            return true;
        };

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public void addUser(User person) throws DuplicateUserException {
            fail("This method should not be called.");
        };

        @Override
        public void deleteUser(User target) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public ReadOnlyAddressBook getUserDatabase() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void addLogToPerson(Person target, Person editedPersonWithNewLog)
                throws PersonNotFoundException {
            fail("This method should not be called.");
        }
        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            fail("This method should not be called.");
        }
    }


    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingDeleteUser extends DeleteUserCommandTest.ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void deleteUser(User user) throws UserNotFoundException {
            usersAdded.add(createValidUser());
            requireNonNull(user);
            usersAdded.remove(user);
        }

    }


    /**
     * A Model stub that always throw a DuplicatePersonException when trying to login.
     */
    private class ModelStubThrowingUserNotFoundException extends DeleteUserCommandTest.ModelStub {
        @Override
        public void deleteUser(User user) throws UserNotFoundException {
            throw new UserNotFoundException();
        }

    }

    /**
     * A Model stub that always throw a AlreadyLoggedInException when trying to login.
     */
    private class ModelStubThrowingAlreadyLoggedInException extends DeleteUserCommandTest.ModelStub {
        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            throw new AlreadyLoggedInException();
        }

    }

    private User createValidUser() {
        return new User(new Username("slap"), new Password("pass"));
    }
}
```
###### /java/seedu/address/logic/commands/CreateUserCommandTest.java
``` java
public class CreateUserCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_createUserAcceptedByModel_createSuccessful() throws Exception {
        CreateUserCommandTest.ModelStubAcceptingCreateUser modelStub =
                new CreateUserCommandTest.ModelStubAcceptingCreateUser();

        User validUser = createValidUser();

        CommandResult commandResult =
                getCreateUserCommandForCreateUserAttempt(validUser, modelStub).execute();

        assertEquals(String.format(CreateUserCommand.MESSAGE_SUCCESS, validUser.getUsername().toString()),
                commandResult.feedbackToUser);
    }


    @Test
    public void execute_duplicateUser_throwsCommandException() throws Exception {
        CreateUserCommandTest.ModelStubThrowingDuplicateUserException modelStub =
                new CreateUserCommandTest.ModelStubThrowingDuplicateUserException();

        User validUser = createValidUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateUserCommand.MESSAGE_DUPLICATE_USER);

        getCreateUserCommandForCreateUserAttempt(validUser, modelStub).execute();
    }

    /**
     * Generates a new CreateUserCommand with the details of the given person.
     */
    private CreateUserCommand getCreateUserCommandForCreateUserAttempt(User user, Model model) {

        CreateUserCommand command = new CreateUserCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {
        @Override
        public void addPerson(Person person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
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
        public boolean hasLoggedIn() {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public User getLoggedInUser() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void setLoginStatus(boolean status) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
            fail("This method should not be called.");
            return false;
        };

        @Override
        public void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public void addUser(User person) throws DuplicateUserException {
            fail("This method should not be called.");
        };

        @Override
        public void deleteUser(User target) throws UserNotFoundException {
            fail("This method should not be called.");
        };

        @Override
        public ReadOnlyAddressBook getUserDatabase() {
            fail("This method should not be called.");
            return null;
        };

        @Override
        public void addLogToPerson(Person target, Person editedPersonWithNewLog)
                throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void setUsersList(UniqueUserList uniqueUserList) {
            fail("This method should not be called.");
        }

    }


    /**
     * A Model stub that always accepts the login attempt.
     */
    private class ModelStubAcceptingCreateUser extends CreateUserCommandTest.ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void addUser(User user) throws DuplicateUserException {
            requireNonNull(user);
            usersAdded.add(user);
        }

    }


    /**
     * A Model stub that always throw a DuplicateUserException when trying to login.
     */
    private class ModelStubThrowingDuplicateUserException extends CreateUserCommandTest.ModelStub {
        @Override
        public void addUser(User person) throws DuplicateUserException {
            throw new DuplicateUserException();
        }

    }

    private User createValidUser() {
        return new User(new Username("slap"), new Password("pass"));
    }

}
```
###### /java/seedu/address/testutil/TypicalUsers.java
``` java
/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {

    public static final User DEFAULT_USER = new User(new Username("username"), new Password("pass"));
    public static final User SLAP = new User(new Username("slap"), new Password("pass"));
    public static final User LONG = new User(new Username("long"), new Password("pass"));
    public static final User KARA = new User(new Username("kara"), new Password("pass"));
    public static final User DANY = new User(new Username("dany"), new Password("pass"));

    private TypicalUsers() {} // prevents instantiation

    /**
     * Returns an {@code UserDatabase} with all the typical users.
     */
    public static UserDatabase getTypicalUserDatabase() {
        UserDatabase ud = new UserDatabase();
        for (User user: getTypicalUsers()) {
            try {
                ud.addUser(user);
            } catch (DuplicateUserException e) {
                throw new AssertionError("not possible");
            }
        }
        return ud;
    }

    public static List<User> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(DEFAULT_USER, SLAP, LONG, KARA, DANY));
    }

}
```
