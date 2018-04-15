//@@author zacci
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
