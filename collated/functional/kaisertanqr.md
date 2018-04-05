# kaisertanqr
###### /java/seedu/address/ui/LoginStatusBar.java
``` java
/**
 * A ui for the login status bar that is displayed at the top of the application.
 */
public class LoginStatusBar extends UiPart<Region> {

    public static final String LOGIN_STATUS_PREFIX = "Status: ";
    public static final String LOGIN_STATUS_TRUE = "Logged In";
    public static final String LOGIN_STATUS_FALSE = "Not Logged In";

    private static final String FXML = "LoginStatusBar.fxml";

    @FXML
    private StatusBar loginStatus;

    public LoginStatusBar() {
        super(FXML);
        updateLoginStatus(false);
        registerAsAnEventHandler(this);
    }

    /**
     * Updates the UI with the login status, {@code status}.
     * @param status
     */
    public void updateLoginStatus(boolean status) {
        if (status) {
            Platform.runLater(() -> this.loginStatus.setText(LOGIN_STATUS_PREFIX + LOGIN_STATUS_TRUE));
            loginStatus.setStyle("-fx-background-color: rgb(0, 77, 26, 0.5)");
        } else {
            Platform.runLater(() -> this.loginStatus.setText(LOGIN_STATUS_PREFIX + LOGIN_STATUS_FALSE));
            loginStatus.setStyle("-fx-background-color: rgb(77, 0, 0, 0.5)");
        }
    }



}
```
###### /java/seedu/address/ui/MainWindow.java
``` java

    /**
     * Hides browser and person list panel.
     */
    void hideBeforeLogin() {
        loginStatusBar.updateLoginStatus(false);
        featuresTabPane.setVisible(false);
        personDetailsPlaceholder.setVisible(false);
        calendarPlaceholder.setVisible(false);
        dailySchedulerPlaceholder.setVisible(false);
        personListPanelPlaceholder.setVisible(false);
    }

    /**
     * Unhide browser and person list panel.
     */
    public void showAfterLogin() {
        loginStatusBar.updateLoginStatus(true);
        featuresTabPane.setVisible(true);
        personDetailsPlaceholder.setVisible(true);
        calendarPlaceholder.setVisible(true);
        dailySchedulerPlaceholder.setVisible(true);
        personListPanelPlaceholder.setVisible(true);
    }

```
###### /java/seedu/address/commons/events/model/UserDatabaseChangedEvent.java
``` java
/** Indicates the UserDatabase in the model has changed*/
public class UserDatabaseChangedEvent extends BaseEvent {

    public final ReadOnlyUserDatabase data;

    public UserDatabaseChangedEvent(ReadOnlyUserDatabase data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of users: " + data.getUsersList().size();
    }
}
```
###### /java/seedu/address/commons/events/model/UserDeletedEvent.java
``` java
/** Indicates a User in the model has been deleted*/
public class UserDeletedEvent extends BaseEvent {

    public final User data;

    public UserDeletedEvent(User user) {
        this.data = user;
    }

    @Override
    public String toString() {
        return "user deleted: " + data.getUsername().toString();
    }
}
```
###### /java/seedu/address/logic/parser/LoginCommandParser.java
``` java
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
            throw new ParseException(String.format(MESSAGE_INVALID_LOGIN_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        try {
            Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME)).get();
            Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD)).get();

            return new LoginCommand(username, password);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
###### /java/seedu/address/logic/commands/LoginCommand.java
``` java
/**
 * Authenticates login credentials.
 */
public class LoginCommand extends Command {

    public static final String COMMAND_WORD = "login";

    public static final String MESSAGE_USAGE = "Login with the parameters: u/USERNAME p/PASSWORD"
            + "\nEXAMPLE: login u/user p/123456"
            + "\nDefault user login credentials are as follows: USERNAME: user , PASSWORD: pass";

    public static final String MESSAGE_LOGIN_SUCCESS = "Login successful!";
    public static final String MESSAGE_LOGIN_FAILURE = "Username or password is incorrect. Please login again.";
    public static final String MESSAGE_LOGIN_ALREADY = "You have already logged in.";

