# cxingkai
###### \java\seedu\address\logic\commands\LoginCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoginCommandTest {

    @Test
    public void equals() {
        LoginCommand loginCommand = new LoginCommand();
        LoginCommand loginCommandCopy = new LoginCommand();

        assertTrue(loginCommand.equals(loginCommandCopy));
    }
}
```
###### \java\seedu\address\logic\commands\LogoutCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.login.LoginManager;

public class LogoutCommandTest {

    @Test
    public void execute_alreadyLoggedIn_successfulLogout() {
        LoginManager.authenticate("alice", "password123");

        LogoutCommand logoutCommand = new LogoutCommand();
        logoutCommand.execute();

        assertEquals(LoginManager.getUserState(), LoginManager.NO_USER_STATE);
    }

    @Test
    public void equals() {
        LogoutCommand logoutCommand = new LogoutCommand();
        LogoutCommand logoutCommandCopy = new LogoutCommand();

        assertTrue(logoutCommand.equals(logoutCommandCopy));
    }

}
```
###### \java\seedu\address\logic\commands\PrintCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.core.index.Index;

public class PrintCommandTest {
    @Test
    public void equals() {
        Index testIndex = Index.fromOneBased(1);
        PrintCommand printCommand = new PrintCommand(testIndex);
        PrintCommand printCommandCopy = new PrintCommand(testIndex);

        assertTrue(printCommand.equals(printCommandCopy));
    }
}
```
###### \java\seedu\address\logic\LoginManagerTest.java
``` java
package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.commands.LoginCommand.FAKE_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.TEST_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.TEST_USERNAME;

import org.junit.Test;

import seedu.address.logic.login.LoginManager;

public class LoginManagerTest {

    @Test
    public void authenticate_correctPassword_returnTrue() {
        LoginManager.logout();
        boolean expectSuccess = LoginManager.authenticate(TEST_USERNAME, TEST_PASSWORD);
        int loginState = LoginManager.getUserState();

        assertEquals(expectSuccess, true);
        assertEquals(loginState, LoginManager.DOCTOR_LOGIN);
    }

    @Test
    public void authenticate_wrongPassword_returnFalse() {
        LoginManager.logout();
        boolean expectFail = LoginManager.authenticate(TEST_USERNAME, FAKE_PASSWORD);
        int loginState = LoginManager.getUserState();

        assertEquals(expectFail, false);
        System.out.println(loginState);
        assertEquals(loginState, LoginManager.NO_USER_STATE);
    }
}
```
###### \java\seedu\address\logic\parser\LoginCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.LoginCommand.TEST_PASSWORD;
import static seedu.address.logic.commands.LoginCommand.TEST_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.LoginCommand;

public class LoginCommandParserTest {
    private LoginCommandParser parser = new LoginCommandParser();

    @Test
    public void parse_emptyArg_returnsLoginCommand() {
        LoginCommand expectedLoginCommand = new LoginCommand();
        assertParseSuccess(parser, "", expectedLoginCommand);
        assertParseSuccess(parser, "      ", expectedLoginCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, TEST_USERNAME, String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        assertParseFailure(parser, TEST_USERNAME + " " + TEST_PASSWORD + " " + TEST_USERNAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\PrintCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.PrintCommand;

public class PrintCommandParserTest {
    private PrintCommandParser parser = new PrintCommandParser();

    @Test
    public void parse_emptyArg_returnsLoginCommand() {
        int testInput = 1;
        Index testIndex = Index.fromOneBased(testInput);
        PrintCommand expectedPrintCommand = new PrintCommand(testIndex);

        assertParseSuccess(parser, Integer.toString(testInput), expectedPrintCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PrintCommand.MESSAGE_USAGE));
    }
}
```
