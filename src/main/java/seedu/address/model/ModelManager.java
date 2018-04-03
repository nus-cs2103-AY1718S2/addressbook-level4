package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.WrongPasswordException;
import seedu.address.model.alias.Alias;
import seedu.address.model.alias.exceptions.AliasNotFoundException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.building.Building;
import seedu.address.model.building.exceptions.BuildingNotFoundException;
import seedu.address.model.building.exceptions.CorruptedVenueInformationException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook addressBook) {
        this(addressBook, new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        addressBook.updatePassword(newData.getPassword());
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

    //@@author jingyinno
    @Override
    public synchronized void addAlias(Alias alias) throws DuplicateAliasException {
        addressBook.addAlias(alias);
        indicateAddressBookChanged();
    }
    //@@author

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //@@author yeggasd
    @Override
    public void updatePassword(byte[] password) {
        addressBook.updatePassword(password);
        indicateAddressBookChanged();
    }
    //@@author

    //@@author jingyinno
    @Override
    public void removeAlias(String toRemove) throws AliasNotFoundException {
        addressBook.removeAlias(toRemove);
        indicateAddressBookChanged();
    }
    //@@author

    //@@author Caijun7-reused
    @Override
    public void deleteTag(Tag tag) {
        addressBook.removeTag(tag);
    }
    //@@author

    //@@author Caijun7
    /**
     * Imports the specified {@code AddressBook} from the filepath to the current {@code AddressBook}.
     * @param filepath
     */
    @Override
    public void importAddressBook(String filepath, byte[] password) throws DataConversionException, IOException,
                                                                            WrongPasswordException {
        requireNonNull(filepath);

        XmlAddressBookStorage xmlAddressBook = new XmlAddressBookStorage(filepath);
        xmlAddressBook.importAddressBook(filepath, this.addressBook, password);
        indicateAddressBookChanged();
    }

    /**
     * Exports the current view of {@code AddressBook} to the filepath.
     * @param filepath
     */
    @Override
    public void exportAddressBook(String filepath, Password password) throws IOException, WrongPasswordException,
                                                                                DuplicatePersonException {
        requireNonNull(filepath);

        XmlAddressBookStorage xmlAddressBook = new XmlAddressBookStorage(filepath);
        xmlAddressBook.exportAddressBook(filepath, password, filteredPersons);
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

    //=========== Vacant Room Finder ==========================================================================

    //@@author Caijun7
    @Override
    public ArrayList<ArrayList<String>> retrieveAllRoomsSchedule(Building building) throws BuildingNotFoundException,
            CorruptedVenueInformationException {
        if (!Building.isValidBuilding(building)) {
            throw new BuildingNotFoundException();
        }
        return building.retrieveAllRoomsSchedule();
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
