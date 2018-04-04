package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUserList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;

//@@author kaisertanqr
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

    //@@author ifalluphill
    /**
     * Returns the User who is logged in.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }
    //@@author kaisertanqr

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
