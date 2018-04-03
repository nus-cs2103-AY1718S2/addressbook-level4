# Jason1im
###### /java/seedu/address/logic/commands/ClearHistoryCommand.java
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
###### /java/seedu/address/logic/commands/LogoutCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.UserLogoutException;

/**
* Logs the user out of contactHeRo.
*/
public class LogoutCommand extends Command {
    public static final String COMMAND_WORD = "logout";
    public static final String MESSAGE_SUCCESS = "You have logout successfully!";
    public static final String MESSAGE_MULTIPLE_LOGOUT = "You have already logout.";

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.logout();
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (UserLogoutException ule) {
            throw new CommandException(MESSAGE_MULTIPLE_LOGOUT);
        }
    }
}
```
###### /java/seedu/address/logic/commands/SignupCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.exception.DuplicateUsernameException;

/**
 * Register a new account for the user.
 */
public class SignupCommand  extends Command {
    public static final String COMMAND_WORD = "signup";

    public static final String COMMAND_SYNTAX = COMMAND_WORD + " "
            + PREFIX_USERNAME + " "
            + PREFIX_PASSWORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + "Creates a new user account."
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "John "
            + PREFIX_PASSWORD + "353535 ";
    public static final String MESSAGE_SUCCESS = "You have signup successfully!";
    public static final String MESSAGE_FAILURE = "Signup has failed.\n";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username has already been used.";

    public final String username;
    public final String password;

    public SignupCommand(String inputUsername, String inputPassword) {
        requireNonNull(inputUsername);
        requireNonNull(inputPassword);
        this.username = inputUsername;
        this.password = inputPassword;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.register(username, password);
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (DuplicateUsernameException due) {
            throw new CommandException(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE_USERNAME));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignupCommand // instanceof handles nulls
                && username.equals(((SignupCommand) other).username)
                && password.equals(((SignupCommand) other).password));
    }
}
```
###### /java/seedu/address/logic/commands/LoginCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.address.commons.core.Messages;

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
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Logs the user into contactHero."
                + "Parameters: "
                + PREFIX_USERNAME + "USERNAME "
                + PREFIX_PASSWORD + "PASSWORD\n"
                + "Example: " + COMMAND_WORD + " "
                + PREFIX_USERNAME + "JohnDoe "
                + PREFIX_PASSWORD + "98765432 ";
    public static final String MESSAGE_SUCCESS = "You have successfully login as %1$s";
    public static final String MESSAGE_MULTIPLE_LOGIN = "You have already login.";

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
###### /java/seedu/address/logic/parser/SignupCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.SignupCommand;

import seedu.address.logic.parser.exceptions.ParseException;




/**
 * Parses input arguments and creates a new SignupCommand object
 */
public class SignupCommandParser implements Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SignupCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignupCommand.MESSAGE_USAGE));
        } else {
            String inputUsername = argMultimap.getValue(PREFIX_USERNAME).get();
            String inputPassword = argMultimap.getValue(PREFIX_PASSWORD).get();
            return new SignupCommand(inputUsername, inputPassword);
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
###### /java/seedu/address/logic/parser/LoginCommandParser.java
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
###### /java/seedu/address/model/Account.java
``` java
package seedu.address.model;

/**
 * Represents a user account.
 */
public final class Account {
    private String username = "JohnDoe";
    private String password = "12345";

    /**
     * @param username should not be null
     * @param password should not be null
     */
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
    public void updateUsername(String newUsername) {
        username = newUsername;
    }

    /**
     * @param newPassword should not be null
     */
    public void updatePassword(String newPassword) {
        password = newPassword;
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
###### /java/seedu/address/model/AccountsManager.java
``` java
package seedu.address.model;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.exception.DuplicateUsernameException;
import seedu.address.model.exception.InvalidPasswordException;
import seedu.address.model.exception.InvalidUsernameException;


/**
 * Represents a database of registered user accounts
 */
public class AccountsManager implements ReadOnlyAccountsManager {
    private ObservableList<Account> accountList;


    public AccountsManager() {
        accountList = FXCollections.observableArrayList();
    }

    public boolean checkUsername(String username, Account account) {
        return account.getUsername().equals(username);
    }

    public boolean checkPassword(String password, Account account) {
        return account.getPassword().equals(password);
    }

    /**
    * adds a new account to the list of registered accounts.
    * @throws DuplicateUsernameException if the username is already used
    */
    public void register(String inputUsername, String inputPassword) throws DuplicateUsernameException {
        requireAllNonNull(inputUsername, inputPassword);
        if (!accountList.isEmpty()) {
            for (Account acc : accountList) {
                if (checkUsername(inputUsername, acc)) {
                    throw new DuplicateUsernameException();
                }
            }
        }
        Account newAccount = new Account(inputUsername, inputPassword);
        accountList.add(newAccount);
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
        Account result = null;
        boolean isValidUsername = false;
        for (Account acc : accountList) {
            if (checkUsername(inputUsername, acc)) {
                if (checkPassword(inputPassword, acc)) {
                    isValidUsername = true;
                    result = acc;
                } else {
                    throw new InvalidPasswordException();
                }
            }
        }
        if (isValidUsername) {
            return result;
        } else {
            throw new InvalidUsernameException();
        }
    }

    @Override
    public ObservableList<Account> getAccountList() {
        assert CollectionUtil.elementsAreUnique(accountList);
        return FXCollections.unmodifiableObservableList(accountList);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountsManager // instanceof handles nulls
                && this.accountList.equals(((AccountsManager) other).accountList));
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountList);
    }
}
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public ReadOnlyAccountsManager getAccountsManager() {
        return accountsManager;
    }

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
    public void register(String username, String password) throws DuplicateUsernameException {
        accountsManager.register(username, password);
    }

```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Returns AccountsManager.
     */
    ReadOnlyAccountsManager getAccountsManager();

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
     * Register a new account for user.
     * @throws DuplicateUsernameException if {@param username} is already in used.
     */
    void register(String username, String password) throws DuplicateUsernameException;

```
###### /java/seedu/address/model/exception/InvalidPasswordException.java
``` java
package seedu.address.model.exception;

/**
 * Signals the error caused by invalid password
 */
public class InvalidPasswordException extends Exception {}
```
###### /java/seedu/address/model/exception/UserLogoutException.java
``` java
package seedu.address.model.exception;

/**
 * Signals an error whereby there is no account to logout from.
 */
public class UserLogoutException extends Exception {}
```
###### /java/seedu/address/model/exception/MultipleLoginException.java
``` java
package seedu.address.model.exception;

/**
 * Signals an error whereby a user has already been login.
 */
public class MultipleLoginException extends Exception {}
```
###### /java/seedu/address/model/exception/DuplicateUsernameException.java
``` java
package seedu.address.model.exception;

/**
 * Signals an error whereby a username is already being used.
 */
public class DuplicateUsernameException extends Exception {}
```
###### /java/seedu/address/model/exception/InvalidUsernameException.java
``` java
package seedu.address.model.exception;

/**
 * Signals an error whereby the username is not found.
 */
public class InvalidUsernameException extends Exception {}
```
###### /java/seedu/address/model/ReadOnlyAccountsManager.java
``` java
package seedu.address.model;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of AccountsManager
 */
public interface ReadOnlyAccountsManager {
    /**
     * Returns an unmodifiable view of the account list.
     * This list will not contain any duplicate accounts.
     */
    public ObservableList<Account> getAccountList();
}
```
