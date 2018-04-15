# shadow2496
###### \java\guitests\guihandles\ResultDisplayHandle.java
``` java
    /**
     * Returns the list of style classes present in the result display.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void loginAccount(Account account) {
            fail("This method should not be called.");
        }

        @Override
        public void setVerificationCode(String code) {
            fail("This method should not be called.");
        }

```
###### \java\seedu\address\logic\commands\LoginCommandTest.java
``` java
public class LoginCommandTest {

    @Test
    public void execute_login_success() {
        Account validAccount = new AccountBuilder().build();
        LoginCommand command = new LoginCommand(validAccount);

        String expectedMessage = String.format(LoginCommand.MESSAGE_SUCCESS, validAccount);

        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        assertCommandSuccess(command, model, expectedMessage, model);
    }

    @Test
    public void equals() {
        Account account = new AccountBuilder().withUsername(VALID_USERNAME_AMY)
                .withPassword(VALID_PASSWORD_AMY).build();
        Account accountWithDiffUsername = new AccountBuilder().withUsername(VALID_USERNAME_BOB)
                .withPassword(VALID_PASSWORD_AMY).build();
        Account accountWithDiffPassword = new AccountBuilder().withUsername(VALID_USERNAME_AMY)
                .withPassword(VALID_PASSWORD_BOB).build();
        LoginCommand standardCommand = new LoginCommand(account);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // same values -> returns true
        LoginCommand commandWithSameValues = new LoginCommand(account);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // different types -> returns false
        assertFalse(standardCommand.equals(1));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different values -> returns false
        LoginCommand commandWithDiffUsername = new LoginCommand(accountWithDiffUsername);
        LoginCommand commandWithDiffPassword = new LoginCommand(accountWithDiffPassword);
        assertFalse(standardCommand.equals(commandWithDiffUsername));
        assertFalse(standardCommand.equals(commandWithDiffPassword));
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_login() throws Exception {
        Account account = new AccountBuilder().build();
        LoginCommand command = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_WORD + " " + AccountUtil.getAccountDetails(account));
        assertEquals(new LoginCommand(account), command);
    }

```
###### \java\seedu\address\logic\parser\LoginCommandParserTest.java
``` java
public class LoginCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Account account = new AccountBuilder().withUsername(VALID_USERNAME_BOB)
                .withPassword(VALID_PASSWORD_BOB).build();
        LoginCommand expectedCommand = new LoginCommand(account);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_BOB + PASSWORD_DESC_BOB,
                expectedCommand);

        // multiple usernames - last username accepted
        assertParseSuccess(parser, USERNAME_DESC_AMY + USERNAME_DESC_BOB + PASSWORD_DESC_BOB,
                expectedCommand);

        // multiple passwords - last password accepted
        assertParseSuccess(parser, USERNAME_DESC_BOB + PASSWORD_DESC_AMY + PASSWORD_DESC_BOB,
                expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing username prefix
        assertParseFailure(parser, VALID_USERNAME_BOB + PASSWORD_DESC_BOB, MESSAGE_INVALID_FORMAT);

        // missing password prefix
        assertParseFailure(parser, USERNAME_DESC_BOB + VALID_PASSWORD_BOB, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC_BOB,
                Username.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, USERNAME_DESC_BOB + INVALID_PASSWORD_DESC,
                Password.MESSAGE_PASSWORD_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC,
                Username.MESSAGE_USERNAME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + USERNAME_DESC_BOB + PASSWORD_DESC_BOB,
                MESSAGE_INVALID_FORMAT);
    }
}
```
###### \java\seedu\address\model\account\PasswordTest.java
``` java
public class PasswordTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Password(null));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String invalidPassword = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Password(invalidPassword));
    }

    @Test
    public void isValidPassword() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // invalid passwords
        assertFalse(Password.isValidPassword("")); // empty string
        assertFalse(Password.isValidPassword(" ")); // spaces only

        // valid passwords
        assertTrue(Password.isValidPassword("amy1111"));
        assertTrue(Password.isValidPassword("-")); // one character
        assertTrue(Password.isValidPassword("amy1111!very!very!very!long!example")); // long password
    }
}
```
###### \java\seedu\address\model\account\UsernameTest.java
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
        // null username
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // invalid usernames
        assertFalse(Username.isValidUsername("")); // empty string
        assertFalse(Username.isValidUsername(" ")); // spaces only

        // valid usernames
        assertTrue(Username.isValidUsername("amybee"));
        assertTrue(Username.isValidUsername("-")); // one character
        assertTrue(Username.isValidUsername("amybee@very-very-very-long-example.com")); // long username
    }
}
```
###### \java\seedu\address\testutil\AccountBuilder.java
``` java
/**
 * A utility class to help with building Account objects.
 */
