# kaisertanqr
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
