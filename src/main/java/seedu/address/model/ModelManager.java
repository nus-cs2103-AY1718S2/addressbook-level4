package seedu.address.model;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.PersonChangedEvent;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final ObservableList<Person> personList;
    private Person selectedPerson;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with data file: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredPersons.setPredicate(PREDICATE_SHOW_ALL_PERSONS);

        // Actively listen to predicate changes
        personList = FXCollections.observableArrayList(filteredPersons);
        filteredPersons.addListener((ListChangeListener<Person>) c -> {
            personList.setAll(filteredPersons);
        });
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        // Quickfix: Reset the filtered person
        personList.clear();
        
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

    /** Raises an event to indicate the person has changed */
    private void indicatePersonChanged(Person source, Person updated) {
        raise(new PersonChangedEvent(source, updated));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
        indicatePersonChanged(target, null);
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
        indicatePersonChanged(target, editedPerson);
    }

    @Override
    public void deleteTag(Tag tag) throws TagNotFoundException {
        addressBook.removeTag(tag);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    /**
     * Returns an active view of the list of {@code Person} backed by the internal list of {@code addressBook}
     * The differences with {@link ModelManager#getFilteredPersonList}
     * is the ability to actively listen to predicate change
     */
    @Override
    public ObservableList<Person> getActivePersonList() {
        return personList;
    }

    /**
     * Set the currently selected person
     * @param selectedPerson currently in the list
     */
    @Override
    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    /**
     * Get the currently selected person
     */
    @Override
    public Person getSelectedPerson() {
        return selectedPerson;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    //@@author mhq199657
    @Override
    public void filterFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        Predicate<? super Person> currPredicate = filteredPersons.getPredicate();
        filteredPersons.setPredicate(isNull(currPredicate) ? predicate : predicate.and(currPredicate));
    }

    //@@author kexiaowen
    @Override
    public void sortPersonListAscOrder(SortCommand.SortField sortField) {
        addressBook.sortAsc(sortField);
        Predicate<? super Person> currPredicate = filteredPersons.getPredicate();
        filteredPersons.setPredicate(currPredicate);
    }

    @Override
    public void sortPersonListDescOrder(SortCommand.SortField sortField) {
        addressBook.sortDesc(sortField);
        Predicate<? super Person> currPredicate = filteredPersons.getPredicate();
        filteredPersons.setPredicate(currPredicate);
    }

    //@@author
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
