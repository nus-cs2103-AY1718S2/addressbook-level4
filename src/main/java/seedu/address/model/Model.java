package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.DuplicateEventException;
import seedu.address.model.event.Event;
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
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<ToDo> PREDICATE_SHOW_ALL_TODOS = unused -> true;
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(Person target) throws PersonNotFoundException;

    /** Deletes the given to-do. */
    void deleteToDo(ToDo target) throws ToDoNotFoundException;

    /** Adds the given person */
    void addPerson(Person person) throws DuplicatePersonException;

    /** Changes the color of a tag */
    public void updateTag(Tag target, Tag editedTag) throws TagNotFoundException;

    /** Removes the given tag from addressbook and all persons */
    void removeTag(Tag tag);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /**
     * Replaces the given ToDo {@code target} with {@code editedToDo}.
     *
     * @throws DuplicateToDoException if updating the ToDo's details causes the ToDo to be equivalent to
     *      another existing ToDo in the list.
     * @throws ToDoNotFoundException if {@code target} could not be found in the list.
     */
    void updateToDo(ToDo target, ToDo editedToDo)
            throws DuplicateToDoException, ToDoNotFoundException;
    /**
     * Replaces the given Group {@code target} with {@code editedGroup}.
     *
     * @throws DuplicateGroupException if updating the Group's details causes the Group to be equivalent to
     *      another existing Group in the list.
     * @throws GroupNotFoundException if {@code target} could not be found in the list.
     */
    void updateGroup(Group target, Group editedGroup)
            throws DuplicateGroupException, GroupNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered to-do list */
    ObservableList<ToDo> getFilteredToDoList();

    /** Returns an unmodifiable view of the filtered to-do list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Returns an unmodifiable view of the filtered group list
     */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the filter of the filtered to-do list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredToDoList(Predicate<ToDo> predicate);

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Updates the filter of the filtered groupList to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /** Adds the given to-do */
    void addToDo(ToDo todo) throws DuplicateToDoException;

    /** Add group */
    void addGroup(Group group) throws DuplicateGroupException;

    /** Adds the given Event */
    void addEvent(Event event) throws DuplicateEventException;

    /** Checks whether application is in Calendar or Timetable view */
    boolean calendarIsViewed();

    /** Switches between Calendar and Timetable view */
    void switchView();

    /** Raises an event to indicate the calendar has changed */
    void indicateCalendarChanged();

    /** Raises an event to indicate the timetable has changed */
    void indicateTimetableChanged();
}
