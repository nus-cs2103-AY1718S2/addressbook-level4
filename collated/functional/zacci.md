# zacci
###### \java\seedu\address\logic\commands\AddUserCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.UserAlreadyExistsException;

/**
 * Adds a new user to the PrisonBook
 */
public class AddUserCommand extends Command {
    public static final String COMMAND_WORD = "adduser";
    public static final String COMMAND_ALIAS = "au";
    public static final int MIN_SECURITY_LEVEL = 3;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new user to the PrisonBook.\n"
            + "Parameters: user/NEW_USERNAME pw/NEW_PASSWORD sl/SECURITY_LEVEL (integer from 0 tp 3)...\n"
            + "Example: " + COMMAND_WORD + " user/newuser1 pw/password1 sl/2";

    public static final String MESSAGE_ADD_USER_SUCCESS = "New user %s added to PrisonBook";
    public static final String MESSAGE_ALREADY_EXISTING_USER = "%s is already a user in PrisonBook";
    private final String username;

    private User userToAdd;

    /**
     * @param username of the new user to be added to the PrisonBook
     * @param password of the new user to be added to the PrisonBook
     * @param securityLevel of the new user to be added to the PrisonBook
     */
    public AddUserCommand(String username, String password, int securityLevel) {
        requireNonNull(username);
        requireNonNull(password);
        this.username = username;
        userToAdd = new User(username, password, securityLevel);
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        requireNonNull(userToAdd);
        try {
            model.addUser(userToAdd);
        } catch (UserAlreadyExistsException uaee) {
            throw new CommandException(String.format(MESSAGE_ALREADY_EXISTING_USER, username));
        }
        return new CommandResult(String.format(MESSAGE_ADD_USER_SUCCESS, username));
    }

    public User getUserToAdd() {
        return userToAdd;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddUserCommand // instanceof handles nulls
                && Objects.equals(this.userToAdd, ((AddUserCommand) other).userToAdd));
    }
}
```
###### \java\seedu\address\logic\commands\CheckStatusCommand.java
``` java
package seedu.address.logic.commands;

/**
 * Displays the status of the current session
 */
public class CheckStatusCommand extends Command {

    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USER_NOT_LOGGED_IN = "You are not currently logged in";

