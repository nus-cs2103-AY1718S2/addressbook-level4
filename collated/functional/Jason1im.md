# Jason1im
###### \java\seedu\address\commons\events\model\AccountUpdateEvent.java
``` java
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.Account;

/**
 * Indicates the user account has changed.
 */
public class AccountUpdateEvent extends BaseEvent {
    public final Account data;

    public AccountUpdateEvent(Account data) {
        this.data = data;
    }

    public String toString() {
        return "username is " + data.getUsername() + " and password is " + data.getPassword();
    }
}
```
###### \java\seedu\address\commons\events\ui\LoginEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to switch between login view and main app view.
 */
public class LoginEvent extends BaseEvent {
    public final boolean isLogin;

    public LoginEvent(boolean value) {
        this.isLogin = value;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\ClearHistoryCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;

/**
 * Clears the history of user input commands.
 */
public class ClearHistoryCommand extends Command {

    public static final String COMMAND_WORD = "clearhistory";
    public static final String MESSAGE_SUCCESS = "Your history has been cleared.";

    @Override
    public CommandResult execute() {
        history.clearHistory();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        requireNonNull(history);
        this.history = history;
    }
}
```
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;

import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.exception.InvalidPasswordException;
import seedu.address.model.exception.InvalidUsernameException;
import seedu.address.model.exception.MultipleLoginException;

/**
* Logs the user into contactHeRo.
*/
public class LoginCommand extends Command {
    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
                + PREFIX_USERNAME + " "
                + PREFIX_PASSWORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Logs the user into contactHero. \n"
                + "Parameters: "
                + PREFIX_USERNAME + "USERNAME "
                + PREFIX_PASSWORD + "PASSWORD \n"
                + "Example: " + COMMAND_WORD + " "
                + PREFIX_USERNAME + "JohnDoe "
                + PREFIX_PASSWORD + "98765432 ";
    public static final String MESSAGE_SUCCESS = "You have successfully logged in as %1$s";
    public static final String MESSAGE_MULTIPLE_LOGIN = "You are already logged in.";

    private final String username;
    private final String password;

    public LoginCommand(String inputUsername, String inputPassword) {
        requireAllNonNull(inputUsername, inputPassword);
        this.username = inputUsername;
        this.password = inputPassword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.login(username, password);
            EventsCenter.getInstance().post(new LoginEvent(true));
            return new CommandResult(String.format(MESSAGE_SUCCESS, username));
        } catch (InvalidUsernameException iue) {
            throw new CommandException(Messages.MESSAGE_INVALID_USERNAME);
        } catch (InvalidPasswordException ipe) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD);
        } catch (MultipleLoginException mle) {
            throw new CommandException(MESSAGE_MULTIPLE_LOGIN);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand) // instanceof handles nulls
                && this.username.equals(((LoginCommand) other).username)
                && password.equals(((LoginCommand) other).password);
    }

}
```
###### \java\seedu\address\logic\commands\LogoutCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoginEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.UserLogoutException;

/**
* Logs the user out of contactHeRo.
*/
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "You have logged out successfully!";
    public static final String MESSAGE_MULTIPLE_LOGOUT = "You have already logged out.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.logout();
            EventsCenter.getInstance().post(new LoginEvent(false));
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UserLogoutException ule) {
            throw new CommandException(MESSAGE_MULTIPLE_LOGOUT);
        }
    }
}
```
###### \java\seedu\address\logic\commands\UpdatePasswordCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.BadDataException;
import seedu.address.model.exception.InvalidPasswordException;

/**
 * Change the password of the user account.
 */
public class UpdatePasswordCommand extends Command {
    public static final String COMMAND_WORD = "updatepassword";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_PASSWORD + " "
            + PREFIX_NEW_PASSWORD;
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Updates the user account password. \n"
            + "Parameters: "
            + PREFIX_PASSWORD + "PASSWORD "
            + PREFIX_NEW_PASSWORD + "NEW_PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PASSWORD + "Doe123 "
            + PREFIX_NEW_PASSWORD + "doe456";
    public static final String MESSAGE_SUCCESS = "You have successfully updated your password!";

    private final String oldPassword;
    private final String newPassword;

    public UpdatePasswordCommand(String oldPassword, String newPassword) {
        requireAllNonNull(oldPassword, newPassword);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.updatePassword(oldPassword, newPassword);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (InvalidPasswordException ipe) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD);
        } catch (BadDataException bde) {
            throw new CommandException(bde.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpdatePasswordCommand) // instanceof handles nulls
                && this.oldPassword.equals(((UpdatePasswordCommand) other).oldPassword)
                && this.newPassword.equals(((UpdatePasswordCommand) other).newPassword);
    }
}
```
###### \java\seedu\address\logic\commands\UpdateUsernameCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.BadDataException;