    private final Username username;
    private final Password password;

    public LoginCommand(Username username, Password password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            if (model.checkLoginCredentials(this.username, this.password)) {
                return new CommandResult(MESSAGE_LOGIN_SUCCESS);
            } else {
                return new CommandResult(MESSAGE_LOGIN_FAILURE);
            }
        } catch (AlreadyLoggedInException e) {
            throw new CommandException(MESSAGE_LOGIN_ALREADY);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginCommand // instanceof handles nulls
                && this.username.equals(((LoginCommand) other).username)
                && this.password.equals(((LoginCommand) other).password)); // state check
    }
}
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public CommandResult executeNoLoginCommands (String commandText, Command command) throws CommandException {
        CommandResult result;
        try {
            if (commandText.split(" ")[0].equals(LoginCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(HelpCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(ExitCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(CreateUserCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(DeleteUserCommand.COMMAND_WORD)
                    || commandText.split(" ")[0].equals(ChangeUserPasswordCommand.COMMAND_WORD)) {
                result = command.execute();
            } else {
                result = null;
            }
        } finally { }

        return result;
    }

    @Override
    public boolean hasLoggedIn() {
        return model.hasLoggedIn();
    }

    @Override
    public User getLoggedInUser() {
        return model.getLoggedInUser();
    }

}
```
###### /java/seedu/address/storage/XmlAdaptedUser.java
``` java
/**
 * JAXB-friendly version of the User.
 */
public class XmlAdaptedUser {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "User's %s field is missing!";

    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String password;
    @XmlElement(required = true)
    private String addressbookfilepath;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {}

    /**
     * Constructs an {@code XmlAdaptedUser} with the given person details.
     */
    public XmlAdaptedUser(String username, String password, String addressbookfilepath) {
        this.username = username;
        this.password = password;
        this.addressbookfilepath = addressbookfilepath;
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedUser(User source) {
        username = source.getUsername().toString();
        password = source.getPassword().toString();
        addressbookfilepath = source.getAddressBookFilePath();
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public User toModelType() throws IllegalValueException {
        if (this.username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(this.username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username username = new Username(this.username);

        if (this.password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Password.class.getSimpleName()));
        }
        if (!Password.isValidPassword(this.password)) {
            throw new IllegalValueException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        final Password password = new Password(this.password);

        if (this.addressbookfilepath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "AddressBook file path"));
        }

        final String addressBookFilePath = this.addressbookfilepath;

        return new User(username, password, addressBookFilePath);
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
        return Objects.equals(username, otherUser.username)
                && Objects.equals(password, otherUser.password)
                && Objects.equals(addressbookfilepath, otherUser.addressbookfilepath);

    }
}
```
###### /java/seedu/address/storage/StorageManager.java
``` java

    // ================ User database methods ==============================

    @Override
    public String getUserDatabaseFilePath() {
        return userDatabaseStorage.getUserDatabaseFilePath();
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return readUserDatabase(userDatabaseStorage.getUserDatabaseFilePath());
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase(String filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return userDatabaseStorage.readUserDatabase(filePath);
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {
        saveUserDatabase(userDatabase, userDatabaseStorage.getUserDatabaseFilePath());
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        userDatabaseStorage.saveUserDatabase(userDatabase, filePath);
    }

    @Override
    public void deleteAddressBook(User user) {
        logger.fine("Attempting to delete to data file: " + user.getAddressBookFilePath());
        addressBookStorage.deleteAddressBook(user);
    }

    @Override
    @Subscribe
    public void handleUserDatabaseChangedEvent(UserDatabaseChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local users data changed, saving to file"));
        try {
            saveUserDatabase(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleUserDeletedEvent(UserDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "User has been deleted, deleting files"));
        deleteAddressBook(event.data);
    }

    // ============== Storage updater =====================

    public void update(User user) {
        this.addressBookStorage = new XmlAddressBookStorage(user.getAddressBookFilePath());
    }



}
```
###### /java/seedu/address/storage/XmlSerializableUserDatabase.java
``` java
/**
 * An Immutable UserDatabase that is serializable to XML format
 */
@XmlRootElement(name = "usersdatabase")
public class XmlSerializableUserDatabase {


    @XmlElement
    private List<XmlAdaptedUser> users;

    /**
     * Creates an empty XmlSerializableUserDatabase.
     */
    public XmlSerializableUserDatabase() {
        users = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableUserDatabase(ReadOnlyUserDatabase src) {
        this();
        users.addAll(src.getUsersList().stream().map(XmlAdaptedUser::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedPerson} or {@code XmlAdaptedTag}.
     */
    public UserDatabase toModelType() throws IllegalValueException {
        UserDatabase userDatabase = new UserDatabase();
        for (XmlAdaptedUser user : users) {
            userDatabase.addUser(user.toModelType());
        }
        return userDatabase;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableUserDatabase)) {
            return false;
        }

        XmlSerializableUserDatabase otherUd = (XmlSerializableUserDatabase) other;
        return users.equals(otherUd.users);
    }
}
```
###### /java/seedu/address/storage/XmlUserDatabaseStorage.java
``` java
/**
 * A class to access UserDatabase data stored as an xml file on the hard disk.
 */
public class XmlUserDatabaseStorage implements UserDatabaseStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlUserDatabaseStorage.class);

    private String filePath;

    public XmlUserDatabaseStorage(String filePath) {
        this.filePath = filePath;
    }

    public String getUserDatabaseFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserDatabase> readUserDatabase() throws DataConversionException, IOException {
        return readUserDatabase(filePath);
    }

    /**
     * Similar to {@link #readUserDatabase()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUserDatabase> readUserDatabase(String filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        File usersFile = new File(filePath);

        if (!usersFile.exists()) {
            logger.info("Users file "  + usersFile + " not found");
            return Optional.empty();
        }

        XmlSerializableUserDatabase xmlUsers = XmlFileStorage.loadUsersFromSaveFile(new File(filePath));
        try {
            return Optional.of(xmlUsers.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + usersFile + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase) throws IOException {
        saveUserDatabase(userDatabase, filePath);
    }

    /**
     * Similar to {@link #saveUserDatabase (ReadOnlyUserDatabase)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveUserDatabase(ReadOnlyUserDatabase userDatabase, String filePath) throws IOException {
        requireNonNull(userDatabase);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveUsersToFile(file, new XmlSerializableUserDatabase(userDatabase));
    }

}

```
###### /java/seedu/address/model/util/SampleUsersUtil.java
``` java
/**
 * Contains utility methods for populating {@code UserDatabase} with one default user.
 */
public class SampleUsersUtil {

    public static ReadOnlyUserDatabase getSampleUserDatabase() {
        try {
            UserDatabase sampleUd = new UserDatabase();
            sampleUd.addUser(new User(new Username("user"), new Password("pass"), "data/addressbook-user.xml"));
            sampleUd.addUser(new User(new Username("u"), new Password("p"), "data/addressbook-hello.xml"));
            return sampleUd;
        } catch (DuplicateUserException e) {
            throw new AssertionError("sample data cannot contain duplicate persons", e);
        }
    }

}
```
###### /java/seedu/address/model/UserDatabase.java
``` java
/**
 * Wraps all the data of Users.
 */
public class UserDatabase implements ReadOnlyUserDatabase {

    private static final String AB_FILEPATH_PREFIX = "data/addressbook-";
    private static final String AB_FILEPATH_POSTFIX = ".xml";
    private final UniqueUserList users;

    private boolean hasLoggedIn;
    private User loggedInUser;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        users = new UniqueUserList();
    }

    public UserDatabase() {
        hasLoggedIn = false;
    }

    /**
     * Creates an UserDatabase using the Users in the {@code toBeLoaded}
     */
    public UserDatabase(ReadOnlyUserDatabase toBeLoaded) {
        this();
        resetData(toBeLoaded);
    }

    /**
     * Creates an UserDatabase using the Users  in the {@code toBeLoaded} and logged-in status
     */
    public UserDatabase(ReadOnlyUserDatabase toBeLoaded, boolean loggedin) {
        this();
        resetData(toBeLoaded);
        hasLoggedIn = loggedin;
    }


    public User getUser(Username username) {
        return users.getUser(username.toString());
    }

    /// login authentication operations

    /**
     * Returns the User who is logged in.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * Returns the login status of the user.
     */
    public boolean hasLoggedIn() {
        return hasLoggedIn;
    }

    /**
     * Sets the login status of the user to {@code status}.
     * @param status
     */
    public void setLoginStatus(boolean status) {
        hasLoggedIn = status;
    }


    /**
     * Checks the login credentials whether it matches any user in UserDatabase.
     *
     * @param username
     * @param password
     * @throws AlreadyLoggedInException is the user is already logged in.
     */
    public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
        User toCheck = new User(username, password,
                AB_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX);

        if (hasLoggedIn) {
            throw new AlreadyLoggedInException();
        } else if (!users.contains(toCheck)) {
            return hasLoggedIn;
        } else {
            hasLoggedIn = true;
            loggedInUser = toCheck;
            return hasLoggedIn;
        }
    }

    /**
     * Checks whether input credentials matches a valid user.
     *
     * @param username
     * @param password
     * @return
     * @throws AlreadyLoggedInException
     */
    public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
        User toCheck = new User(username, password,
                AB_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX);
        if (!hasLoggedIn) {
            return users.contains(toCheck);
        } else {
            throw new AlreadyLoggedInException();
        }
    }

    //// list overwrite operations

    public void setUsers(List<User> users) throws DuplicateUserException {
        this.users.setUsers(users);
    }


    //// user-level operations

    /**
     * Adds a user to the User Database.
     *
     * @throws DuplicateUserException if an equivalent user already exists.
     */
    public void addUser(User user) throws DuplicateUserException {
        users.add(user);
    }

    /**
     * Replaces the given user {@code target} in the list with {@code editedUser}.
     *
     * @throws DuplicateUserException if updating the user's details causes the user to be equivalent to
     *      another existing user in the list.
     * @throws UserNotFoundException if {@code target} could not be found in the list.
     */
    public void updateUserPassword(User target, User userWithNewPassword)
            throws UserNotFoundException {
        requireNonNull(userWithNewPassword);
        users.setUser(target, userWithNewPassword);
    }


    /**
     * Removes {@code key} from this {@code UserDatabase}.
     * @throws UserNotFoundException if the {@code key} is not in this {@code UserDatabase}.
     */
    public boolean removeUser(User key) throws UserNotFoundException {
        if (users.remove(key)) {
            return true;
        } else {
            throw new UserNotFoundException();
        }
    }

    /**
     * Resets the existing user list of this {@code UserDatabase} with {@code newData}.
     */
    private void resetData(ReadOnlyUserDatabase newData) {
        requireNonNull(newData);
        List<User> userList = newData.getUsersList().stream().collect(Collectors.toList());
        try {
            setUsers(userList);
        } catch (DuplicateUserException e) {
            throw new AssertionError("UserDatabase should not have duplicate persons");
        }
    }

    @Override
    public ObservableList<User> getUsersList() {
        return users.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserDatabase // instanceof handles nulls
                && this.users.equals(((UserDatabase) other).users));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(users);
    }
}
```
###### /java/seedu/address/model/ReadOnlyUserDatabase.java
``` java
/**
 * Unmodifiable view of a user database
 */
public interface ReadOnlyUserDatabase {

    /**
     * Returns an unmodifiable view of the User list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<User> getUsersList();
}
```
###### /java/seedu/address/model/ModelManager.java
``` java

    /**
     * Reloads and updates the addressBook and its storage path using the {@code username} provided.
     * @param username
     */
    private void reloadAddressBook(Username username) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook newData;

        storage.update(userDatabase.getUser(username));
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            newData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            newData = new AddressBook();
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
        } catch (IOException e) {
            newData = new AddressBook();
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
        }
        addressBook.resetData(newData);
    }

    // ============== UserDatabase Modifiers =============================================================

    @Override
    public ReadOnlyAddressBook getUserDatabase() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserDatabaseChanged() {
        raise(new UserDatabaseChangedEvent(userDatabase));
    }

    /** Raises an event to indicate a user has been deleted */
    private void indicateUserDeleted(User user) {
        raise(new UserDeletedEvent(user));
    }

    @Override
    public synchronized void deleteUser(User target) throws UserNotFoundException {
        userDatabase.removeUser(target);
        indicateUserDatabaseChanged();
        indicateUserDeleted(target);
    }

    @Override
    public synchronized void addUser(User person) throws DuplicateUserException {
        userDatabase.addUser(person);
        indicateUserDatabaseChanged();
    }

    @Override
    public boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException {
        boolean result = userDatabase.checkLoginCredentials(username, password);
        if (hasLoggedIn() && result) {
            reloadAddressBook(username);
        }
        return result;
    }

    @Override
    public boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException {
        boolean result = userDatabase.checkCredentials(username, password);
        return result;
    }

    @Override
    public void updateUserPassword(User target, User userWithNewPassword)
            throws UserNotFoundException {
        requireAllNonNull(target, userWithNewPassword);
        userDatabase.updateUserPassword(target, userWithNewPassword);
        indicateUserDatabaseChanged();
    }

    @Override
    public User getLoggedInUser() {
        return userDatabase.getLoggedInUser();
    }


    @Override
    public boolean hasLoggedIn() {
        return userDatabase.hasLoggedIn();
    }

    @Override
    public void setLoginStatus(boolean status) {
        userDatabase.setLoginStatus(status);
    }

```
###### /java/seedu/address/model/login/UniqueUserList.java
``` java
/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see User#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueUserList implements Iterable<User> {
    private final ObservableList<User> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent user as the given argument.
     */
    public boolean contains(User toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a user to the list.
     *
     * @throws DuplicateUserException if the user to add is a duplicate of an existing user in the list.
     */
    public void add(User toAdd) throws DuplicateUserException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUserException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the user {@code target} in the list with {@code editedUser}.
     *
     * @throws DuplicateUserException if the replacement is equivalent to another existing user in the list.
     * @throws UserNotFoundException if {@code target} could not be found in the list.
     */
    public void setUser(User target, User editedUser)
            throws UserNotFoundException {
        requireNonNull(editedUser);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new UserNotFoundException();
        }

        internalList.set(index, editedUser);
    }

    public User getUser(String username) {
        for (User user :internalList) {
            if (username.equals(user.getUsername().toString())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Removes the equivalent user from the list.
     *
     * @throws UserNotFoundException if no such user could be found in the list.
     */
    public boolean remove(User toRemove) throws UserNotFoundException {
        requireNonNull(toRemove);
        final boolean userFoundAndDeleted = internalList.remove(toRemove);
        if (!userFoundAndDeleted) {
            throw new UserNotFoundException();
        }
        return userFoundAndDeleted;
    }

    public void setUsers(UniqueUserList replacement) {
        this.internalList.setAll(replacement.internalList);
    }

    public void setUsers(List<User> users) throws DuplicateUserException {
        requireAllNonNull(users);
        final UniqueUserList replacement = new UniqueUserList();
        for (final User user : users) {
            replacement.add(user);
        }
        setUsers(replacement);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<User> asObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<User> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueUserList // instanceof handles nulls
                && this.internalList.equals(((UniqueUserList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
```
###### /java/seedu/address/model/login/User.java
``` java
/**
 * Represents a User of the addressbook
 *
 */
public class User {

    public static final String AB_FILEPATH_PREFIX = "data/addressbook-";
    public static final String AB_FILEPATH_POSTFIX = ".xml";

    private Username username;
    private Password password;
    private String addressBookFilePath;

    public User() {
        this.username = new Username("default");
        this.password = new Password("password");
        this.addressBookFilePath = "data/addressbook-default.xml";
    }

    public User(Username username, Password password) {
        this(username, password, AB_FILEPATH_PREFIX + username + AB_FILEPATH_POSTFIX);
    }

    public User(Username username, Password password, String addressBookFilePath) {
        requireAllNonNull(username, password, addressBookFilePath);
        this.username = username;
        this.password = password;
        this.addressBookFilePath = addressBookFilePath;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public String getAddressBookFilePath() {
        return addressBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof User)) {
            return false;
        }

        User otherPerson = (User) other;
        return otherPerson.getUsername().equals(this.getUsername())
                && otherPerson.getPassword().equals(this.getPassword())
                && otherPerson.getAddressBookFilePath().equals(this.getAddressBookFilePath());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username, password, addressBookFilePath);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUsername())
                .append(" Username: ")
                .append(getPassword())
                .append(" Password: ")
                .append(getAddressBookFilePath())
                .append(" File Path: ");
        return builder.toString();
    }

}
```
###### /java/seedu/address/model/login/Username.java
``` java
/**
 * Represents a Username used to login to AddressBook
 * Guarantees: immutable; is valid as declared in {@link #isValidUsername(String)}
 */
public class Username {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Usernames should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullUsername;

    /**
     * Constructs a {@code Username}.
     *
     * @param username A valid username.
     */
    public Username(String username) {
        requireNonNull(username);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        this.fullUsername = username;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullUsername;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Username // instanceof handles nulls
                && this.fullUsername.equals(((Username) other).fullUsername)); // state check
    }

    @Override
    public int hashCode() {
        return fullUsername.hashCode();
    }
}
```
###### /java/seedu/address/model/login/exceptions/AlreadyLoggedInException.java
``` java
/**
 * Signals that the user has already logged in.
 */
public class AlreadyLoggedInException extends Exception {
}
```
###### /java/seedu/address/model/login/exceptions/AuthenticationFailedException.java
``` java
/**
 * Signals that the operation has failed the authentication process.
 */
public class AuthenticationFailedException extends Exception {}
```
###### /java/seedu/address/model/login/Password.java
``` java
/**
 * Represents a Username used to login to AddressBook
 * Guarantees: immutable; is valid as declared in {@link #isValidPassword(String)}
 */
public class Password {

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PASSWORD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String password;

    /**
     * Constructs a {@code Username}.
     *
     * @param password A valid name.
     */
    public Password(String password) {
        requireNonNull(password);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && this.password.equals(((Password) other).password)); // state check
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}
```
###### /java/seedu/address/model/Model.java
``` java

    /** Returns the UserDatabase */
    ReadOnlyAddressBook getUserDatabase();

    /** Deletes the given user. */
    void deleteUser(User target) throws UserNotFoundException;

    /** Adds the given user */
    void addUser(User person) throws DuplicateUserException;

    /** Updates the password of the target user*/
    void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException;

    /**
     * Checks the validity of login credentials.
     *
     * @param username
     * @param password
     *
     * @throws AlreadyLoggedInException if user has already logged in.
     */
    boolean checkLoginCredentials(Username username, Password password) throws AlreadyLoggedInException;

    /**
     * Checks a whether credentials.
     *
     * @param username
     * @param password
     */
    boolean checkCredentials(Username username, Password password) throws AlreadyLoggedInException;

    /**
     * Returns whether the AddressBook has already been logged into.
     *
     */
    boolean hasLoggedIn();

    /**
     * Set login status in AddressBook to {@code status}.
     *
     * @param status
     */
    void setLoginStatus(boolean status);

    /**
     * Returns the user who is logged in.
     */
    User getLoggedInUser();
}
```