    /**
     * Checks the status of current session
     * @return details of the status
     */
    public CommandResult execute() {
        if (!model.checkIsLoggedIn()) {
            return new CommandResult(MESSAGE_USER_NOT_LOGGED_IN);
        }
        String details = (model.getSessionDetails());
        return new CommandResult(details);
    }

}
```
###### \java\seedu\address\logic\commands\Command.java
``` java
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }
```
###### \java\seedu\address\logic\commands\DeleteUserCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.user.exceptions.CannotDeleteSelfException;
import seedu.address.model.user.exceptions.MustHaveAtLeastOneSecurityLevelThreeUserException;
import seedu.address.model.user.exceptions.NotEnoughAuthorityToDeleteException;
import seedu.address.model.user.exceptions.UserDoesNotExistException;

/**
 * Deletes a user from the PrisonBook.
 */
public class DeleteUserCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteuser";
    public static final String COMMAND_ALIAS = "du";
    public static final int MIN_SECURITY_LEVEL = 2;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes specified user.\n"
            + "Parameters: user/USERNAME_TO_BE_DELETED\n"
            + "Example: " + COMMAND_WORD + " user/prisonguard";

    public static final String MESSAGE_DELETE_CELL_SUCCESS = "User has been successfully deleted";

    private String userToDelete;

    /**
     * Creates a deleteUserCommand object
     * @param userToDelete username to be deleted
     */
    public DeleteUserCommand(String userToDelete) {
        this.userToDelete = userToDelete;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(userToDelete);
        try {
            model.deleteUser(userToDelete);
        } catch (CannotDeleteSelfException cdse) {
            throw new CommandException(cdse.getMessage());
        } catch (MustHaveAtLeastOneSecurityLevelThreeUserException mhalosltue) {
            throw new CommandException(mhalosltue.getMessage());
        } catch (UserDoesNotExistException udnee) {
            throw new CommandException(udnee.getMessage());
        } catch (NotEnoughAuthorityToDeleteException neatde) {
            throw new CommandException(neatde.getMessage());
        }

        return new CommandResult(MESSAGE_DELETE_CELL_SUCCESS);
    }

    @Override
    protected void preprocessUndoableCommand() {
    }

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteUserCommand // instanceof handles nulls
                && this.userToDelete.equals(((DeleteUserCommand) other).userToDelete)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java

    @Override
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }
```
###### \java\seedu\address\logic\commands\LoginCommand.java
``` java
package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Attempts to log in user with given Username and Password
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_LOGIN_FAILURE = "Login failed. Username and/or Password entered incorrectly.";
    public static final String MESSAGE_LOGIN_SUCCESS = "Login Success";
    public static final String MESSAGE_ALREADY_LOGGED_IN = "You are already logged in. Please logout before "
            + "attempting to login with another account.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logs in with your username and password to gain "
            + "access to the Prison Book.\n"
            + "Parameters: user/YOUR_USERNAME pw/YOUR_PASSWORD...\n"
            + "Example: " + COMMAND_WORD + " user/prisonwarden99 pw/password1";

    private final String username;
    private final String password;

    public LoginCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (model.checkIsLoggedIn()) {
            throw new CommandException(MESSAGE_ALREADY_LOGGED_IN);
        }
        if (!model.attemptLogin(username, password)) {
            throw new CommandException(MESSAGE_LOGIN_FAILURE);
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_LOGIN_SUCCESS);
        }
    }

}
```
###### \java\seedu\address\logic\commands\LogoutCommand.java
``` java
package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HideMapEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Logs the user out of the current session
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_SUCCESS = "Successfully logged out";
    public static final String MESSAGE_USER_NOT_LOGGED_IN = "You are not currently logged in";

    @Override
    public CommandResult execute() throws CommandException {
        undoRedoStack.clearStack();
        if (!model.checkIsLoggedIn()) {
            throw new CommandException(MESSAGE_USER_NOT_LOGGED_IN);
        }
        model.logout();
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(new ArrayList<String>()));
        EventsCenter.getInstance().post(new HideMapEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\UndoableCommand.java
``` java
    /**
     * Returns the MIN_SECURITY_LEVEL to caller
     */
    public int getMinSecurityLevel() {
        return MIN_SECURITY_LEVEL;
    }
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    /**
     * Executes the received command if the logged in user's security level meets the MIN_SECURITY_LEVEL for the command
     */
    private CommandResult restrictedExecute (Command command) throws CommandException {
        logger.info("Command MIN_SECURITY_LEVEL: " + command.getMinSecurityLevel());
        if (command.getMinSecurityLevel() <= model.getSecurityLevel()) {
            try {
                CommandResult result = command.execute();
                undoRedoStack.push(command);
                return result;
            } finally {
            }
        } else {
            CommandResult result = new CommandResult(MESSAGE_INSUFFICIENT_SECURITY_CLEARANCE);
            return result;
        }
    }
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<User> getUserList() {
        return model.getAddressBook().getUserList();
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case LoginCommand.COMMAND_WORD:
            return new LoginCommandParser().parse(arguments);

        case LogoutCommand.COMMAND_WORD:
            return new LogoutCommand();

        case CheckStatusCommand.COMMAND_WORD:
            return new CheckStatusCommand();

        case ShowUsersCommand.COMMAND_WORD:
            return new ShowUsersCommand();

        case AddUserCommand.COMMAND_WORD:
            return new AddUserCommandParser().parse(arguments);

        case DeleteUserCommand.COMMAND_WORD:
            return new DeleteUserCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddUserCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SECURITY_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddUserCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddUserCommand object
 */
public class AddUserCommandParser implements Parser<AddUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddUserCommand
     * and returns an AddUserCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_SECURITY_LEVEL);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD, PREFIX_SECURITY_LEVEL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddUserCommand.MESSAGE_USAGE));
        }

        String username = "";
        String password = "";
        int securityLevel = -1;
        try {
            username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME));
            password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD));
            securityLevel = ParserUtil.parseSecurityLevel(argMultimap.getValue(PREFIX_SECURITY_LEVEL));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }


        return new AddUserCommand(username, password, securityLevel);

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
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_USERNAME = new Prefix("user/");
    public static final Prefix PREFIX_PASSWORD = new Prefix("pw/");
    public static final Prefix PREFIX_SECURITY_LEVEL = new Prefix("sl/");
