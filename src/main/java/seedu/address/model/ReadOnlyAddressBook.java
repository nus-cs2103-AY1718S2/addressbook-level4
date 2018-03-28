package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.todo.ToDo;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the todos list.
     * This list will not contain any duplicate todos.
     */
    ObservableList<ToDo> getToDoList();

    /**
     * Returns an unmodifiable view of the todos list.
     * This list will not contain any duplicate todos.
     */
    ObservableList<Group> getGroupList();

    /**
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getEventList();
}
