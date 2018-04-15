package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.exceptions.AlreadyInCellException;
import seedu.address.model.cell.exceptions.FullCellException;
import seedu.address.model.cell.exceptions.NonExistentCellException;
import seedu.address.model.cell.exceptions.NotImprisonedException;
import seedu.address.model.cell.exceptions.NotPrisonerException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
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
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final Session session;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     * Also initialises an empty cellMap and empty session
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        updateFilteredPersonList(new NameContainsKeywordsPredicate(new ArrayList<String>()));

        //@@author zacci
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

    //@@author zacci
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
    //@@author

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //@@author sarahgoh97
    @Override
    public void updatePrisonerFromUndo(Person changed, Person original) {
        addressBook.updatePrisonerFromUndo(changed, original);
        indicateAddressBookChanged();
    }

    @Override
    public void addPrisonerToCell(Person prisoner, String cellAddress)
            throws FullCellException, NonExistentCellException,
            NotPrisonerException, AlreadyInCellException {
        requireAllNonNull(prisoner, cellAddress);
        addressBook.addPrisonerToCell(cellAddress, prisoner);
        indicateAddressBookChanged();
    }

    /**
     * Deletes a prisoner from a cell from DeleteCellCommand
      * @param prisoner to be removed from cell
     * @throws PersonNotFoundException if prisoner does not exist
     * @throws NotImprisonedException if person chosen is not imprisoned here
     */
    @Override
    public void deletePrisonerFromCell(Person prisoner) throws PersonNotFoundException, NotImprisonedException {
        requireNonNull(prisoner);
        if (!filteredPersons.contains(prisoner)) {
            throw new PersonNotFoundException();
        } else {
            String cellAddress = prisoner.getAddress().toString();
            if (prisoner.getIsInCell()) {
                cellAddress = cellAddress.substring(0, cellAddress.indexOf(" "));
                addressBook.deletePrisonerFromCell(prisoner, cellAddress);
                Person freedPrisoner = new Person(prisoner, false);
                addressBook.updatePrisoner(prisoner, freedPrisoner);
            } else {
                throw new NotImprisonedException();
            }
        }
        indicateAddressBookChanged();
    }

    /* this is to undo AddCellCommand*/
    @Override
    public void deletePrisonerFromCellFromUndo(Person prisoner, String cellAddress) {
        requireAllNonNull(prisoner, cellAddress);
        Person updatedPrisoner = new Person(prisoner, true, cellAddress);
        addressBook.deletePrisonerFromCell(updatedPrisoner, cellAddress);
        indicateAddressBookChanged();
    }

    /* this is to undo DeleteCellCommand*/
    @Override
    public void addPrisonerToCellFromUndo(Person prisoner, String cellAddress) {
        requireAllNonNull(prisoner, cellAddress);
        addressBook.addPrisonerToCellPermitted(prisoner, cellAddress);
        indicateAddressBookChanged();
    }
    //@@author

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook) && filteredPersons.equals(other.filteredPersons);
    }

    //@@author sarahgoh97
    /* This is for ListCellCommand */
    @Override
    public void updateFilteredPersonListForCell(Predicate<Person> predicate, String cellAddress)
            throws NonExistentCellException {
        requireNonNull(predicate);
        requireNonNull(cellAddress);
        if (Cell.isValidCellAddress(cellAddress)) {
            filteredPersons.setPredicate(predicate);
        } else {
            throw new NonExistentCellException();
        }
    }
    //@@author

    @Override
    public String toString() {
        return filteredPersons.toString();
    }

}