/**
 * Change the username of the user account.
 */
public class UpdateUsernameCommand extends Command {

    public static final String COMMAND_WORD = "updateusername";
    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
               + PREFIX_USERNAME;
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Updates the username.\n"
            + "Parameters: "
            + PREFIX_USERNAME + "NEW_USERNAME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "John";
    public static final String MESSAGE_SUCCESS = "You have successfully changed your username to %1$s";

    private final String newUsername;

    public UpdateUsernameCommand( String newUsername) {
        requireNonNull( newUsername);
        this.newUsername = newUsername;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.updateUsername(newUsername);
            return new CommandResult(String.format(MESSAGE_SUCCESS, newUsername));
        }  catch (BadDataException bde) {
            throw new CommandException(bde.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.UpdateUsernameCommand) // instanceof handles nulls
                && this.newUsername.equals(((seedu.address.logic.commands.UpdateUsernameCommand) other).newUsername);
    }

}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    /**
     * Executes the command if it can used before logging in
     * @throws CommandException if the command is restricted.
     */
    private CommandResult execute(Command command) throws CommandException {
        if (model.isLoggedIn() || isUnrestrictedCommand(command)) {
            return command.execute();
        } else {
            throw new CommandException(Messages.MESSAGE_RESTRICTED_COMMMAND);
        }
    }

    /**
     * Checks if the command can be executed before logging in.
     */
    private boolean isUnrestrictedCommand(Command command) {
        return command instanceof LoginCommand || command instanceof HelpCommand
                || command instanceof ExitCommand || command instanceof ClearHistoryCommand;
    }

