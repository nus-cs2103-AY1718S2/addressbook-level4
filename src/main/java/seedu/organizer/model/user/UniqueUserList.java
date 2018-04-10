package seedu.organizer.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.organizer.model.user.User.passwordMatches;
import static seedu.organizer.model.user.User.usernameMatches;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.organizer.commons.util.CollectionUtil;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.model.user.exceptions.UserPasswordWrongException;

//@@author dominickenn
/**
 * A list of users that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see User#equals(Object)
 */
public class UniqueUserList implements Iterable<User> {

    private final ObservableList<User> internalList = FXCollections.observableArrayList();
    private User currentLoggedInUser = null;

    /**
     * Constructs empty UserList.
     */
    public UniqueUserList() {}

    /**
     * Creates a UniqueUserList using given {@code users}
     * Enforces no nulls
     */
    public UniqueUserList(Set<User> users) {
        requireAllNonNull(users);
        internalList.addAll(users);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Sets currentLoggedInUser to {@code userToLogin}
     */
    public void setCurrentLoggedInUser(User userToLogIn)
            throws UserNotFoundException,
            CurrentlyLoggedInException,
            UserPasswordWrongException {

        requireNonNull(userToLogIn);

        if (currentLoggedInUser != null) {
            throw new CurrentlyLoggedInException();
        }

        if (userExistsInUserList(userToLogIn)) {
            this.currentLoggedInUser = userToLogIn;
        }
    }

    /**
     * Returns the true if {@code userToLogin} is found
     * @Throws UserNotFoundException if not found
     */
    private boolean userExistsInUserList(User userToLogIn) throws UserPasswordWrongException, UserNotFoundException {
        requireNonNull(userToLogIn);
        User userFound = null;
        for (User user : internalList) {
            userFound = returnUserIfMatch(userToLogIn, user);
        }
        if (userFound == null) {
            throw new UserNotFoundException();
        }
        return true;
    }

    /**
     * Attempts to match {@code userToLogin} and {@code user}
     * Both password and username have to match before a user is returned
     */
    private User returnUserIfMatch(User userToLogIn, User user) throws UserPasswordWrongException {
        User userFound = null;
        if (usernameMatches(user, userToLogIn)) {
            if (passwordMatches(user, userToLogIn)) {
                userFound = user;
            } else {
                throw new UserPasswordWrongException();
            }
        }
        return userFound;
    }

    public void setCurrentLoggedInUserToNull() {
        currentLoggedInUser = null;
    }

    public User getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    /**
     * Returns all users in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<User> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Users in this list with those in the argument user list.
     */
    public void setUsers(List<User> users) {
        requireAllNonNull(users);
        internalList.setAll(users);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent User as the given argument.
     */
    public boolean contains(User toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a User to the list.
     *
     * @throws DuplicateUserException if the User to add is a duplicate of an existing User in the list.
     */
    public void add(User toAdd) throws DuplicateUserException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUserException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Replaces a user with another user in internalList
     */
    public void updateUserToUserWithQuestionAnswer(User toRemove, UserWithQuestionAnswer toAdd)
            throws UserNotFoundException {
        requireAllNonNull(toRemove, toAdd);
        if (!internalList.contains(toRemove)) {
            throw new UserNotFoundException();
        }
        internalList.remove(toRemove);
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    public User getUserByUsername(String username) throws UserNotFoundException {
        requireNonNull(username);
        User userWithUsername = null;

        for (User u : internalList) {
            if (u.username.equals(username)) {
                userWithUsername = u;
            }
        }

        if (userWithUsername == null) {
            throw new UserNotFoundException();
        }

        return userWithUsername;
    }

    @Override
    public Iterator<User> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<User> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueUserList // instanceof handles nulls
                && this.internalList.equals(((UniqueUserList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
