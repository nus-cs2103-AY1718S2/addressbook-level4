package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    //@@author kaisertanqr

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