```
###### \java\seedu\address\logic\parser\LoginCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.LoginCommand;

import seedu.address.logic.parser.exceptions.ParseException;

/**
* Parses input arguments and creates a new LoginCommand object
*/
public class LoginCommandParser implements Parser<LoginCommand> {
    /**
    * * Parses the given {@code String} of arguments in the context of the LoginCommand
    * *  and returns an LoginCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        } else {
            String inputUsername = argMultimap.getValue(PREFIX_USERNAME).get();
            String inputPassword = argMultimap.getValue(PREFIX_PASSWORD).get();
            return new LoginCommand(inputUsername, inputPassword);
        }
    }

    /**
    * Returns true if none of the prefixes contains empty {@code Optional} values in the given
    * {@code ArgumentMultimap}.
    */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\UpdatePasswordCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.stream.Stream;

import seedu.address.logic.commands.UpdatePasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdatePasswordCommand object
 */
public class UpdatePasswordCommandParser implements Parser<UpdatePasswordCommand> {
    /**
     * * Parses the given {@code String} of arguments in the context of the UpdatePasswordCommand
     * *  and returns an UpdatePasswordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdatePasswordCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PASSWORD, PREFIX_NEW_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_PASSWORD, PREFIX_NEW_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdatePasswordCommand.MESSAGE_USAGE));
        } else {
            String inputPassword1 = argMultimap.getValue(PREFIX_PASSWORD).get();
            String inputPassword2 = argMultimap.getValue(PREFIX_NEW_PASSWORD).get();
            return new UpdatePasswordCommand(inputPassword1, inputPassword2);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\logic\parser\UpdateUsernameCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateUsernameCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UpdateUsernameCommand object
 */
public class UpdateUsernameCommandParser implements Parser<UpdateUsernameCommand> {
    /**
     * * Parses the given {@code String} of arguments in the context of the LoginCommand
     * *  and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateUsernameCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateUsernameCommand.MESSAGE_USAGE));
        } else {
            String inputUsername = argMultimap.getValue(PREFIX_USERNAME).get();
            return new UpdateUsernameCommand(inputUsername);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
```
###### \java\seedu\address\model\Account.java
``` java
package seedu.address.model;

import seedu.address.model.exception.BadDataException;

/**
 * Represents a user account.
 */
public final class Account {

    public static final String MESSAGE_USERNAME_CONSTRAINTS = "Username should be alphanumeric,"
            + " 3-20 characters long and should not contain any white space.";
    public static final String MESSAGE_PASSWORD_CONSTRAINTS = "Password should start with a "
            + "alphanumeric character and around 5-20 characters long.\n It should contain at "
            + "least 1 digit, 1 alphabet and not contain any white space.";

    public static final String USERNAME_VALIDATION_REGEX = "\\S\\w{3,20}";

    public static final String PASSWORD_VALIDATION_REGEX = "(?=.*[A-Za-z])(?=.*\\d)[\\p{Alnum}](\\S{4,20})";

    private static final String DEFAULT_USERNAME = "Admin";
    private static final String DEFAULT_PASSWORD = "ad123";

    private String username;
    private String password;

    public Account() {
        this.username = DEFAULT_USERNAME;
        this.password = DEFAULT_PASSWORD;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @param newUsername should not be null
     */
    public void updateUsername(String newUsername) throws BadDataException {
        if (isValidUsername(newUsername)) {
            username = newUsername;
        } else {
            throw new BadDataException("Bad username. " + MESSAGE_USERNAME_CONSTRAINTS);
        }
    }

    /**
     * @param newPassword should not be null
     */
    public void updatePassword(String newPassword) throws BadDataException {
        if (isValidPassword(newPassword)) {
            password = newPassword;
        } else {
            throw new BadDataException("Bad password. " + MESSAGE_PASSWORD_CONSTRAINTS);
        }
    }

    public static boolean isValidUsername(String username) {
        return username.matches(USERNAME_VALIDATION_REGEX);
    }

    public static boolean isValidPassword(String password) {
        return password.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Account)) {
            return false;
        }
        Account otherAccount = (Account) other;
        return otherAccount.getUsername().equals(this.getUsername())
                && otherAccount.getPassword().equals(this.getPassword());
    }

}
```
###### \java\seedu\address\model\AccountsManager.java
``` java
package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.AccountUpdateEvent;
import seedu.address.model.exception.BadDataException;
import seedu.address.model.exception.InvalidPasswordException;
import seedu.address.model.exception.InvalidUsernameException;


/**
 * Represents a database of registered user accounts
 */
public class AccountsManager {
    private final Account account;


    public AccountsManager() {
        account = new Account();
    }

    public AccountsManager(Account account) { this.account = account; }

    private boolean checkUsername(String username, Account account) {
        return account.getUsername().equals(username);
    }

    private boolean checkPassword(String password, Account account) {
        return account.getPassword().equals(password);
    }

    /** Raises an event to indicate the account has changed */
    private void indicateAccountUpdated() {
        EventsCenter.getInstance().post(new AccountUpdateEvent(account));
    }

    /**
     * Updates the username of the account.
     * @throws InvalidUsernameException if the username is already in use
     */
    public void updateUsername(String inputUsername) throws BadDataException {
        requireNonNull(inputUsername);
        account.updateUsername(inputUsername);
        indicateAccountUpdated();
    }

    /**
     * Updates the password of the account.
     * @throws InvalidPasswordException
     */
    public void updatePassword(String oldPassword, String newPassword)
            throws InvalidPasswordException, BadDataException {
        requireAllNonNull(oldPassword, newPassword);
        if (!checkPassword(oldPassword, account)) {
            throw new InvalidPasswordException();
        } else {
            account.updatePassword(newPassword);
            indicateAccountUpdated();
        }
    }

    /**
     * Checks for validity of username and password.
     * @return the user account that matches the inputs.
     * @throws InvalidUsernameException if the input username cannot be found
     * @throws InvalidPasswordException if the input password does not match with the username
     */
    public Account login(String inputUsername, String inputPassword)
            throws InvalidUsernameException, InvalidPasswordException {

        requireAllNonNull(inputUsername, inputPassword);
        if (!checkUsername(inputUsername, account)) {
            throw new InvalidUsernameException();
        } else if (!checkPassword(inputPassword, account)) {
            throw new InvalidPasswordException();
        } else {
            return account;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountsManager // instanceof handles nulls
                && this.account.equals(((AccountsManager) other).account));
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}
```
###### \java\seedu\address\model\exception\BadDataException.java
``` java
package seedu.address.model.exception;

/**
 * Signals the error cause by bad input username or password
 * which do not meet their respective contraint.
 */
public class BadDataException extends Exception {
    public BadDataException(String message) {
        super(message);
    }
}
```
###### \java\seedu\address\model\exception\DuplicateUsernameException.java
``` java
package seedu.address.model.exception;

/**
 * Signals an error whereby a username is already being used.
 */
public class DuplicateUsernameException extends Exception {}
```
###### \java\seedu\address\model\exception\InvalidPasswordException.java
``` java
package seedu.address.model.exception;

/**
 * Signals the error caused by invalid password
 */
public class InvalidPasswordException extends Exception {}
```
###### \java\seedu\address\model\exception\InvalidUsernameException.java
``` java
package seedu.address.model.exception;

/**
 * Signals an error whereby the username is not found.
 */
public class InvalidUsernameException extends Exception {}
```
###### \java\seedu\address\model\exception\MultipleLoginException.java
``` java
package seedu.address.model.exception;

/**
 * Signals an error whereby a user has already been login.
 */
public class MultipleLoginException extends Exception {}
```
###### \java\seedu\address\model\exception\UserLogoutException.java
``` java
package seedu.address.model.exception;

/**
 * Signals an error whereby there is no account to logout from.
 */
public class UserLogoutException extends Exception {}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Logs the user into contactHeRo.
     * @throws InvalidUsernameException if username is invalid.
     * @throws InvalidPasswordException if the password is invalid.
     * @throws MultipleLoginException if a user is already logged in.
     */
    void login(String username, String password) throws InvalidUsernameException,
            InvalidPasswordException, MultipleLoginException;

    /**
     * Logs the user out of contactHeRo
     * @throws UserLogoutException if no user is login to the system.
     */
    void logout() throws UserLogoutException;

    /**
     * Changes the password of the user account
     * @param oldPassword
     * @param newPassword
     * @throws InvalidPasswordException if password is invalid.
     * @throws BadDataException if password does not meet contraints.
     */
    void updatePassword(String oldPassword, String newPassword)
            throws InvalidPasswordException, BadDataException;

    /**
     * Changes the username of the user account.
     * @throws BadDataException is username does not meet contraints.
     */
    void updateUsername(String oldUsername) throws BadDataException;

    /**
     * Checks if the user has logged in.
     * @return true if user is logged in.
     */
    boolean isLoggedIn();

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Logs the user into the system.
     * @throws InvalidUsernameException
     * @throws InvalidPasswordException
     * @throws MultipleLoginException if a user is already logged in.
     */
    @Override
    public void login(String username, String password)
            throws InvalidUsernameException, InvalidPasswordException, MultipleLoginException {
        if (user.isPresent()) {
            throw new MultipleLoginException();
        } else {
            requireNonNull(accountsManager);
            Account user = accountsManager.login(username, password);
            setUser(user);
        }
    }

    /**
     * Logs the user out of the system.
     * @throws UserLogoutException
     */
    @Override
    public void logout() throws UserLogoutException {
        if (user.isPresent()) {
            setUser(null);
        } else {
            throw new UserLogoutException();
        }
    }

    @Override
    public void updateUsername(String newUsername) throws BadDataException {
        accountsManager.updateUsername(newUsername);
    }

    @Override
    public void updatePassword(String oldPassword, String newPassword)
        throws InvalidPasswordException, BadDataException {
        accountsManager.updatePassword(oldPassword, newPassword);
    }

    private void setUser(Account account) {
        user = user.ofNullable(account);
    }

    @Override
    public boolean isLoggedIn() { return user.isPresent(); }

```
###### \java\seedu\address\storage\AccountDataStorage.java
``` java
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Account;

/**
 * Represents a storage for {@link seedu.address.model.Account}.
 */
public interface AccountDataStorage {

    /**
     * Returns the account data stored in the hard disk.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Account> readAccountData() throws DataConversionException, IOException;

    /**
     * @see #readAccountData()
     */
    Optional<Account> readAccountData(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link Account} to the storage.
     * @param accountData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccountData(Account accountData) throws IOException;

    /**
     * @see #saveAccountData(Account)
     */
    void saveAccountData(Account accountData, String filePath) throws IOException;

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    // ================ AccountData methods ==============================

    @Override
    public Optional<Account> readAccountData() throws DataConversionException, IOException {
        return accountDataStorage.readAccountData();
    }

    @Override
    public Optional<Account> readAccountData(String filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return accountDataStorage.readAccountData(filePath);
    }

    @Override
    public void saveAccountData(Account account) throws IOException {
        accountDataStorage.saveAccountData(account);
    }

    @Override
    public void saveAccountData(Account account, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        accountDataStorage.saveAccountData(account);
    }

```
###### \java\seedu\address\storage\XmlAccountDataStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.Account;

