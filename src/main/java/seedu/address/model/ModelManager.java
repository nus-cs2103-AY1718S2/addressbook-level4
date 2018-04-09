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
import seedu.address.commons.events.ui.CalendarChangedEvent;
import seedu.address.commons.events.ui.TimetableChangedEvent;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.group.Group;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagNotFoundException;
import seedu.address.model.todo.ToDo;
import seedu.address.model.todo.exceptions.DuplicateToDoException;
import seedu.address.model.todo.exceptions.ToDoNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<ToDo> filteredToDos;
    private final FilteredList<Event> filteredEvents;
    private final FilteredList<Group> filteredGroups;

    private boolean inCalendarView = true;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredToDos = new FilteredList<>(this.addressBook.getToDoList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
        filteredGroups = new FilteredList<>(this.addressBook.getGroupList());
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

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    //@@author LeonidAgarth
    @Override
    public void indicateCalendarChanged() {
        raise(new CalendarChangedEvent());
    }

    @Override
    public void indicateTimetableChanged() {
        raise(new TimetableChangedEvent());
    }

    //@@author
    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteToDo(ToDo target) throws ToDoNotFoundException {
        addressBook.removeToDo(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addToDo(ToDo todo) throws DuplicateToDoException {
        addressBook.addToDo(todo);
        indicateAddressBookChanged();
    }

    @Override
    public void updateTag(Tag target, Tag editedTag) throws TagNotFoundException {
        addressBook.editTag(target, editedTag);
        indicateAddressBookChanged();
    }

    @Override
    public void removeTag(Tag tag) {
        addressBook.removeTag(tag);
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void updateToDo(ToDo target, ToDo editedToDo)
            throws DuplicateToDoException, ToDoNotFoundException {
        requireAllNonNull(target, editedToDo);

        addressBook.updateToDo(target, editedToDo);
        indicateAddressBookChanged();
    }

    @Override
    public void updateGroup(Group target, Group editedGroup)
            throws DuplicateGroupException, GroupNotFoundException {
        requireAllNonNull(target, editedGroup);

        addressBook.updateGroup(target, editedGroup);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addGroup(Group group) throws DuplicateGroupException {
        addressBook.addGroup(group);
        indicateAddressBookChanged();
    }

    //@@author LeonidAgarth
    @Override
    public synchronized void addEvent(Event event) throws DuplicateEventException {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public boolean calendarIsViewed() {
        return inCalendarView;
    }

    @Override
    public void switchView() {
        inCalendarView = !inCalendarView;
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

    //=========== Filtered ToDo List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ToDo} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ToDo> getFilteredToDoList() {
        return FXCollections.unmodifiableObservableList(filteredToDos);
    }

    @Override
    public void updateFilteredToDoList(Predicate<ToDo> predicate) {
        requireNonNull(predicate);
        filteredToDos.setPredicate(predicate);
    }

    //@@author LeonidAgarth
    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }
    //@@author
    //=========== Filtered Group List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return FXCollections.unmodifiableObservableList(filteredGroups);
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
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
