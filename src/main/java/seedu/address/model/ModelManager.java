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
import seedu.address.commons.events.ui.handleCalendarViewChangedEvent;
import seedu.address.model.calendar.GoogleCalendar;
import seedu.address.model.export.exceptions.CalendarAccessDeniedException;
import seedu.address.model.export.exceptions.ConnectivityIssueException;
import seedu.address.model.export.exceptions.InvalidFileNameException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final GoogleCalendar calendar = new GoogleCalendar();

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

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        calendar.resetCalendar();
        raise(new handleCalendarViewChangedEvent());
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
        calendar.removePersonFromCalendar(target);
        raise(new handleCalendarViewChangedEvent());
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        calendar.addPersonToCalendar(person);
        raise(new handleCalendarViewChangedEvent());
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        calendar.updatePersonToCalendar(target, editedPerson);
        raise(new handleCalendarViewChangedEvent());
        indicateAddressBookChanged();
    }

    //@@author daviddalmaso
    @Override
    public void exportCalendar() throws CalendarAccessDeniedException, ConnectivityIssueException {
        addressBook.exportCalendar();
    }

    @Override
    public void exportPortfolio(String filePath) throws InvalidFileNameException {
        requireNonNull(filePath);
        addressBook.exportPortfolio(filePath);
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

    @Override
    public ObservableList<Person> sortFilteredPersonList(ObservableList<Person> personsList) {

        addressBook.sortedPersonsList();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();

        return personsList;
    }
}