/**
 * A class to access the account data stored as an xml file on the hard disk.
 */
public class XmlAccountDataStorage implements AccountDataStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlAddressBookStorage.class);

    private String filePath;

    public XmlAccountDataStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Optional<Account> readAccountData() throws DataConversionException, IOException {
        return readAccountData(filePath);
    }

    /**
     * Similar to {@link #readAccountData()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<Account> readAccountData(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File accountDataFile = new File(filePath);

        if (!accountDataFile.exists()) {
            logger.info("AccountData file "  + accountDataFile + " not found");
            return Optional.empty();
        }

        XmlAdaptedAccount xmlAccountData =  XmlFileStorage.loadAccountDataFromFile(new File(filePath));
        try {
            return Optional.of(xmlAccountData.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + accountDataFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAccountData(Account accountData) throws IOException {
        saveAccountData(accountData, filePath);
    }

    /**
     * Similar to {@link #saveAccountData(Account)}
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveAccountData(Account accountData, String filePath) throws IOException {
        requireNonNull(accountData);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlAdaptedAccount(accountData));
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedAccount.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Account;

/**
 * JAXB-friendly version of the Account.
 */
@XmlRootElement(name = "accountData")
public class XmlAdaptedAccount {

    @XmlAttribute(name = "username")
    private String username;
    @XmlAttribute(name = "password")
    private String password;