```
###### \java\seedu\address\logic\parser\LoginCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoginCommand object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LoginCommand
     * and returns an LoginCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String username = "";
        String password = "";
        try {
            username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME));
            password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD));
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }


        return new LoginCommand(username, password);

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
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a @code String username
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code password} is invalid.
     */
    public static String parseUsername(String username) throws IllegalValueException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!trimmedUsername.matches("[\\p{Alnum}]*")) {
            throw new IllegalValueException("Username can only consist of alphanumeric characters");
        }
        return trimmedUsername;
    }

    /**
     * Parses a {@code Optional<String> username} into an {@code Optional<username>} if {@code username} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parseUsername(Optional<String> username) throws IllegalValueException {
        //requireNonNull(username); null accepted for now
        return username.isPresent() ? parseUsername(username.get()) : "";
    }

    /**
     * Parses a @code String password
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code password} is invalid.
     */
    public static String parsePassword(String password) throws IllegalValueException {
        requireNonNull(password);
        String trimmedPassword = password.trim();

        if (!trimmedPassword.matches("[\\p{Alnum}]*")) {
            throw new IllegalValueException("Password can only consist of alphanumeric characters");
        }
        return trimmedPassword;
    }

    /**
     * Parses a {@code Optional<String> password} into an {@code Optional<password>} if {@code password} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static String parsePassword(Optional<String> password) throws IllegalValueException {
        requireNonNull(password);
        return password.isPresent() ? parsePassword(password.get()) : "";
    }

    /**
     * Parses a @code String securityLevel
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws IllegalValueException if the given {@code securityLevel} is invalid.
     */
    public static int parseSecurityLevel(String securityLevel) throws NumberFormatException, IllegalValueException {
        requireNonNull(securityLevel);
        String trimmedSecurityLevel = securityLevel.trim();

        if (!trimmedSecurityLevel.matches("[123]")) {
            throw new IllegalValueException("Security Level can only take integer values 1, 2 or 3");
        }

        int intSecurityLevel = Integer.parseInt(trimmedSecurityLevel);
        return intSecurityLevel;
    }

    /**
     * Parses a {@code Optional<int> securityLevel} into an {@code Optional<securityLevel>}
     * if {@code securityLevel} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static int parseSecurityLevel (Optional<String> password)
            throws NumberFormatException, IllegalValueException {
        requireNonNull(password);
        return password.isPresent() ? parseSecurityLevel(password.get()) : -1;
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// user-level operations

    /**
     *
     * @param u is the user to add to the HashMap
     */
    public void addUser(User u) throws UserAlreadyExistsException {
        users.addUser(u);
    }

    /**
     * Attempt to log in with the entered username and password
     */
    public int attemptLogin(String username, String password) {
        return users.verify(username, password);
    }

    @Override
    public ObservableList<User> getUserList() {
        return users.getUserList();
    }

    public void setUsers(ObservableList<User> users) {
        this.users.setUsers(users);
    }

    public void deleteUser(String userToDelete, String deleterUsername) throws CannotDeleteSelfException,
            MustHaveAtLeastOneSecurityLevelThreeUserException, UserDoesNotExistException,
            NotEnoughAuthorityToDeleteException {
        users.deleteUser(userToDelete, deleterUsername);
    }

```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns the session */
    Session getSession();

    /** Checks if there is a user logged in to the current session*/
    boolean checkIsLoggedIn();

    /** Clears existing session*/
    void logout();

    /** Logs in verified user and assigns security level to the session */
    void login(String username, int securityLevel);

    /** Attempts to login user with entered username and password */
    boolean attemptLogin(String username, String password);

    /** Returns Session details to caller */
    String getSessionDetails();

    /** Returns Session security level to caller */
    int getSecurityLevel();

    /** Adds given user to the PrisonBook */
    void addUser(User user) throws UserAlreadyExistsException;

    /** Adds given user to the PrisonBook */
    void deleteUser(String user) throws CannotDeleteSelfException, MustHaveAtLeastOneSecurityLevelThreeUserException,
            UserDoesNotExistException, NotEnoughAuthorityToDeleteException;
