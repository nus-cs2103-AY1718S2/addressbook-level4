package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.login.Password;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AlreadyLoggedInException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Storage storage;
    private final AddressBook addressBook;
    private final UserDatabase userDatabase;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, ReadOnlyUserDatabase userDatabase,
                         Storage storage) {
        super();
        requireAllNonNull(addressBook, userPrefs, userDatabase);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs
                + "and user database " + userDatabase);
        this.storage = storage;
        this.userDatabase = new UserDatabase(userDatabase);
        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs and a login status
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, ReadOnlyUserDatabase userDatabase,
                        Storage storage, boolean loggedIn) {
        super();
        requireAllNonNull(addressBook, userPrefs, userDatabase);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs
                + "and user database " + userDatabase);
        this.storage = storage;
        this.userDatabase = new UserDatabase(userDatabase, true);
        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager(Storage storage) {
        this(new AddressBook(), new UserPrefs(), new UserDatabase(), storage);
    }


    // ============== AddressBook Modifiers =============================================================

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

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


    //@@author kaisertanqr

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
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