    /**
     * Constructs an XmlAdaptedAccount.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAccount() {}

    /**
     * Constructs an {@code XmlAdaptedAccount} with the given details.
     */
    public XmlAdaptedAccount(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given Account into this class for JAXB use.
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedAccount(Account source) {
        username = source.getUsername();
        password = source.getPassword();
    }

    /**
     * Converts this jaxb-friendly adapted skill object into the model's Account object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Account toModelType() throws IllegalValueException {
        if (!Account.isValidUsername(username)) {
            throw new IllegalValueException(Account.MESSAGE_USERNAME_CONSTRAINTS);
        } else if (!Account.isValidPassword(password)) {
            throw new IllegalValueException(Account.MESSAGE_PASSWORD_CONSTRAINTS);
        } else {
            return new Account(username, password);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAccount)) {
            return false;
        }

        return username.equals(((XmlAdaptedAccount) other).username)
                && password.equals(((XmlAdaptedAccount) other).password);
    }
}
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    /**
     * Saves the given account data to the specified file.
     */
    public static void saveDataToFile(File file, XmlAdaptedAccount xmlAccountData)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, xmlAccountData);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

```
###### \java\seedu\address\ui\DisplayPanel.java
``` java
package seedu.address.ui;

import javafx.scene.layout.Region;

/**
 * A display page for login window.
 */
public class DisplayPanel extends UiPart<Region> {

    private static final String FXML = "DisplayPanel.fxml";

    public DisplayPanel() {
        super(FXML);
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Initialize all the Panels in Main Window
     */
    void init() {
        detailsPanel = new DetailsPanel();
        detailsPanel.addBrowserPanel();
        detailsPanel.addContactDetailsDisplayPanel();
        detailsPanel.addCalendarPanel(logic.getAppointmentList());
        detailsPanel.addEmailPanel();
        detailsPanel.addGoogleLoginPanel();

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());

        jobListPanel = new JobListPanel(logic.getFilteredJobList());

        displayPanel = new DisplayPanel();

        statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());

        resultDisplay = new ResultDisplay();

        commandBox = new CommandBox(logic);

    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Switches the fxml file loaded onto primaryStage
     */
    private void switchView(boolean isLogin) {
        if (isLogin) {
            loadFxmlFile(getFxmlFileUrl(FXML), primaryStage);
            fillInnerParts(true);
        } else {
            loadFxmlFile(getFxmlFileUrl(FXML_0), primaryStage);
            fillInnerParts(false);
        }
    }

    @Subscribe
    private void handleLoginEvent(LoginEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switchView(event.isLogin);
    }

```
###### \resources\view\DisplayPanel.fxml
``` fxml

<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.text.Font?>
<?import javafx.scene.text.Text?>


<GridPane xmlns="http://javafx.com/javafx/9" xmlns:fx="http://javafx.com/fxml/1">
   <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" />
   </columnConstraints>
   <rowConstraints>
      <RowConstraints minHeight="10.0" vgrow="SOMETIMES" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
   </rowConstraints>
   <children>
         <Text fill="#f9f9f9" strokeType="OUTSIDE" strokeWidth="0.0" text="Welcome to contactHeRo" textAlignment="CENTER" GridPane.halignment="CENTER" GridPane.rowIndex="1" GridPane.valignment="CENTER">
            <font>
               <Font size="27.0" />
            </font>
         </Text>
         <ImageView fitHeight="300.0" fitWidth="400.0" pickOnBounds="true" preserveRatio="true" GridPane.halignment="CENTER" GridPane.valignment="CENTER">
            <image>
               <Image url="@../images/displayscreenimage.png" />
            </image>
      </ImageView>
   </children>
</GridPane>
```
###### \resources\view\LoginWindow.fxml
``` fxml
                <StackPane fx:id="displayPanelPlaceholder" styleClass="pane-with-border" VBox.vgrow="ALWAYS">
                    <padding>
                        <Insets bottom="10" left="10" right="10" top="10" />
                    </padding>
                </StackPane>

```
