package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.cell.exceptions.AlreadyInCellException;
import seedu.address.model.cell.exceptions.FullCellException;
import seedu.address.model.cell.exceptions.NonExistentCellException;
import seedu.address.model.cell.exceptions.NotImprisonedException;
import seedu.address.model.cell.exceptions.NotPrisonerException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.CannotDeleteSelfException;
import seedu.address.model.user.exceptions.MustHaveAtLeastOneSecurityLevelThreeUserException;
import seedu.address.model.user.exceptions.NotEnoughAuthorityToDeleteException;
import seedu.address.model.user.exceptions.UserAlreadyExistsException;
import seedu.address.model.user.exceptions.UserDoesNotExistException;

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

    //@@author zacci
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
    //@@author

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    //@@author sarahgoh97
    /** Adds given prisoner into a cell */
    void addPrisonerToCell(Person prisoner, String cellAddress)
            throws FullCellException, NonExistentCellException,
            NotPrisonerException, AlreadyInCellException;

    /**Deletes given prisoner from a cell from DeleteCellCommand */
    void deletePrisonerFromCell(Person prisoner) throws PersonNotFoundException, NotImprisonedException;

    /**Adds given prisoner back into a cell from undo command */
    void addPrisonerToCellFromUndo(Person prisoner, String cellAddress);

    /** Deletes given prisoner from a cell from undo command*/
    void deletePrisonerFromCellFromUndo(Person prisoner, String cellAddress);

    /** Updates given prisoner who changed from undo command*/
    void updatePrisonerFromUndo(Person changed, Person original);
    //@@author
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

    //@@author sarahgoh97
    /**
     * Updates the filter of the filtered person list to show only people in the cell.
     */
    void updateFilteredPersonListForCell(Predicate<Person> predicate, String cellAddress)
            throws NonExistentCellException;

}