public class AccountBuilder {

    private static final String DEFAULT_USERNAME = "alicepauline";
    private static final String DEFAULT_PASSWORD = "alice8535";

    private Username username;
    private Password password;

    public AccountBuilder() {
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
    }

    /**
     * Sets the {@code Username} of the {@code Account} that we are building.
     */
    public AccountBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Account} that we are building.
     */
    public AccountBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    public Account build() {
        return new Account(username, password);
    }
}
```
###### \java\seedu\address\testutil\AccountUtil.java
``` java
/**
 * A utility class for Account.
 */
public class AccountUtil {

    /**
     * Returns the part of command string for the given {@code account}'s details.
     */
    public static String getAccountDetails(Account account) {
        return PREFIX_USERNAME + account.getUsername().value + " "
                + PREFIX_PASSWORD + account.getPassword().value;
    }
}
```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
    private static final NewResultAvailableEvent NEW_RESULT_EVENT_NON_ERROR =
            new NewResultAvailableEvent("Non Error", false);
    private static final NewResultAvailableEvent NEW_RESULT_EVENT_ERROR =
            new NewResultAvailableEvent("Error", true);

    private ArrayList<String> defaultStyleOfResultDisplay;
    private ArrayList<String> errorStyleOfResultDisplay;

```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
        uiPartRule.setUiPart(resultDisplay);

        defaultStyleOfResultDisplay = new ArrayList<>(resultDisplayHandle.getStyleClass());

        errorStyleOfResultDisplay = new ArrayList<>(defaultStyleOfResultDisplay);
        errorStyleOfResultDisplay.add(ResultDisplay.ERROR_STYLE_CLASS);

```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
        assertEquals(defaultStyleOfResultDisplay, resultDisplayHandle.getStyleClass());

        // new error result received
        postNow(NEW_RESULT_EVENT_ERROR);
        guiRobot.pauseForHuman();
        assertBehaviorForErrorResult();

        // new non-error result received
        postNow(NEW_RESULT_EVENT_NON_ERROR);
        guiRobot.pauseForHuman();
        assertBehaviorForNonErrorResult();

```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
    /**
     * Verifies a result which has an error that <br>
     *      - the text remains <br>
     *      - the result display's style is the same as {@code errorStyleOfResultDisplay}.
     */
    private void assertBehaviorForErrorResult() {
        assertEquals(NEW_RESULT_EVENT_ERROR.message, resultDisplayHandle.getText());
        assertEquals(errorStyleOfResultDisplay, resultDisplayHandle.getStyleClass());
    }

    /**
     * Verifies a result which doesn't have an error that <br>
     *      - the text remains <br>
     *      - the result display's style is the same as {@code defaultStyleOfResultDisplay}.
     */
    private void assertBehaviorForNonErrorResult() {
        assertEquals(NEW_RESULT_EVENT_NON_ERROR.message, resultDisplayHandle.getText());
        assertEquals(defaultStyleOfResultDisplay, resultDisplayHandle.getStyleClass());
    }
}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the result display's shows the default style.
     */
    protected void assertResultDisplayShowsDefaultStyle() {
        assertEquals(RESULT_DISPLAY_DEFAULT_STYLE, getResultDisplay().getStyleClass());
    }

    /**
     * Asserts that the result display's shows the error style.
     */
    protected void assertResultDisplayShowsErrorStyle() {
        assertEquals(RESULT_DISPLAY_ERROR_STYLE, getResultDisplay().getStyleClass());
    }

```
