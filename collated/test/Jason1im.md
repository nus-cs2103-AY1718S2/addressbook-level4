# Jason1im
###### /java/seedu/address/logic/commands/LoginCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private String username = "John";
    private String password = "123";


    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SignupCommand command = new SignupCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }


    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, password);
        new LoginCommand(username, null);
    }

    @Test
    public void execute_loginSuccessful() throws Exception {
        CommandResult commandResult = getLoginCommand(username, password, model).execute();
        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, username),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidUsername_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_USERNAME);
        LoginCommand command = getLoginCommand("Jane", password, model);
        command.execute();
    }

    @Test
    public void execute_invalidPassword_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PASSWORD);
        LoginCommand command = getLoginCommand(username, "43546", model);
        command.execute();
    }

    @Test
    public void execute_multipleLogin_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_MULTIPLE_LOGIN);
        LoginCommand command = getLoginCommand(username, password, model);
        command.execute();
        command.execute();
    }

    public LoginCommand getLoginCommand(String username, String password, Model model) {
        LoginCommand command = new LoginCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### /java/seedu/address/logic/commands/LogoutCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SignupCommand command = new SignupCommand("John", "123");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();

        LoginCommand login = new LoginCommand("John", "123");
        login.setData(model, new CommandHistory(), new UndoRedoStack());
        login.execute();
    }

    @Test
    public void execute_logoutSuccessful() throws Exception {
        CommandResult commandResult = getLogoutCommand(model).execute();
        assertEquals(LogoutCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
    }

    @Test
    public void execute_logoutFailure() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_MULTIPLE_LOGOUT);
        LogoutCommand command = getLogoutCommand(model);
        command.execute();
        command.execute();
    }

    public LogoutCommand getLogoutCommand(Model model) {
        LogoutCommand command = new LogoutCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### /java/seedu/address/logic/commands/ClearHistoryCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ClearHistoryCommandTest {
    private ClearHistoryCommand clearHistoryCommand;
    private CommandHistory history;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        history = new CommandHistory();
        clearHistoryCommand = new ClearHistoryCommand();
        clearHistoryCommand.setData(model, history, new UndoRedoStack());
    }

    @Test
    public void execute() {
        assertCommandResult(clearHistoryCommand, ClearHistoryCommand.MESSAGE_SUCCESS);

        String command2 = "randomCommand";
        String command3 = "select 1";
        history.add(command2);
        history.add(command3);

        String expectedMessage = ClearHistoryCommand.MESSAGE_SUCCESS;

        assertCommandResult(clearHistoryCommand, expectedMessage);
    }

    /**
     * Asserts that the result message from the execution of {@code clearHistoryCommand}
     * is equal to {@code expectedMessage}
     */
    private void assertCommandResult(ClearHistoryCommand clearHistoryCommand, String expectedMessage) {
        assertEquals(expectedMessage, clearHistoryCommand.execute().feedbackToUser);
    }
}
```
###### /java/seedu/address/logic/commands/SignupCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class SignupCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }


    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SignupCommand(null, "123");
        new SignupCommand("John", null);
    }

    @Test
    public void execute_accountRegistered_addSuccessful() throws Exception {
        String inputUsername = "John";
        String inputPassword = "123";

        CommandResult commandResult = getSignupCommand(inputUsername, inputPassword, model).execute();

        assertEquals(SignupCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(Arrays.asList(new Account(inputUsername, inputPassword)),
                model.getAccountsManager().getAccountList());
    }

    @Test
    public void execute_duplicateAccount_throwsCommandException() throws Exception {
        String inputUsername = "John";
        String inputPassword = "123";

        thrown.expect(CommandException.class);

        //thrown.expectMessage(SignupCommand.MESSAGE_FAILURE);
        thrown.expectMessage(String.format(SignupCommand.MESSAGE_FAILURE,
                                            SignupCommand.MESSAGE_DUPLICATE_USERNAME));

        getSignupCommand(inputUsername, inputPassword, model).execute();
        getSignupCommand(inputUsername, inputPassword, model).execute();
    }

    @Test
    public void equals() {
        String inputUsername1 = "John";
        String inputPassword1 = "123";
        String inputUsername2 = "Jane";
        String inputPassword2 = "456";

        SignupCommand signupCommand1 = new SignupCommand(inputUsername1, inputPassword1);
        SignupCommand signupCommand2 = new SignupCommand(inputUsername2, inputPassword2);

        // same object -> returns true
        assertTrue(signupCommand1.equals(signupCommand1));

        // same values -> returns true
        SignupCommand signupCommand1Copy = new SignupCommand(inputUsername1, inputPassword1);
        assertTrue(signupCommand1.equals(signupCommand1Copy));

        // different types -> returns false
        assertFalse(signupCommand1.equals(1));

        // null -> returns false
        assertFalse(signupCommand1.equals(null));

        // different person -> returns false
        assertFalse(signupCommand1.equals(signupCommand2));
    }

    /**
     * Generates a new SignupCommand with the details of the given person.
     */
    private SignupCommand getSignupCommand(String username, String password,  Model model) throws Exception {
        SignupCommand command = new SignupCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### /java/seedu/address/logic/parser/SignupCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_USER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_USER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SignupCommand;


public class SignupCommandParserTest {
    private SignupCommandParser parser = new SignupCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER
                + PASSWORD_DESC_USER, new SignupCommand("John", "1234"));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignupCommand.MESSAGE_USAGE);

        // Missing username prefix.
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_USERNAME_USER
                + PASSWORD_DESC_USER, expectedMessage);

        assertParseFailure(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER
                + VALID_PASSWORD_USER, expectedMessage);

        // Missing username
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PREFIX_USERNAME + PASSWORD_DESC_USER, expectedMessage);

        // Missing password
        assertParseFailure(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER, expectedMessage);
    }

}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_login() throws Exception {
        String username = "John";
        String password = "123";
        LoginCommand command = (LoginCommand) parser.parseCommand(LoginCommand.COMMAND_WORD
                + " u/" + username + " pw/" + password);
        assertEquals(new LoginCommand(username, password), command);
    }

    @Test
    public void parseCommand_logout() throws Exception {
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD) instanceof LogoutCommand);
        assertTrue(parser.parseCommand(LogoutCommand.COMMAND_WORD + " 3") instanceof LogoutCommand);
    }

```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_signupCommandWord_returnsSignupCommand() throws Exception {
        String testUsername = "test";
        String testPassword = "123";
        SignupCommand command = (SignupCommand) parser.parseCommand(
                SignupCommand.COMMAND_WORD + " u/" + testUsername + " pw/" + testPassword);
        assertEquals(new SignupCommand(testUsername, testPassword), command);
    }

```
###### /java/seedu/address/logic/parser/LoginCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_USER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_USER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

public class LoginCommandParserTest {

    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER
                + PASSWORD_DESC_USER, new LoginCommand("John", "1234"));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE);

        // Missing username prefix.
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_USERNAME_USER
                + PASSWORD_DESC_USER, expectedMessage);

        assertParseFailure(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER
                + VALID_PASSWORD_USER, expectedMessage);

        // Missing username
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PREFIX_USERNAME + PASSWORD_DESC_USER, expectedMessage);

        // Missing password
        assertParseFailure(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER, expectedMessage);
    }

}
```
###### /java/seedu/address/model/AccountsManagerTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountsManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        AccountsManager accountsManager = new AccountsManager();
        thrown.expect(UnsupportedOperationException.class);
        accountsManager.getAccountList().remove(0);
    }
}
```
