# shadow2496
###### \java\seedu\address\logic\commands\LoginCommandTest.java
``` java
public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute_throwsCommandException() throws Exception {
        Account validAccount = new AccountBuilder().build();
        LoginCommand command = new LoginCommand(validAccount);

        thrown.expect(CommandException.class);
        thrown.expectMessage("Test");

        command.execute();
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