```
###### \java\seedu\address\model\ModelManager.java
``` java
        logger.info("Initialising session");
        session = new Session();
        logger.info("Initialised session");
        //@@ author
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void logout() {
        session.logout();
        logger.info("User logged out");
    }

    @Override
    public boolean checkIsLoggedIn() {
        return session.checkIsLoggedIn();
    }

    @Override
    public void login(String username, int securityLevel) {
        session.login(username, securityLevel);
        logger.info("User logged in with: u/" + username + " slevel/" + securityLevel);
    }

    @Override
    public boolean attemptLogin(String username, String password) {
        logger.info("Current session: " + getSessionDetails());
        int securityLevel = addressBook.attemptLogin(username, password);
        if (securityLevel < 0) {
            return false;
        } else {
            login(username, securityLevel);
            indicateAddressBookChanged();
            return true;
        }
    }

    @Override
    public String getSessionDetails() {
        return ("Username: " + session.getUsername() + " Security Level: " + session.getSecurityLevel());
    }

    @Override
    public int getSecurityLevel() {
        return session.getSecurityLevel();
    }

    @Override
    public void addUser(User userToAdd) throws UserAlreadyExistsException {
        addressBook.addUser(userToAdd);
        indicateAddressBookChanged();
        logger.info("New user added: " + userToAdd.getUsername());
    }

    @Override
    public void deleteUser (String userToDelete) throws CannotDeleteSelfException,
            MustHaveAtLeastOneSecurityLevelThreeUserException, UserDoesNotExistException,
            NotEnoughAuthorityToDeleteException {
        addressBook.deleteUser(userToDelete, session.getUsername());
        indicateAddressBookChanged();
        logger.info("User deleted: " + userToDelete);
    }
