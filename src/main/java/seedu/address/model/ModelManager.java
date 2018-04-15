package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.pair.Pair;
import seedu.address.model.pair.exceptions.DuplicatePairException;
import seedu.address.model.pair.exceptions.PairNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonMatchedCannotDeleteException;
import seedu.address.model.person.exceptions.PersonMatchedCannotEditException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Pair> filteredPairs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredPairs = new FilteredList<>(this.addressBook.getPairList());
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

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    //===================================Person operation ===================
    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException,
            PersonMatchedCannotDeleteException {
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
            throws DuplicatePersonException, PersonNotFoundException, PersonMatchedCannotEditException {
        requireAllNonNull(target, editedPerson);
        addressBook.updatePersonForAddAndEdit(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void rateRemarkPerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);
        addressBook.updatePersonForRateAndRemark(target, editedPerson);
        indicateAddressBookChanged();
    }

    //============Pair operation=============================================================================
    //@@author alexawangzi
    @Override
    public void deletePair(Pair target) throws PairNotFoundException {
        addressBook.removePair(target);
        indicateAddressBookChanged();
    }

    //@@author alexawangzi
    /**
     * Add a pair to STUtor
     * @param student
     * @param tutor
     * @throws DuplicatePersonException
     */
    public synchronized void addPair(Person student, Person tutor) throws DuplicatePairException {
        addressBook.addPair(student, tutor);
        updateFilteredPairList(PREDICATE_SHOW_ALL_PAIRS);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    //@@author
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


    //=========== Filtered Pair List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Pair} backed by the internal list of
     * {@code addressBook}
     */

    @Override
    public ObservableList<Pair> getFilteredPairList() {
        return FXCollections.unmodifiableObservableList(filteredPairs);
    }

    @Override
    public void updateFilteredPairList(Predicate<Pair> predicate) {
        requireNonNull(predicate);
        filteredPairs.setPredicate(predicate);
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
