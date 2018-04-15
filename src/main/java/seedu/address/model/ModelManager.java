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
import seedu.address.commons.events.model.CustomerStatsChangedEvent;
import seedu.address.commons.events.model.MenuChangedEvent;
import seedu.address.model.dish.exceptions.DishNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final CustomerStats customerStats;
    private final FilteredList<Task> filteredTasks;
    private final Menu menu;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs, ReadOnlyCustomerStats customerStats,
                        ReadOnlyMenu menu) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook
                + " and user prefs " + userPrefs
                + " and customer stats " + customerStats
                + " and menu " + menu);
        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.customerStats = new CustomerStats();
        filteredTasks = new FilteredList<>(this.addressBook.getTaskList());
        this.menu = new Menu(menu);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new CustomerStats(), new Menu());
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

    //@@author Wuhao-ooo
    /** Raises an event to indicate customer stats has changed */
    private void indicateCustomerStatsChanged() {
        raise(new CustomerStatsChangedEvent(customerStats));
    }

    //@@author
    //@@author ZacZequn
    /** Raises an event to indicate customer stats has changed */
    private void indicateMenuChanged() {
        raise(new MenuChangedEvent(menu));
    }

    //@@author
    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException, DishNotFoundException {
        indicateMenuChanged();
        checkOrder(person);
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
        customerStats.addCount(person.getPhone().toString());
        indicateCustomerStatsChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addTask(Task task) throws DuplicateTaskException {
        addressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteTask(Task task) throws TaskNotFoundException {
        addressBook.removeTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateAddressBookChanged();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    //@@author ZacZequn
    @Override
    public void checkOrder(Person target)  throws DishNotFoundException {
        if (menu.get(target.getOrder().toString()) == null) {
            throw new DishNotFoundException("Dish not available");
        }
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
