# Jason1im
###### \data\XmlAccountDataStorageTest\invalidAccountData.xml
``` xml
<account>
    <!-- invalid username -->
    <username>...</username>
    <passqord>doe123</passqord>
</account>
```
###### \java\seedu\address\logic\commands\ClearHistoryCommandTest.java
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
###### \java\seedu\address\logic\commands\LoginCommandTest.java
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
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private String username = "Admin";
    private String password = "ad123";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Account());
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
###### \java\seedu\address\logic\commands\LogoutCommandTest.java
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
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Account());
        LoginCommand login = new LoginCommand("Admin", "ad123");
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
###### \java\seedu\address\logic\commands\UpdatePasswordCommandTest.java
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
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UpdatePasswordCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private String username = "Admin";
    private String password = "ad123";


    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new Account());
        LoginCommand command = new LoginCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }


    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdatePasswordCommand(null, "123");
        new UpdatePasswordCommand(password, null);
    }

    @Test
    public void execute_updateSuccessful() throws Exception {
        CommandResult commandResult = getUpdatePasswordCommand(password, "abc123", model).execute();
        assertEquals(String.format(UpdatePasswordCommand.MESSAGE_SUCCESS, password),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_invalidPassword_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PASSWORD);
        UpdatePasswordCommand command = getUpdatePasswordCommand("asd", "4de546", model);
        command.execute();
    }

    @Test
    public void execute_badPassword_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("Bad password. " + Account.MESSAGE_PASSWORD_CONSTRAINTS);
        UpdatePasswordCommand command = getUpdatePasswordCommand("ad123", "...", model);
        command.execute();
    }

    public UpdatePasswordCommand getUpdatePasswordCommand(String oldPassword, String newPassword, Model model) {
        UpdatePasswordCommand command = new UpdatePasswordCommand(oldPassword, newPassword);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\commands\UpdateUsernameCommandTest.java
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
import seedu.address.model.Account;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UpdateUsernameCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private String username = "Admin";
    private String password = "ad123";


    @Before
    public void setUp() throws Exception {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(),
                new Account(username, password));
        LoginCommand command = new LoginCommand(username, password);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        command.execute();
    }


    @Test
    public void constructor_nullValues_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new UpdateUsernameCommand(null);
    }

    @Test
    public void execute_updateSuccessful() throws Exception {
        CommandResult commandResult = getUpdateUsernameCommand("John", model).execute();
        assertEquals(String.format(UpdateUsernameCommand.MESSAGE_SUCCESS, "John"),
                commandResult.feedbackToUser);
    }

    @Test
    public void execute_badUsername_throwsCommandException() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage("Bad username. " + Account.MESSAGE_USERNAME_CONSTRAINTS);
        UpdateUsernameCommand command = getUpdateUsernameCommand(".a.", model);
        command.execute();
    }

    public UpdateUsernameCommand getUpdateUsernameCommand(String newUsername, Model model) {
        UpdateUsernameCommand command = new UpdateUsernameCommand(newUsername);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
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
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_updatePasswordCommandWord_returnsUpdatePasswordCommand() throws Exception {
        String testPassword1 = "test";
        String testPassword2 = "123";
        UpdatePasswordCommand command = (UpdatePasswordCommand) parser.parseCommand(
                UpdatePasswordCommand.COMMAND_WORD + " pw/" + testPassword1
                            + " npw/" + testPassword2);
        assertEquals(new UpdatePasswordCommand(testPassword1, testPassword2), command);
    }

```
###### \java\seedu\address\logic\parser\LoginCommandParserTest.java
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
                + PASSWORD_DESC_USER, new LoginCommand(VALID_USERNAME_USER, VALID_PASSWORD_USER));
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
###### \java\seedu\address\logic\parser\UpdatePasswordParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PASSWORD_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_USER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UpdatePasswordCommand;

public class UpdatePasswordParserTest {
    private UpdatePasswordCommandParser parser = new UpdatePasswordCommandParser();
    private String newPassword = "ang123";

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + PASSWORD_DESC_USER
                + " " + PREFIX_NEW_PASSWORD + newPassword,
                new UpdatePasswordCommand(VALID_PASSWORD_USER, newPassword));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdatePasswordCommand.MESSAGE_USAGE);

        //Missing prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PASSWORD_DESC_USER
                + newPassword, expectedMessage);

        // Missing input
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PASSWORD_DESC_USER
                + PREFIX_NEW_PASSWORD, expectedMessage);

    }
}
```
###### \java\seedu\address\logic\parser\UpdateUsernameParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.USERNAME_DESC_USER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_USER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UpdateUsernameCommand;

public class UpdateUsernameParserTest {
    private UpdateUsernameCommandParser parser = new UpdateUsernameCommandParser();
    private String newUsername = "John";

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + USERNAME_DESC_USER,
                new UpdateUsernameCommand(VALID_USERNAME_USER));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateUsernameCommand.MESSAGE_USAGE);

        //Missing prefix
        assertParseFailure(parser, PREAMBLE_WHITESPACE + VALID_USERNAME_USER, expectedMessage);

        // Missing input
        assertParseFailure(parser, PREAMBLE_WHITESPACE + PREFIX_USERNAME, expectedMessage);
    }
}
```
###### \java\seedu\address\storage\XmlAccountDataStorageTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Account;

public class XmlAccountDataStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlAccountDataStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAccountData_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAccountData(null);
    }

    private java.util.Optional<Account> readAccountData(String filePath) throws Exception {
        return new XmlAccountDataStorage(filePath).readAccountData(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAccountData("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAccountData("NotXmlFormatAccountData.xml");
    }

    @Test
    public void readAccountData_invalidAccountData_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAccountData("invalidAccountData.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAccountData.xml";
        Account original = new Account();
        XmlAccountDataStorage xmlAccountDataStorage = new XmlAccountDataStorage(filePath);

        //Save in new file and read back
        xmlAccountDataStorage.saveAccountData(original, filePath);
        Account readBack = xmlAccountDataStorage.readAccountData(filePath).get();
        assertEquals(original, readBack);

        //Modify data, overwrite exiting file, and read back
        original.updateUsername("Bill");
        original.updatePassword("gan123");
        xmlAccountDataStorage.saveAccountData(original, filePath);
        readBack = xmlAccountDataStorage.readAccountData(filePath).get();
        assertEquals(original, readBack);

        //Save and read without specifying file path
        original.updatePassword("boy123");
        xmlAccountDataStorage.saveAccountData(original); //file path not specified
        readBack = xmlAccountDataStorage.readAccountData().get(); //file path not specified
        assertEquals(original, readBack);

    }

    @Test
    public void saveAccountData_nullAccountData_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAccountData(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAccountData(Account account, String filePath) {
        try {
            new XmlAccountDataStorage(filePath).saveAccountData(account, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAccountData_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveAccountData(new Account(), null);
    }


}
```
###### \java\seedu\address\storage\XmlAdapatedAccountTest.java
``` java
package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Account;
import seedu.address.testutil.Assert;

public class XmlAdapatedAccountTest {
    private static final String INVALID_USERNAME = "::";
    private static final String INVALID_PASSWORD = "-a-";

    private static final String VALID_USERNAME = "John";
    private static final String VALID_PASSWORD = "doe123";

    @Test
    public void toModelType_validAccount_returnsAccount() throws Exception {
        Account account = new Account(VALID_USERNAME, VALID_PASSWORD);
        XmlAdaptedAccount xmlAccount = new XmlAdaptedAccount(account);
        assertEquals(account, xmlAccount.toModelType());
    }

    @Test
    public void toModelType_invalidUserName_throwsIllegalValueException() {
        XmlAdaptedAccount account = new XmlAdaptedAccount(INVALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = Account.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        XmlAdaptedAccount account = new XmlAdaptedAccount(VALID_USERNAME, INVALID_PASSWORD);
        String expectedMessage = Account.MESSAGE_PASSWORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, account::toModelType);
    }
}
```