```
###### \java\seedu\address\model\person\Role.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {


    public static final String MESSAGE_ROLE_CONSTRAINTS =
            "Role can only take on the values 'g' or 'p', which represents Guard or Prisoner respectively";
    public static final Role PRISONER = new Role("p");
    public static final Role GUARD = new Role("g");
    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role 'g' or 'p' to represent Guard or Prisoner respectively.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_ROLE_CONSTRAINTS);
        this.value = role;
    }

    /**
     * Returns true if a given string is a valid person phone number.
     */
    public static boolean isValidRole(String test) {
        return test.equals("g") || test.equals("p");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Role // instanceof handles nulls
                && this.value.equals(((Role) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the users list.
     * This list will not contain any duplicate users.
     */
    ObservableList<User> getUserList();
```
###### \java\seedu\address\model\Session.java
``` java
package seedu.address.model;

/**
 * Represents User's Session.
 */
public class Session {

    private String username;
    private int securityLevel;
    private boolean isLoggedIn = false;

    public Session() {
        resetSession();
    }

    /**
     * Sets session details for user upon successful login
     */
    public void login(String username, int securityLevel) {
        this.username = username;
        this.securityLevel = securityLevel;
        this.isLoggedIn = true;
    }

    public void logout() {
        resetSession();
    }

    private void resetSession() {
        username = "";
        securityLevel = 0;
        isLoggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public boolean checkIsLoggedIn () {
        return isLoggedIn;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof Session)) {
            return false;
        }

        // state check
        Session other = (Session) obj;
        return username.equals(other.username)
                && securityLevel == other.securityLevel;
    }
}
```
###### \java\seedu\address\model\user\exceptions\UserAlreadyExistsException.java
``` java
package seedu.address.model.user.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that the username that is being added already exists.
 */
public class UserAlreadyExistsException extends IllegalValueException {
    public UserAlreadyExistsException() {
        super("This username is already used");
    }
}
```
###### \java\seedu\address\model\user\UniqueUserMap.java
``` java
package seedu.address.model.user;

import java.util.HashMap;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.user.exceptions.CannotDeleteSelfException;
import seedu.address.model.user.exceptions.MustHaveAtLeastOneSecurityLevelThreeUserException;
import seedu.address.model.user.exceptions.NotEnoughAuthorityToDeleteException;
import seedu.address.model.user.exceptions.UserAlreadyExistsException;
import seedu.address.model.user.exceptions.UserDoesNotExistException;

/**
 * Contains the users of the PrisonBook
 */
public class UniqueUserMap {

    private final ObservableList<User> internalList = FXCollections.observableArrayList();

    private HashMap<String, User> userMap;

    private int numberOfSecurityLevelThree = 0;

    private final User defaultUser1 = new User("prisonguard", "password1", 1);
    private final User defaultUser2 = new User("prisonleader", "password2", 2);
    private final User defaultUser3 = new User("prisonwarden", "password3", 3);

    public UniqueUserMap() {
        resetData();
    }

    /**
     * Resets the existing data of this {@code userMap}.
     */
    public void resetData() {
        userMap = new HashMap<String, User>();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<User> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Returns user's securityLevel if username and matching password is found in database, else return -1
     * @param username
     * @param password
     * @return
     */
    public int verify(String username, String password) {
        if (contains(username)) {
            return userMap.get(username).checkPassword(password);
        } else {
            return -1;
        }
    }

    public boolean contains(String username) {
        return userMap.containsKey(username);
    }

    /**
     * For storage purposes
     */
    public ObservableList<User> getUserList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    /**
     * Adds user to the userMap and the internalList
     * @param user must be valid User
     * @return true if added successfully and false if failed to add
     */
    public boolean addUser(User user) throws UserAlreadyExistsException {
        if (contains(user.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            userMap.put(user.getUsername(), user);
            internalList.add(user);
            if (user.getSecurityLevel() == 3) {
                numberOfSecurityLevelThree++;
            }
            return true;
        }
    }

    /**
     * Delete user from the userMap and the internalList
     * @param userToDelete must be an existing user
     * @return true if added successfully and false if failed to add
     */
    public boolean deleteUser(String userToDelete, String deleterUsername) throws UserDoesNotExistException,
            NotEnoughAuthorityToDeleteException, CannotDeleteSelfException,
            MustHaveAtLeastOneSecurityLevelThreeUserException {
        int deleterSecurityLevel = userMap.get(deleterUsername).getSecurityLevel();
        if (!contains(userToDelete)) {
            throw new UserDoesNotExistException();
        } else if (userToDelete.equals(deleterUsername)) {
            throw new CannotDeleteSelfException();
        } else if (deleterSecurityLevel != 3 && userMap.get(userToDelete).getSecurityLevel() >= deleterSecurityLevel) {
            throw new NotEnoughAuthorityToDeleteException();
        } else if (userMap.get(userToDelete).getSecurityLevel() == 3 && numberOfSecurityLevelThree <= 1) {
            throw new MustHaveAtLeastOneSecurityLevelThreeUserException();
        } else {
            userMap.remove(userToDelete);
            Iterator<User> iter = internalList.listIterator();
            for (int i = 0; i < internalList.size(); i++) {
                User curr = iter.next();
                if (userToDelete.equals(curr.getUsername())) {
                    iter.remove();
                    break;
                }
            }
            return true;
        }
    }

    public void setUsers(ObservableList<User> users) {
        for (User u: users) {
            try {
                addUser(u);
            } catch (UserAlreadyExistsException e) {
                int dummy = 0;
            }
        }
        internalList.clear();
        internalList.setAll(users);
    }

    /**
     * Checks if two UniqueUserMaps are equal
     * @param obj any object
     * @return return true if the userMap and internalList are equal
     */
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof UniqueUserMap)) {
            return false;
        }

        // state check
        UniqueUserMap other = (UniqueUserMap) obj;
        return userMap.equals(other.userMap) && internalList.equals(other.internalList);
    }
}
```
###### \java\seedu\address\model\user\User.java
``` java
package seedu.address.model.user;

/**
 * Represents a user of the PrisonBook
 */
public class User {

    public static final String NAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String username;
    private final String password;
    private final int securityLevel;

    public User (String username, String password, int securityLevel) {
        this.username = username;
        this.password = password;
        this.securityLevel = securityLevel;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }

    /**
     * Checks if given string matches password, returns user securityLevel if password matches, else returns -1
     */
    public int checkPassword(String enteredPassword) {
        if (enteredPassword.equals(password)) {
            return securityLevel;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof User // instanceof handles nulls
                && username.equals(((User) other).username));
    }

    @Override
    public String toString() {
        return ("Username: " + username + " securityLevel: " + securityLevel);
    }

}

```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    public static User[] getSampleUsers() {
        return new User[] {
            new User("prisonguard", "password1", 1),
            new User("prisonleader", "password2", 2),
            new User("prisonwarden", "password3", 3),
            new User("prisonguard2", "password1", 1),
            new User("prisonguard3", "password1", 1),
            new User("prisonleader2", "password2", 2),
            new User("prisonleader3", "password2", 2),
            new User("prisonwarden2", "password3", 3)
        };
    }
```
###### \java\seedu\address\storage\XmlAdaptedUser.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.user.User;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedUser {

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private int securityLevel;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedUser(String username, String password, int securityLevel) {
        this.username = username;
        this.password = password;
        this.securityLevel = securityLevel;
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedUser
     */
    public XmlAdaptedUser(User source) {
        username = source.getUsername();
        password = source.getPassword();
        securityLevel = source.getSecurityLevel();
    }

    /**
     * Converts this jaxb-friendly adapted user object into the model's User object.
     *
     */
    public User toModelType() {
        return new User(username, password, securityLevel);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUser)) {
            return false;
        }

        XmlAdaptedUser otherUser = (XmlAdaptedUser) other;
        return this.username.equals(((XmlAdaptedUser) other).username)
                && this.password.equals(((XmlAdaptedUser) other).password)
                && this.securityLevel == (((XmlAdaptedUser) other).securityLevel);
    }
}
```
